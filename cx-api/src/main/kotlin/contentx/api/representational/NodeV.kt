package contentx.api.representational

import com.fasterxml.jackson.annotation.JsonProperty
import contentx.core.Node
import io.reactivex.Single
import ratpack.handling.Context
import ratpack.http.Status
import ratpack.rx2.RxRatpack

class NodeV {

    @JsonProperty
    lateinit var id: String

    @JsonProperty
    lateinit var name: String

    @JsonProperty
    var properties: Map<String, Any> = hashMapOf()

    @JsonProperty
    var children: List<NodeV> = arrayListOf()

    fun representational(context: Context, sNode: Single<out Node>): Single<NodeV> {
        val nodeV = NodeV()
        RxRatpack.promise(sNode).onError { context.response.status(Status.NOT_FOUND).send() }
                .then { node ->
                    nodeV.id = node.id()
                    nodeV.name = node.name()
                    nodeV.properties = node.properties()
                    RxRatpack.promiseAll(node.children().toObservable())
                            .then { nodes ->
                                nodes.forEach { n ->
                                    RxRatpack.promise(this.representational(context, Single.just(n))).then { t ->
                                        nodeV.addChild(t)
                                    }
                                }
                            }
                }
        return Single.just(nodeV)
    }

    private fun addChild(nodeV: NodeV) {
        children = children.plus(nodeV)
    }

}