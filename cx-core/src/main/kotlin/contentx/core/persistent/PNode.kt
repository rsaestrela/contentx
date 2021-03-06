package contentx.core.persistent

import contentx.core.Node
import contentx.core.RepositoryRoot
import contentx.core.persistent.unit.PersistenceUnit
import io.reactivex.Maybe
import io.reactivex.Single
import java.util.*

class PNode {

    lateinit var _id: String

    lateinit var name: String

    lateinit var parent: String

    lateinit var properties: Map<String, Any>

    companion object {

        fun newRoot(): PNode {
            val pNode = PNode()
            pNode._id = UUID.randomUUID().toString()
            pNode.name = "root"
            pNode.parent = ""
            pNode.properties = mapOf()
            return pNode
        }

        fun newNode(name: String, parent: String, properties: Map<String, Any>): PNode {
            val pNode = PNode()
            pNode._id = UUID.randomUUID().toString()
            pNode.name = name
            pNode.parent = parent
            pNode.properties = properties
            return pNode
        }

        fun fromPNode(pNode: PNode, pu: PersistenceUnit): Single<RepositoryRoot> {
            return Single.just(pNode).map { p -> RootPersistentNode(p, pu) }
        }

        fun fromNode(pn: PNode, pu: PersistenceUnit): Node {
            return SimplePersistentNode(pn, pu)
        }

        fun maybeSimple(pn: Single<PNode>, pu: PersistenceUnit): Maybe<Node> {
            return pn.flatMapMaybe { p -> Maybe.just(SimplePersistentNode(p, pu)) }
        }

    }

    override fun toString(): String {
        return "[_id=$_id, name=$name]"
    }

}