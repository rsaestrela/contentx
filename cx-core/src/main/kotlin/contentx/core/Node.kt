package contentx.core

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import kotlinx.collections.immutable.PersistentMap

interface Node {

    fun id(): Single<String>

    fun name(): Single<String>

    fun path(): Single<String>

    fun parent(): Maybe<Node?>

    fun children(): Flowable<Node>

    fun addChild(name: String, properties: PersistentMap<String, Any>): Single<Node>

    fun properties(): Single<PersistentMap<String, Any>>

    fun putProperty(property: String, value: Any): Single<Node>

}