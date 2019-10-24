package contentx.core

import io.reactivex.Single

interface Repository {

    fun root(): Single<Node>

}