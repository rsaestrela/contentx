package contentx.core.persistent.unit

import contentx.core.persistent.PNode
import org.reactivestreams.Publisher

interface PersistenceUnit<O> {

    fun insert(pNode: PNode): Publisher<O>

    fun findByProperty(property: String, value: String): Publisher<PNode>

}