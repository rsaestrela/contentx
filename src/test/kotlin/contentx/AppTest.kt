package contentx

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AppTest {

    @Test
    fun test() {
        val contentXLauncher = ContentXLauncher()
        val repository = contentXLauncher.init()
        assertNotNull(repository)
        val root = repository.root()
        assertNotNull(root)
        assertEquals(36, root.id().length)
    }

}
