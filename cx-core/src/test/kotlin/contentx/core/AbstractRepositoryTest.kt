package contentx.core

import io.reactivex.Single
import kotlin.test.BeforeTest

abstract class AbstractRepositoryTest {

    @BeforeTest
    fun clearDatabase() {
        MongoTestingPersistenceUnit(RepositoryCredentialUtil.testingCredential).dropCollection()
    }

    private class MongoTestingPersistenceUnit(credential: RepositoryCredential) : MongoPersistenceUnit(credential), TestingPersistenceUnit {
        override fun dropCollection() {
            Single.fromPublisher(pu.drop()).blockingGet()
        }
    }

}