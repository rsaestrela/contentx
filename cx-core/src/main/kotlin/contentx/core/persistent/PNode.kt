package contentx.core.persistent

import com.mongodb.reactivestreams.client.Success
import contentx.core.Node
import contentx.core.RepositoryRoot
import contentx.core.persistent.unit.PersistenceUnit
import io.reactivex.Single

class PNode {

    lateinit var parent: String

    var properties: Map<String, Any> = hashMapOf()

    companion object {

        fun root(pNode: Single<PNode>, pu: PersistenceUnit<Success>): Single<RepositoryRoot> {
            return pNode.map { p -> RootPersistentNode(p, pu) }
        }

        fun <O> simple(pn: PNode, pu: PersistenceUnit<O>): Node {
            return SimplePersistentNode(pn, pu)
        }

        fun <O> simple(pn: Single<PNode>, pu: PersistenceUnit<O>): Single<Node> {
            return pn.flatMap { p -> Single.just(SimplePersistentNode(p, pu)) }
        }

    }

}