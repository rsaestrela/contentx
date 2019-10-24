package contentx.core.persistent

import contentx.core.Node
import contentx.core.Property
import contentx.core.persistent.unit.PersistenceUnit
import io.reactivex.Single
import kotlinx.collections.immutable.PersistentMap

open class SimplePersistentNode<O>(pNode: PNode, pu: PersistenceUnit<O>) : AbstractPersistentNode<O>(pNode, pu) {

    override fun id(): String {
        return properties()[Property.ID.key] as String
    }

    override fun addChild(name: String, properties: PersistentMap<String, Any>): Single<Node> {
        throw NotImplementedError()
    }

}