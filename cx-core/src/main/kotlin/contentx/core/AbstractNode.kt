package contentx.core

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import org.reactivestreams.Publisher

abstract class AbstractNode(protected val pNode: PNode,
                            protected val pu: PersistenceUnit) : Node {

    override fun name(): String {
        return pNode.name
    }

    override fun properties(): Map<String, Any> {
        return pNode.properties
    }

    override fun path(): Single<String> {
        return parent().flatMapSingle { p -> p.path() }.zipWith(Single.just(name()), BiFunction { p, t -> p.plus("/").plus(t) })
    }

    override fun parent(): Maybe<Node?> {
        val parent = pu.findByProperty(CxConstant.ID.v, pNode.parent)
        return Observable.fromPublisher(parent).switchIfEmpty(Observable.empty()).firstElement().map { pn -> PNode.fromNode(pn, pu) }
    }

    override fun children(): Flowable<Node> {
        val findPublisher: Publisher<PNode?> = pu.findByProperty(CxConstant.PARENT.v, pNode._id)
        return Flowable.fromPublisher(findPublisher).map { pn -> PNode.fromNode(pn, pu) }
    }

    override fun putProperty(property: String, value: Any): Maybe<Node> {
        pNode.properties = pNode.properties.plus(Pair(property, value))
        val insert = Single.fromPublisher(pu.insert(pNode))
        val node = Single.fromPublisher(pu.findByProperty(CxConstant.ID.v, pNode._id))
        return insert.flatMapMaybe { PNode.maybeSimple(node, pu) }
    }

}