package contentx.core

import io.reactivex.Maybe
import io.reactivex.Single

abstract class AbstractRepository : Repository {

    protected fun resolve(from: Single<out Node>, path: String): Maybe<out Node> {
        return NodeResolver().resolve(from, path)
    }

    private class NodeResolver {

        fun resolve(from: Single<out Node>, path: String): Maybe<out Node> {
            val parts = path.split("/").filter { p -> p.isNotEmpty() && p != CxConstant.ROOT.v }
            return find(Maybe.fromSingle(from), 0, parts)
        }

        private fun find(parent: Maybe<out Node>, index: Int, parts: List<String>): Maybe<out Node> {
            if (index > parts.size - 1 || parts.isEmpty()) {
                return parent
            }
            val childId = parts[index]
            val child = findChild(parent, childId)
            return child.flatMap { c -> find(Maybe.just(c), index.inc(), parts) }
        }

        private fun findChild(parent: Maybe<out Node>, childId: String): Maybe<Node> {
            return parent.flatMapPublisher { r -> r.children() }
                    .filter { c -> c.id() == childId }
                    .firstElement()
        }

    }

}