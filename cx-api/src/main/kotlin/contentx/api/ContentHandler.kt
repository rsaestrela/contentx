package contentx.api

import contentx.api.representational.RepositoryTree
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.jackson.Jackson
import ratpack.rx2.RxRatpack
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentHandler @Inject constructor(private val contentService: ContentService) : Handler {

    override fun handle(context: Context) {
        RxRatpack.promise(contentService.getContent())
                .map { r -> RepositoryTree(r) }
                .then { u -> context.render(Jackson.json(u)) }
    }

}
