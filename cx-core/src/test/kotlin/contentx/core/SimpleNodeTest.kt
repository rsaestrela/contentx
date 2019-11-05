package contentx.core

import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.Ignore
import kotlin.test.Test

@RunWith(Parameterized::class)
class SimpleNodeTest(private val repository: Repository) : AbstractRepositoryTest() {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
                CxRepository.get(RepositoryType.MAP),
                CxRepository.get(RepositoryType.MONGO, RepositoryCredentialUtil.testingCredential)
        )
    }

    @Ignore
    @Test
    fun shouldCreateChildrenNodes() {
        repository.root().map { r -> testTree(r, 5) }.test().await()
    }

    private fun testTree(parent: Node, depth: Int) {
        if (depth < 1) {
            return
        }
        for (i in 1..5) {
            val child = parent.addChild("test-child-${i}", mapOf())
            child.map { c -> c.parent().test().assertValueCount(1) }
            child.map { c -> c.parent().test().assertValue { p -> p.id() == parent.id() } }
            child.map { c -> c.children().test().assertValueCount(0) }
            child.map { c -> testTree(c, depth.dec()) }.test().await()
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Ignore
    @Test
    fun shouldAcceptPropertiesOnConstruction() {
        val propertiesMap = propertiesMap()
        val child = repository.root().flatMap { r -> r.addChild("test-child", propertiesMap) }
        val testChild = child.test().await()
        testChild.assertComplete()
        testChild.assertValue { c -> (propertiesMap["property1"] as Byte).toInt() == c.properties()["property1"] as Int }
        testChild.assertValue { c -> (propertiesMap["property2"] as Short).toInt() == c.properties()["property2"] as Int }
        testChild.assertValue { c -> propertiesMap["property3"] as Int == c.properties()["property3"] as Int }
        testChild.assertValue { c -> propertiesMap["property4"] as Long == c.properties()["property4"] as Long }
        testChild.assertValue { c -> (propertiesMap["property5"] as Float).toDouble() == c.properties()["property5"] as Double }
        testChild.assertValue { c -> propertiesMap["property6"] as Char == (c.properties()["property6"] as String).toCharArray()[0] }
        testChild.assertValue { c -> propertiesMap["property7"] as Boolean == c.properties()["property7"] as Boolean }
        testChild.assertValue { c -> propertiesMap["property8"] as ArrayList<Any> == c.properties()["property8"] as ArrayList<Any> }
    }

    private fun propertiesMap(): Map<String, Any> {
        return mapOf(
                Pair("property1", Byte.MAX_VALUE),
                Pair("property2", Short.MAX_VALUE),
                Pair("property3", Int.MAX_VALUE),
                Pair("property4", Long.MAX_VALUE),
                Pair("property5", Float.MAX_VALUE),
                Pair("property6", 'c'),
                Pair("property7", true),
                Pair("property8", arrayListOf("value1", Int.MAX_VALUE, false))
        )
    }


}
