package contentx.core.state

import contentx.core.Node
import contentx.core.Property
import contentx.core.RepositoryRoot
import io.reactivex.Single
import kotlinx.collections.immutable.persistentMapOf

class RootStateNode : SimpleStateNode(Property.ROOT.key, null, persistentMapOf()), RepositoryRoot {

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