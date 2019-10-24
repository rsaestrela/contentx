package contentx.core.state

import contentx.core.Node
import contentx.core.Property
import io.reactivex.Single
import kotlinx.collections.immutable.PersistentMap

open class SimpleStateNode(name: String, parent: Node?, properties: PersistentMap<String, Any>) : AbstractStateNode(name, parent, properties) {

    override fun id(): String {
        return properties()[Property.ID.key] as String
    }

    override fun addChild(name: String, properties: PersistentMap<String, Any>): Single<Node> {
        val node = SimpleStateNode(name, this, properties)
        children = children + node
        return Single.just(node)
    }

}