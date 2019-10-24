package contentx.core.state

import contentx.core.Node
import contentx.core.Repository
import io.reactivex.Single

class StateRepository : Repository {

    override fun root(): Single<Node> {
        val root = RootStateNode()
        return Single.just(root)
    }

}