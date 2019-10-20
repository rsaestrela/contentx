package contentx.core

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentMapOf
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
            val child = parent.addChild("test-child-${i}", persistentMapOf()).blockingGet()
            val last = parent.children().blockingLast()
            assertEquals(parent, child.parent().blockingGet())
            val properties = child.properties().blockingGet()
            assertEquals(36, (properties["id"] as String).length)
            assertEquals("test-child-${i}", properties["name"] as String)
            assertEquals(36, child.id().blockingGet().length)
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
        node.properties().test().assertValueCount(1)
        val properties = node.properties().blockingGet()
        assertEquals(36, (properties["id"] as String).length)
        assertEquals("test-child", properties["name"] as String)
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
        val node = root.addChild("test-child", persistentMapOf())
                .doOnSuccess { c -> propertiesMap.forEach { c.putProperty(it.key, it.value) } }
                .blockingGet()
        node.properties().test().assertValueCount(1)
        val properties = node.properties().blockingGet()
        assertEquals(36, (properties["id"] as String).length)
        assertEquals("test-child", properties["name"] as String)
        assertEquals(propertiesMap["property1"] as Byte, properties["property1"] as Byte)
        assertEquals(propertiesMap["property2"] as Short, properties["property2"] as Short)
        assertEquals(propertiesMap["property3"] as Int, properties["property3"] as Int)
        assertEquals(propertiesMap["property4"] as Long, properties["property4"] as Long)
        assertEquals(propertiesMap["property5"] as Float, properties["property5"] as Float)
        assertEquals(propertiesMap["property6"] as Char, properties["property6"] as Char)
        assertEquals(propertiesMap["property7"] as Boolean, properties["property7"] as Boolean)
        assertEquals(propertiesMap["property8"] as Array<*>, properties["property8"] as Array<*>)
    }

    private fun propertiesMap(): PersistentMap<String, Any> {
        return persistentHashMapOf(
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
