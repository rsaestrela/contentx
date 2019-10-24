package contentx.core.persistent.unit

import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.Success
import contentx.core.Property
import contentx.core.persistent.PNode
import org.reactivestreams.Publisher

class MongoPersistenceUnit : PersistenceUnit<Success> {

    private val pu: MongoCollection<PNode> = MongoClients.create().getDatabase("contentx").getCollection("data", PNode::class.java)

    override fun insert(pNode: PNode): Publisher<Success> {
        return pu.insertOne(pNode)
    }

    override fun findByProperty(property: String, value: String): Publisher<PNode> {
        return pu.find(Filters.eq(Property.ID.key, value), PNode::class.java)
    }

}

