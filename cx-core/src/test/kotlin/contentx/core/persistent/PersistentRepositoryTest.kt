package contentx.core.persistent

import contentx.core.Repository
import java.util.*
import kotlin.test.Test

internal class PersistentRepositoryTest : AbstractNodeTest() {

    @Test
    fun shouldResolveRootNode() {
        val repository: Repository = PersistentRepository(repositoryCredential)
        repository.resolve("/root").test().await().assertComplete().assertValueCount(1)
    }

    @Test
    fun shouldResolveRootChild() {
        val repository: Repository = PersistentRepository(repositoryCredential)

        val child = repository.root().flatMap { r -> r.addChild("test-child", mapOf()) }
        val testChild = child.test().await()
        testChild.assertComplete()
        testChild.assertValue { n -> n.name() == "test-child" }
        testChild.assertValue { n -> n.id().length == 36 }

        child.map { c ->
            repository.resolve("/root/${c.id()}").test()
                    .await()
                    .assertComplete()
                    .assertValue { v -> v.name() == c.name() }
        }.test().await().assertComplete()

        child.map { repository.resolve("/root/${UUID.randomUUID()}").test().await().assertComplete().assertValueCount(0) }
                .test()
                .await()
                .assertComplete()
    }

    @Test
    fun shouldResolveMultipleRootChild() {
        val repository: Repository = PersistentRepository(repositoryCredential)
        for (i in 1..5) {
            val child = repository.root().flatMap { r -> r.addChild("test-child-${i}", mapOf()) }
            val testChild = child.test().await()
            testChild.assertComplete()
            testChild.assertValue { n -> n.name() == "test-child-${i}" }
            child.map { c -> repository.resolve("/root/${c.id()}").test().await().assertComplete().assertValueCount(1) }
                    .test()
                    .await()
                    .assertComplete()
            for (j in 1..5) {
                val child2 = child.flatMap { r -> r.addChild("test-child-${i}-${j}", mapOf()) }
                val testChild2 = child2.test().await()
                testChild2.assertComplete()
                testChild2.assertValue { n -> n.name() == "test-child-${i}-${j}" }
                child2.map { c2 ->
                    repository.resolve("/${child.map { c -> c.id() }.blockingGet()}/${c2.id()}")
                            .test()
                            .await()
                            .assertComplete()
                            .assertValueCount(1)
                }.test().await()
            }
        }
    }

}
