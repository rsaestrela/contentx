package contentx.core


import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class RootNodeTest(private val repository: Repository) : AbstractRepositoryTest() {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
                CxRepository.get(RepositoryType.MAP)
        )
    }

    @Test
    fun shouldCreateRootNode() {

        val root = repository.root()
        val testRoot = root.test().await()

        testRoot.assertComplete()
        testRoot.assertValueCount(1)
        testRoot.assertValue { n -> n.name() == "root" }
        testRoot.assertValue { n -> n.id().length == 36 }

        val path = repository.root().flatMap { r -> r.path() }
        val testPath = path.test().await()
        testPath.assertValue { p -> p == "/root" }

    }

    @Test
    fun shouldNotAcceptProperties() {

        val rootS = repository.root()
        val testRoot = rootS.test().await()
        testRoot.assertComplete()
        testRoot.assertValueCount(1)

        repository.root()
                .flatMapMaybe { r -> r.putProperty("prop", "any") }
                .test()
                .await()
                .assertError(UnsupportedOperationException::class.java)

    }

    @Ignore
    @Test
    fun rootNodeShouldNotContainAnyChildNode() {
        val children = repository.root().flatMapPublisher { r -> r.children() }.blockingIterable().toCollection(arrayListOf())
        assertEquals(listOf<Node>(), children)
    }

    @Test
    fun shouldBeAbleToCreateRootChildNode() {

        val child = repository.root().flatMap { r -> r.addChild("test", mapOf()) }
        val testChild = child.test().await()

        testChild.assertComplete()
        testChild.assertValue { n -> n.name() == "test" }
        testChild.assertValue { n -> n.id().length == 36 }

    }

    @Test
    fun shouldBeAbleToCreateRootChildrenNodes() {

        for (i in 1..10) {
            val child = repository.root().flatMap { r -> r.addChild("test-child-${i}", mapOf()) }
            val testChild = child.test().await()
            testChild.assertComplete()
            testChild.assertValue { n -> n.name() == "test-child-${i}" }
            testChild.assertValue { n -> n.id().length == 36 }
        }

        repository.root().map { r -> r.children().test().assertValueCount(10).await() }

    }

}
