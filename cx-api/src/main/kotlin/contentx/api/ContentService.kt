package contentx.api

import contentx.core.Node
import io.reactivex.Single

interface ContentService {

    fun getContent(): Single<Node>

}
