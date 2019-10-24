package contentx.core.persistent

import contentx.core.Node
import contentx.core.Property
import contentx.core.persistent.unit.PersistenceUnit
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import org.reactivestreams.Publisher

abstract class AbstractPersistentNode<O>(private val pNode: PNode,
                                         private val pu: PersistenceUnit<O>) : Node {

    override fun name(): String {
        return pNode.properties[Property.NAME.key] as String
    }

    override fun properties(): Map<String, Any> {
        return pNode.properties
    }

    override fun path(): Single<String> {
        return parent().flatMapSingle { p -> p.path() }.zipWith(Single.just(name()), BiFunction { p, t -> p.plus("/").plus(t) })
    }

    override fun parent(): Maybe<Node?> {
        val parent = pu.findByProperty(Property.PARENT.key, pNode.parent)
        return Maybe.fromSingle(Single.fromPublisher(parent).map { pn -> PNode.simple(pn, pu) })
    }

    override fun children(): Flowable<Node> {
        val findPublisher: Publisher<PNode> = pu.findByProperty(Property.PARENT.key, pNode.properties[Property.ID.key] as String)
        return Flowable.fromPublisher(findPublisher).map { pn -> PNode.simple(pn, pu) }
    }

    override fun putProperty(property: String, value: Any): Single<Node> {
        pNode.properties = pNode.properties.plus(Pair(property, value))
        val insert = Single.fromPublisher(pu.insert(pNode))
        val node = Single.fromPublisher(pu.findByProperty(Property.ID.key, pNode.properties[Property.ID.key] as String))
        return insert.flatMap { PNode.simple(node, pu) }
    }

}