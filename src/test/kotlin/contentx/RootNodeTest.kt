package contentx

import kotlinx.collections.immutable.persistentMapOf
import java.lang.UnsupportedOperationException
import kotlin.test.*

internal class RootNodeTest {

    @Test
    fun shouldCreateRootNode() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        assertNotNull(root)
        assertEquals(root.id().blockingGet(), "root")
        assertEquals(root.name().blockingGet(), "root")
        assertEquals(root.path().blockingGet(), "/root")
    }

    @Test
    fun shouldNotAcceptProperties() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        assertNotNull(root)
        assertFailsWith<UnsupportedOperationException> { root.putProperty("prop", "any") }
    }

    @Test
    fun rootNodeShouldNotContainAnyNode() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        assertTrue(root.children().blockingIterable().none())
        root.children().test().assertValueCount(0)
    }

    @Test
    fun shouldBeAbleToCreateRootChildNode() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        val newNode = root.addChild("test-child", persistentMapOf()).blockingGet()
        assertEquals(newNode, root.children().blockingFirst())
        root.children().test().assertValueCount(1)
    }

    @Test
    fun shouldBeAbleToCreateRootChildrenNodes() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        for (i in 1..1000) {
            val child = root.addChild("test-child-${i}", persistentMapOf()).blockingGet()
            val last = root.children().blockingLast()
            assertEquals(child, last)
            last.children().test().assertValueCount(0)
        }
        root.children().test().assertValueCount(1000)
    }

}
