package contentx.core.state

import contentx.core.Node
import contentx.core.Property
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toFlowable
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import java.util.*

abstract class AbstractStateNode() : Node {

    private var parent: Node? = null

    protected var children: List<Node> = persistentListOf()

    private lateinit var properties: PersistentMap<String, Any>

    constructor(name: String,
                parent: Node?,
                properties: PersistentMap<String, Any>) : this() {
        this.parent = parent
        this.properties = initProperties(properties, name)
    }

    private fun initProperties(properties: PersistentMap<String, Any>, name: String): PersistentMap<String, Any> {
        return properties.put(Property.ID.key, UUID.randomUUID().toString()).put(Property.NAME.key, name)
    }

    override fun name(): String {
        return properties[Property.NAME.key] as String
    }

    override fun properties(): Map<String, Any> {
        return properties
    }

    override fun path(): Single<String> {
        return parent!!.path().zipWith(Single.just(name()), BiFunction { p, t -> p.plus("/").plus(t) })
    }

    override fun parent(): Maybe<Node?> {
        return Maybe.just(parent)
    }

    override fun children(): Flowable<Node> {
        return children.toFlowable()
    }

    override fun putProperty(property: String, value: Any): Single<Node> {
        properties = properties.put(property, value)
        return Single.just(this)
    }

}