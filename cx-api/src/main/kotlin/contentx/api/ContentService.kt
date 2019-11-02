package contentx.api

import contentx.core.Node
import io.reactivex.Maybe
import ratpack.handling.Context

interface ContentService {

    fun getContent(context: Context, uri: String): Maybe<out Node>

}
