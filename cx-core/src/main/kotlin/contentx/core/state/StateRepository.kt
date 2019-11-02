package contentx.core.state

import contentx.core.AbstractRepository
import contentx.core.Node
import contentx.core.RepositoryRoot
import io.reactivex.Maybe
import io.reactivex.Single

class StateRepository : AbstractRepository() {

    override fun root(): Single<RepositoryRoot> {
        val root = RootStateNode()
        return Single.just(root)
    }

    override fun resolve(path: String): Maybe<out Node> {
        return super.resolve(root(), path)
    }

}