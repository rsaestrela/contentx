package contentx.core.state

import contentx.core.Node
import contentx.core.Repository
import contentx.core.RepositoryRoot
import io.reactivex.Maybe
import io.reactivex.Single

class StateRepository : Repository {

    override fun root(): Single<RepositoryRoot> {
        val root = RootStateNode()
        return Single.just(root)
    }

    override fun resolve(path: String): Maybe<Node> {
        val parts = path.split("/").filter { p -> p.isNotEmpty() && p != "root" }
        return find(Maybe.fromSingle(root()), parts.size - 1, parts)
    }

    private fun find(parent: Maybe<out Node>, index: Int, parts: List<String>): Maybe<Node> {
        if (index < 0 || parts.isEmpty()) {
            return Maybe.empty()
        }
        val part = parts[index]
        val child = findChild(parent, part)
        return child.switchIfEmpty(find(child, index.dec(), parts))
    }

    private fun findChild(parent: Maybe<out Node>, part: String): Maybe<Node> {
        return parent.flatMapPublisher { r -> r.children() }
                .filter { c -> c.id() == part }
                .firstElement()
    }

}