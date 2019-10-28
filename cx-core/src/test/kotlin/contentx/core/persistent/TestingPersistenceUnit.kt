package contentx.core.persistent

import com.mongodb.reactivestreams.client.Success
import org.reactivestreams.Publisher

interface TestingPersistenceUnit {

    fun dropCollection(): Publisher<Success>

}