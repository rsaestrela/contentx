package contentx.core

import com.google.common.flogger.FluentLogger
import io.reactivex.Maybe
import io.reactivex.Single
import org.reactivestreams.Publisher

class MapPersistenceUnit : PersistenceUnit {

    private val logger = FluentLogger.forEnclosingClass()

    internal var map = mapOf<String, PNode>()

    override fun insert(pNode: PNode): Publisher<PNode> {
        logger.atInfo().log("operation=insert pNode=%s", pNode)
        map = map.plus(Pair(pNode._id, pNode))
        return Single.just(pNode).toFlowable()
    }

    override fun findByProperty(property: String, value: String): Publisher<PNode?> {
        logger.atInfo().log("operation=findByProperty prop=%s value=%s", property, value)
        if (property == CxConstant.ID.v) {
            return Maybe.just(map[value]).toFlowable()
        }
        return when (CxConstant.lookup(property)) {
            CxConstant.ID -> {
                val node = map.values.find { o -> o._id == value }
                return if (node != null) Maybe.just(node).toFlowable() else Maybe.empty<PNode?>().toFlowable()
            }
            CxConstant.NAME -> {
                val node = map.values.find { o -> o.name == value }
                return if (node != null) Maybe.just(node).toFlowable() else Maybe.empty<PNode?>().toFlowable()
            }
            CxConstant.PARENT -> {
                val node = map.values.find { o -> o.parent == value }
                return if (node != null) Maybe.just(node).toFlowable() else Maybe.empty<PNode?>().toFlowable()
            }
            else -> Maybe.empty<PNode>().toFlowable()
        }
    }

}

