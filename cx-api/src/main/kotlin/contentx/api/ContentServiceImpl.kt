package contentx.api

import contentx.core.Repository
import contentx.core.RepositoryRoot
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentServiceImpl : ContentService {

    @Inject
    lateinit var stateRepository: Repository

    override fun getContent(): Single<RepositoryRoot> {
        return stateRepository.root()
    }

}
