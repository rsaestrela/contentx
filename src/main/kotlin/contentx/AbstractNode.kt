package contentx

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.rxkotlin.toFlowable
import kotlinx.collections.immutable.PersistentMap
import java.util.*

abstract class AbstractNode(private val name: String,
                            private val parent: Node?,
                            private var properties: PersistentMap<String, Any>) : Node {

    protected val id: String = UUID.randomUUID().toString()

    @Volatile
    protected var children: List<Node> = arrayListOf()

    override fun name(): Single<String> {
        return Single.just(name)
    }

    override fun path(): Single<String> {
        return parent!!.path().map { p -> p.plus("/").plus(name) }
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

    @Synchronized
    override fun putProperty(property: String, value: Any): Single<Node> {
        properties = properties.put(property, value)
        return Single.just(this)
    }

}