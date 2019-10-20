package contentx.api.representational

import com.fasterxml.jackson.annotation.JsonProperty
import contentx.core.Node
import contentx.core.RepositoryRoot
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

class RepositoryTree(private val rootNode: RepositoryRoot) {

    @JsonProperty
    fun root(): NodeV {
        return representational(rootNode)
    }

    private fun representational(rootNode: RepositoryRoot): NodeV {
        return NodeV().representational(rootNode)
    }

    class NodeV {

        @JsonProperty
        lateinit var id: String

        @JsonProperty
        lateinit var name: String

        @JsonProperty
        lateinit var path: String

        @JsonProperty
        var properties: PersistentMap<String, Any> = persistentMapOf()

        @JsonProperty
        var children: List<NodeV> = arrayListOf()

        // TODO: rework async
        fun representational(node: Node): NodeV {
            val nodeV = NodeV()
            nodeV.id = node.id().blockingGet()
            nodeV.name = node.name().blockingGet()
            nodeV.path = node.path().blockingGet()
            nodeV.properties = node.properties().blockingGet()
            val childrenN = node.children().blockingIterable().toCollection(arrayListOf())
            childrenN.forEach { n ->
                val childNodeV = representational(n)
                nodeV.addChild(childNodeV)
            }
            return nodeV
        }

        private fun addChild(nodeV: NodeV) {
            children = children.plus(nodeV)
        }

    }

}