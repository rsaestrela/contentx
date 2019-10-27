package contentx.core.state

import contentx.core.Repository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class RootStateNodeTest {

    @Test
    fun shouldCreateRootNode() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        assertNotNull(root)
        assertEquals(root.id(), "root")
        assertEquals(root.name(), "root")
        assertEquals(root.path().blockingGet(), "/root")
    }

    @Test
    fun shouldNotAcceptProperties() {
        val repository: Repository = StateRepository()
        repository.root()
                .flatMapMaybe { r -> r.putProperty("property", "value") }
                .test()
                .await()
                .assertError(UnsupportedOperationException::class.java)
    }

    @Test
    fun rootNodeShouldNotContainAnyChildNode() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        assertTrue(root.children().blockingIterable().none())
        root.children().test().assertValueCount(0)
    }

    @Test
    fun shouldBeAbleToCreateRootChildNode() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        val newNode = root.addChild("test-child", mapOf()).blockingGet()
        assertEquals(newNode, root.children().blockingFirst())
        root.children().test().assertValueCount(1)
    }

    @Test
    fun shouldBeAbleToCreateRootChildrenNodes() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        for (i in 1..10) {
            val child = root.addChild("test-child-${i}", mapOf()).blockingGet()
            val last = root.children().blockingLast()
            assertEquals(child, last)
            last.children().test().assertValueCount(0)
        }
        root.children().test().assertValueCount(10)
    }

}
