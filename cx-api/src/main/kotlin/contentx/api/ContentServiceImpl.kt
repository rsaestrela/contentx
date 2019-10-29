package contentx.api

import contentx.core.RepositoryRoot
import contentx.core.persistent.PersistentRepository
import contentx.core.persistent.unit.MongoRepositoryCredential
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class ContentServiceImpl : ContentService {

    private val repositoryCredential = MongoRepositoryCredential.Builder()
            .user("tester")
            .password("password")
            .database("contentx")
            .collection("data")
            .build()

    private var persistentRepository: PersistentRepository = PersistentRepository(repositoryCredential)

    override fun getContent(): Single<RepositoryRoot> {
        return persistentRepository.root()
    }

}
