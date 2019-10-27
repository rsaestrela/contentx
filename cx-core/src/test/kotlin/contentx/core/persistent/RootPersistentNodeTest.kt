package contentx.core.persistent

import contentx.core.Node
import contentx.core.Repository
import contentx.core.persistent.unit.MongoRepositoryCredential
import kotlin.test.Test
import kotlin.test.assertEquals

internal class RootPersistentNodeTest {

    private val repositoryCredential = MongoRepositoryCredential.Builder()
            .user("raul")
            .password("")
            .database("contentx")
            .collection("data")
            .build()

    @Test
    fun shouldCreateRootNode() {

        val repository: Repository = PersistentRepository(repositoryCredential)

        val root = repository.root()
        val testRoot = root.test().await()
        testRoot.assertComplete()
        testRoot.assertValue { n -> n.name() == "root" }
        testRoot.assertValue { n -> n.id().length == 36 }

        val path = repository.root().flatMap { r -> r.path() }
        val testPath = path.test().await()
        testPath.assertValue { p -> p == "/root" }

    }

    @Test
    fun shouldNotAcceptProperties() {

        val repository: Repository = PersistentRepository(repositoryCredential)

        repository.root()
                .flatMapMaybe { r -> r.putProperty("prop", "any") }
                .test()
                .await()
                .assertError(UnsupportedOperationException::class.java)

    }

    @Test
    fun rootNodeShouldNotContainAnyChildNode() {

        val repository: Repository = PersistentRepository(repositoryCredential)

        val childrenP = repository.root().flatMapPublisher { r -> r.children() }
        childrenP.test().await()

        val children = mutableListOf<Node>()
        childrenP.subscribe { node -> children += node }
        assertEquals(listOf<Node>(), children)

    }

    @Test
    fun shouldBeAbleToCreateRootChildNode() {

        val repository: Repository = PersistentRepository(repositoryCredential)

        val child = repository.root().flatMap { r -> r.addChild("raul", mapOf()) }
        val testChild = child.test().await()

        testChild.assertComplete()
        testChild.assertValue { n -> n.name() == "raul" }
        testChild.assertValue { n -> n.id().length == 36 }

    }

}
