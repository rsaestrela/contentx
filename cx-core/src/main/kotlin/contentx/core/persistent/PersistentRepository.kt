package contentx.core.persistent

import com.mongodb.reactivestreams.client.Success
import contentx.core.Node
import contentx.core.Property
import contentx.core.Repository
import contentx.core.persistent.unit.MongoPersistenceUnit
import contentx.core.persistent.unit.PersistenceUnit
import io.reactivex.Single

class PersistentRepository : Repository {

    private val persistenceUnit: PersistenceUnit<Success> = MongoPersistenceUnit()

    override fun root(): Single<Node> {
        val insert = Single.fromPublisher(persistenceUnit.insert(PNode()))
        val root = Single.fromPublisher(persistenceUnit.findByProperty(Property.NAME.key, Property.ROOT.key))
        return insert.flatMap { PNode.root(root, persistenceUnit) }
    }

}