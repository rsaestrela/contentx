package contentx.core.state

import contentx.core.Repository
import kotlin.test.Test

internal class StateRepositoryTest {

    @Test
    fun shouldNotResolveOnRootNode() {
        val repository: Repository = StateRepository()
        repository.resolve("/root").test().assertValueCount(0)
    }

    @Test
    fun shouldResolveRootChildren() {
        val repository: Repository = StateRepository()

        for (i in 1..10) {
            val child = repository.root().flatMap { r -> r.addChild("test-child-${i}", mapOf()) }
            val testChild = child.test().await()
            testChild.assertComplete()
            testChild.assertValue { n -> n.name() == "test-child-${i}" }
            testChild.assertValue { n -> n.id().length == 36 }
            child.subscribe { n -> repository.resolve("/root/${n.id()}").test().assertValue { v -> v.id() == n.id() } }

        }

        repository.root().map { r -> r.children().test().assertValueCount(10).await() }
    }

}
