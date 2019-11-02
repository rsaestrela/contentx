package contentx.api

import contentx.core.Node
import contentx.core.persistent.PersistentRepository
import contentx.core.persistent.unit.MongoRepositoryCredential
import io.reactivex.Maybe
import ratpack.handling.Context
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

    init {
        persistentRepository.root().subscribe()
    }

    override fun getContent(context: Context, uri: String): Maybe<out Node> {
        return persistentRepository.resolve(uri)
    }

}
