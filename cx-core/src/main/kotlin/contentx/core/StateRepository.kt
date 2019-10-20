package contentx.core

import io.reactivex.Single

class StateRepository : Repository {

    override fun root(): Single<RepositoryRoot> {
        val root = RootStateNode()
        return Single.just(root)
    }

}