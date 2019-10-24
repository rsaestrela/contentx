package contentx.core

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import kotlinx.collections.immutable.PersistentMap

interface Node {

    fun id(): String

    fun name(): String

    fun properties(): Map<String, Any>

    fun path(): Single<String>

    fun parent(): Maybe<Node?>

    fun children(): Flowable<Node>

    fun addChild(name: String, properties: PersistentMap<String, Any>): Single<Node>

    fun putProperty(property: String, value: Any): Single<Node>

}