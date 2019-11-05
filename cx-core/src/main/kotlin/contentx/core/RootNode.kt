package contentx.core

import io.reactivex.Maybe
import io.reactivex.Single

class RootNode(pNode: PNode, persistenceUnit: PersistenceUnit) : PersistentNode(pNode, persistenceUnit), RepositoryRoot {

    override fun path(): Single<String> {
        return Single.just("/root")
    }

    override fun putProperty(property: String, value: Any): Maybe<Node> {
        return Maybe.error(UnsupportedOperationException())
    }

}