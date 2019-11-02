package contentx.api

import contentx.api.representational.NodeV
import io.reactivex.Single
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.jackson.Jackson
import ratpack.rx2.RxRatpack
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentHandler @Inject constructor(private val contentService: ContentService) : Handler {

    override fun handle(context: Context) {
        val nodeP = contentService.getContent(context, context.request.uri).switchIfEmpty(Single.error(NodeNotFoundException()))
        val nodeV = NodeV().representational(context, nodeP)
        RxRatpack.promise(nodeV)
                .then { json -> context.render(Jackson.json(json)) }
    }

}
