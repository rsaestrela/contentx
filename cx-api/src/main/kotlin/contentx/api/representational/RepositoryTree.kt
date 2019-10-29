package contentx.api.representational

import com.fasterxml.jackson.annotation.JsonProperty
import contentx.core.Node

class RepositoryTree(private val rootNode: Node) {

    @JsonProperty
    fun root(): NodeV {
        return representational(rootNode)
    }

    private fun representational(rootNode: Node): NodeV {
        return NodeV().representational(rootNode)
    }

    class NodeV {

        @JsonProperty
        lateinit var id: String

        @JsonProperty
        lateinit var name: String

        @JsonProperty
        var path: String = "TODO"

        @JsonProperty
        var properties: Map<String, Any> = hashMapOf()

        @JsonProperty
        var children: List<NodeV> = arrayListOf()

        fun representational(node: Node): NodeV {
            val nodeV = NodeV()
            nodeV.id = node.id()
            nodeV.name = node.name()
            nodeV.properties = node.properties()
            node.children().map { c -> nodeV.addChild(representational(c)) }.subscribe()
            return nodeV
        }

        private fun addChild(nodeV: NodeV) {
            children = children.plus(nodeV)
        }

    }

}