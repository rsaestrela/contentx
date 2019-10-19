package contentx

import io.reactivex.Single

class StateRepository : Repository {

    override fun root(): Single<RepositoryRoot> {
        return Single.just(RootNode())
    }

}