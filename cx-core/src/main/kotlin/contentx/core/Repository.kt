package contentx.core

import io.reactivex.Maybe
import io.reactivex.Single

interface Repository {

    fun root(): Single<RepositoryRoot>

    fun resolve(path: String): Maybe<out Node>

}