package contentx.core.state

import contentx.core.CxConstant
import contentx.core.Node
import contentx.core.RepositoryRoot
import io.reactivex.Maybe
import io.reactivex.Single

class RootStateNode : SimpleStateNode(CxConstant.ROOT.v, null, mapOf()), RepositoryRoot {

    override fun id(): String {
        return CxConstant.ROOT.v
    }

    override fun path(): Single<String> {
        return Single.just("/root")
    }

    override fun putProperty(property: String, value: Any): Maybe<Node> {
        return Maybe.error(UnsupportedOperationException())
    }

}