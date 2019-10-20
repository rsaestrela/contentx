package contentx.core

import io.reactivex.Single
import kotlinx.collections.immutable.PersistentMap

open class SimpleStateNode(name: String, parent: Node?, properties: PersistentMap<String, Any>) : AbstractNode(name, parent, properties) {

    override fun id(): Single<String> {
        return properties().map { p -> p[Property.ID.key] as String }
    }

    override fun addChild(name: String, properties: PersistentMap<String, Any>): Single<Node> {
        val node = SimpleStateNode(name, this, properties)
        children = children + node
        return Single.just(node)
    }

}