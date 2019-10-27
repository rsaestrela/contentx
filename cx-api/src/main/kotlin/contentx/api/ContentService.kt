package contentx.api

import contentx.core.RepositoryRoot
import io.reactivex.Single

interface ContentService {

    fun getContent(): Single<RepositoryRoot>

}
