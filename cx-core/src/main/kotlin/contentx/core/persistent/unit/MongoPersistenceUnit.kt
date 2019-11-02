package contentx.core.persistent.unit

import com.google.common.flogger.FluentLogger
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import contentx.core.CxConstant
import contentx.core.persistent.PNode
import io.reactivex.Single
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import org.reactivestreams.Publisher


open class MongoPersistenceUnit(mongoRepositoryCredential: MongoRepositoryCredential) : PersistenceUnit {

    private val logger = FluentLogger.forEnclosingClass()

    private val codecRegistry: CodecRegistry = getCodecRegistry()

    private val settings: MongoClientSettings = getSettings(mongoRepositoryCredential)

    protected val pu: MongoCollection<PNode> = getCollection(mongoRepositoryCredential)

    override fun insert(pNode: PNode): Publisher<PNode> {
        logger.atInfo().log("operation=insert pNode=%s", pNode)
        fromRegistries(MongoClients.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()))
        return Single.fromPublisher(pu.insertOne(pNode))
                .flatMapPublisher { findByProperty(CxConstant.ID.v, pNode._id) }
    }

    override fun findByProperty(property: String, value: String): Publisher<PNode> {
        logger.atInfo().log("operation=findByProperty prop=%s value=%s", property, value)
        return pu.find(Filters.eq(property, value), PNode::class.java)
    }

    private fun getCodecRegistry(): CodecRegistry {
        return fromRegistries(
                MongoClients.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        )
    }

    private fun getSettings(mongoRepositoryCredential: MongoRepositoryCredential): MongoClientSettings {
        return MongoClientSettings.builder()
                .credential(MongoCredential.createCredential(
                        mongoRepositoryCredential.user,
                        mongoRepositoryCredential.database,
                        mongoRepositoryCredential.password.toCharArray()))
                .codecRegistry(codecRegistry)
                .applyToConnectionPoolSettings { z -> z.maxWaitQueueSize(1000) }
                .build()
    }

    private fun getCollection(mongoRepositoryCredential: MongoRepositoryCredential): MongoCollection<PNode> {
        return MongoClients.create(settings)
                .getDatabase(mongoRepositoryCredential.database)
                .getCollection(mongoRepositoryCredential.collection, PNode::class.java)
    }

}

