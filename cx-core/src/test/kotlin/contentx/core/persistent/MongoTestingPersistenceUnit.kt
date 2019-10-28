package contentx.core.persistent

import com.mongodb.reactivestreams.client.Success
import contentx.core.persistent.unit.MongoPersistenceUnit
import contentx.core.persistent.unit.MongoRepositoryCredential
import org.reactivestreams.Publisher

class MongoTestingPersistenceUnit(credential: MongoRepositoryCredential) : MongoPersistenceUnit(credential), TestingPersistenceUnit {

    override fun dropCollection(): Publisher<Success> {
        return pu.drop()
    }

}

