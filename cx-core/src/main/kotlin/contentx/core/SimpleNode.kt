package contentx.core

import io.reactivex.Single
import kotlinx.collections.immutable.PersistentMap

open class SimpleNode(name: String, parent: Node?, properties: PersistentMap<String, Any>) : AbstractNode(name, parent, properties) {

    override fun id(): Single<String> {
        return Single.just(id)
    }

    override fun addChild(name: String, properties: PersistentMap<String, Any>): Single<Node> {
        val node = SimpleNode(name, this, properties)
        children = children + node
        return Single.just(node)
    }

}