package contentx.core.persistent

import contentx.core.persistent.unit.MongoRepositoryCredential
import io.reactivex.Single
import kotlin.test.BeforeTest

internal abstract class AbstractNodeTest {

    protected val repositoryCredential = MongoRepositoryCredential.Builder()
            .user("tester")
            .password("password")
            .database("contentx")
            .collection("data")
            .build()

    private val testingPersistenceUnit = MongoTestingPersistenceUnit(repositoryCredential)

    @BeforeTest
    fun clearDatabase() {
        Single.fromPublisher(testingPersistenceUnit.dropCollection()).subscribe()
    }

}
