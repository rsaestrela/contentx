package contentx.core.persistent

import contentx.core.Node
import contentx.core.persistent.unit.PersistenceUnit
import io.reactivex.Single

open class SimplePersistentNode<O>(pNode: PNode, pu: PersistenceUnit<O>) : AbstractPersistentNode<O>(pNode, pu) {

    override fun id(): String {
        return pNode._id
    }

    override fun addChild(name: String, properties: Map<String, Any>): Single<Node> {
        val pNode: Single<PNode> = Single.fromPublisher(pu.insert(PNode.newNode(name, pNode._id, properties)))
        return pNode.flatMap { n -> PNode.fromPNode(n, pu) }
    }

}