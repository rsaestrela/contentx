package contentx.core.state

import contentx.core.Node
import contentx.core.Repository
import org.junit.Test
import kotlin.test.assertEquals

internal class SimpleStateNodeTest {

    @Test
    fun shouldCreateChildrenNodes() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        testTree(root, 5)
    }

    private fun testTree(parent: Node, depth: Int) {
        if (depth < 1) {
            return
        }
        for (i in 1..5) {
            val child = parent.addChild("test-child-${i}", mapOf()).blockingGet()
            val last = parent.children().blockingLast()
            assertEquals(parent, child.parent().blockingGet())
            assertEquals(36, child.id().length)
            assertEquals("test-child-${i}", child.name())
            assertEquals(36, child.id().length)
            assertEquals(child, last)
            last.children().test().assertValueCount(0)
            last.parent().test().assertValueCount(1)
            testTree(child, depth.dec())
        }
    }

    @Test
    fun shouldAcceptPropertiesOnConstruction() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        val propertiesMap = propertiesMap()
        val node = root.addChild("test-child", propertiesMap).blockingGet()
        val properties = node.properties()
        assertEquals(36, node.id().length)
        assertEquals("test-child", node.name())
        assertEquals(propertiesMap["property1"] as Byte, properties["property1"] as Byte)
        assertEquals(propertiesMap["property2"] as Short, properties["property2"] as Short)
        assertEquals(propertiesMap["property3"] as Int, properties["property3"] as Int)
        assertEquals(propertiesMap["property4"] as Long, properties["property4"] as Long)
        assertEquals(propertiesMap["property5"] as Float, properties["property5"] as Float)
        assertEquals(propertiesMap["property6"] as Char, properties["property6"] as Char)
        assertEquals(propertiesMap["property7"] as Boolean, properties["property7"] as Boolean)
        assertEquals(propertiesMap["property8"] as Array<*>, properties["property8"] as Array<*>)
    }

    @Test
    fun shouldAcceptAndReturnProperties() {
        val repository: Repository = StateRepository()
        val root = repository.root().blockingGet()
        val propertiesMap = propertiesMap()
        val node = root.addChild("test-child", mapOf())
                .doOnSuccess { c -> propertiesMap.forEach { c.putProperty(it.key, it.value) } }
                .blockingGet()
        val properties = node.properties()
        assertEquals(36, node.id().length)
        assertEquals("test-child", node.name())
        assertEquals(propertiesMap["property1"] as Byte, properties["property1"] as Byte)
        assertEquals(propertiesMap["property2"] as Short, properties["property2"] as Short)
        assertEquals(propertiesMap["property3"] as Int, properties["property3"] as Int)
        assertEquals(propertiesMap["property4"] as Long, properties["property4"] as Long)
        assertEquals(propertiesMap["property5"] as Float, properties["property5"] as Float)
        assertEquals(propertiesMap["property6"] as Char, properties["property6"] as Char)
        assertEquals(propertiesMap["property7"] as Boolean, properties["property7"] as Boolean)
        assertEquals(propertiesMap["property8"] as Array<*>, properties["property8"] as Array<*>)
    }

    private fun propertiesMap(): Map<String, Any> {
        return mapOf(
                Pair("property1", Byte.MAX_VALUE),
                Pair("property2", Short.MAX_VALUE),
                Pair("property3", Int.MAX_VALUE),
                Pair("property4", Long.MAX_VALUE),
                Pair("property5", Float.MAX_VALUE),
                Pair("property6", Char.MAX_VALUE),
                Pair("property7", true),
                Pair("property8", arrayOf(Int.MIN_VALUE))
        )
    }

}
