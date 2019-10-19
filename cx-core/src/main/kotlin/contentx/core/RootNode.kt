package contentx.core

import io.reactivex.Single
import kotlinx.collections.immutable.persistentMapOf

class RootNode : SimpleNode("root", null, persistentMapOf()), RepositoryRoot {

    override fun id(): Single<String> {
        return Single.just("root")
    }

    override fun path(): Single<String> {
        return Single.just("/root")
    }

    override fun putProperty(property: String, value: Any): Single<Node> {
        throw UnsupportedOperationException()
    }

}