package contentx.core.state

import contentx.core.Node
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toFlowable
import java.util.*

abstract class AbstractStateNode() : Node {

    protected val id: String = UUID.randomUUID().toString()

    private lateinit var name: String

    private var parent: Node? = null

    protected var children: List<Node> = arrayListOf()

    private lateinit var properties: Map<String, Any>

    constructor(name: String,
                parent: Node?,
                properties: Map<String, Any>) : this() {
        this.name = name
        this.parent = parent
        this.properties = properties
    }

    override fun name(): String {
        return name
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

    override fun putProperty(property: String, value: Any): Maybe<Node> {
        properties = properties.plus(Pair(property, value))
        return Maybe.just(this)
    }

}