package contentx.core.persistent.unit

import contentx.core.persistent.PNode
import org.reactivestreams.Publisher

interface PersistenceUnit {

    fun insert(pNode: PNode): Publisher<PNode>

    fun findByProperty(property: String, value: String): Publisher<PNode>

}