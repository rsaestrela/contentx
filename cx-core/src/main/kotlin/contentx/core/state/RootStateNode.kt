package contentx.core.state

import contentx.core.Node
import contentx.core.Property
import contentx.core.RepositoryRoot
import io.reactivex.Maybe
import io.reactivex.Single

class RootStateNode : SimpleStateNode(Property.ROOT.key, null, mapOf()), RepositoryRoot {

    override fun id(): String {
        return Property.ROOT.key
    }

    override fun path(): Single<String> {
        return Single.just("/root")
    }

    override fun putProperty(property: String, value: Any): Maybe<Node> {
        return Maybe.error(UnsupportedOperationException())
    }

}