package contentx

import io.reactivex.Single

interface Repository {

    fun root(): Single<RepositoryRoot>

}