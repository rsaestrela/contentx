package contentx.core.persistent

import contentx.core.Node
import contentx.core.Property
import contentx.core.RepositoryRoot
import contentx.core.persistent.unit.PersistenceUnit
import io.reactivex.Single

class RootPersistentNode<O>(pNode: PNode, persistenceUnit: PersistenceUnit<O>) : SimplePersistentNode<O>(pNode, persistenceUnit), RepositoryRoot {

    override fun id(): String {
        return Property.ROOT.key
    }

    override fun path(): Single<String> {
        return Single.just("/root")
    }

    override fun putProperty(property: String, value: Any): Single<Node> {
        throw UnsupportedOperationException()
    }

}