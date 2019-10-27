package contentx.core.state

import contentx.core.Node
import io.reactivex.Single

open class SimpleStateNode(name: String, parent: Node?, properties: Map<String, Any>) : AbstractStateNode(name, parent, properties) {

    override fun id(): String {
        return super.id
    }

    override fun addChild(name: String, properties: Map<String, Any>): Single<Node> {
        val node = SimpleStateNode(name, this, properties)
        children = children + node
        return Single.just(node)
    }

}