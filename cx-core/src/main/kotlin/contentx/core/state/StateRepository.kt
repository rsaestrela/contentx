package contentx.core.state

import contentx.core.Repository
import contentx.core.RepositoryRoot
import io.reactivex.Single

class StateRepository : Repository {

    override fun root(): Single<RepositoryRoot> {
        val root = RootStateNode()
        return Single.just(root)
    }

}