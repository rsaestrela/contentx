package contentx.core

import io.reactivex.Single

open class PersistentNode(pNode: PNode, pu: PersistenceUnit) : AbstractNode(pNode, pu) {

    override fun id(): String {
        return pNode._id
    }

    override fun addChild(name: String, properties: Map<String, Any>): Single<Node> {
        val pNode: Single<PNode> = Single.fromPublisher(pu.insert(PNode.newNode(name, pNode._id, properties)))
        return pNode.flatMap { n -> PNode.fromPNode(n, pu) }
    }

}