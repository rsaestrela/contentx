package contentx.core

import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class RepositoryTest(private val repository: Repository) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
                CxRepository.get(RepositoryType.MAP),
                CxRepository.get(RepositoryType.MONGO, RepositoryCredentialUtil.testingCredential)
        )
    }

    @Test
    fun shouldResolveRootNode() {
        repository.root().blockingGet()
        repository.resolve("/root").test().await().assertComplete().assertValueCount(1)
    }

    @Ignore
    @Test
    fun shouldResolveRootChild() {

        val root = repository.root().blockingGet()

        val childS = root.addChild("test-child", mapOf())
        val testSChild = childS.test().await()
        testSChild.assertComplete()
        testSChild.assertValueCount(1)

        val child = childS.blockingGet()
        assertEquals(36, child.id().length)
        assertEquals("test-child", child.name())

        val resolvedS = repository.resolve("/root/${child.id()}")
        val testSResolved = resolvedS.test().await()
        testSResolved.assertComplete()
        testSResolved.assertValueCount(1)

        val resolved = resolvedS.blockingGet()
        assertEquals(resolved.id(), child.id())
        assertEquals("/root/${child.id()}", resolved.path().blockingGet())

    }

}
