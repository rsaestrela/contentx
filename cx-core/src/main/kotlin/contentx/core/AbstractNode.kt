package contentx.core

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toFlowable
import kotlinx.collections.immutable.PersistentMap
import java.util.*

abstract class AbstractNode() : Node {

    private var parent: Node? = null

    protected var children: List<Node> = arrayListOf()

    private lateinit var properties: PersistentMap<String, Any>

    constructor(name: String,
                parent: Node?,
                properties: PersistentMap<String, Any>) : this() {
        this.parent = parent
        this.properties = initProperties(properties, name)
    }

    private fun initProperties(properties: PersistentMap<String, Any>, name: String): PersistentMap<String, Any> {
        return properties.put("id", UUID.randomUUID().toString()).put("name", name)
    }

    override fun name(): Single<String> {
        return Single.just(properties["name"] as String)
    }

    override fun path(): Single<String> {
        return parent!!.path().zipWith(name(), BiFunction { p, t -> p.plus("/").plus(t) })
    }

    override fun parent(): Maybe<Node?> {
        return Maybe.just(parent)
    }

    override fun children(): Flowable<Node> {
        return children.toFlowable()
    }

    override fun properties(): Single<PersistentMap<String, Any>> {
        return Single.just(properties)
    }

    override fun putProperty(property: String, value: Any): Single<Node> {
        properties = properties.put(property, value)
        return Single.just(this)
    }

}