package contentx.core.persistent

import contentx.core.Node
import contentx.core.RepositoryRoot
import contentx.core.persistent.unit.PersistenceUnit
import io.reactivex.Maybe
import io.reactivex.Single

class RootPersistentNode(pNode: PNode, persistenceUnit: PersistenceUnit) : SimplePersistentNode(pNode, persistenceUnit), RepositoryRoot {

    override fun path(): Single<String> {
        return Single.just("/root")
    }

    override fun putProperty(property: String, value: Any): Maybe<Node> {
        return Maybe.error(UnsupportedOperationException())
    }

}