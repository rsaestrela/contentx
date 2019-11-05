package contentx.core

import contentx.core.PNode.Companion.newRoot
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

class CxRepository constructor(persistenceUnit: PersistenceUnit) : Repository {

    private val pu: PersistenceUnit = persistenceUnit

    companion object {
        fun get(repositoryType: RepositoryType): Repository {
            return when (repositoryType) {
                RepositoryType.MAP -> CxRepository(MapPersistenceUnit())
                else -> throw UnsupportedRepositoryException()
            }
        }

        fun get(repositoryType: RepositoryType, repositoryCredential: RepositoryCredential): CxRepository {
            return when (repositoryType) {
                RepositoryType.MONGO -> CxRepository(MongoPersistenceUnit(repositoryCredential))
                else -> throw UnsupportedRepositoryException()
            }
        }
    }

    override fun root(): Single<RepositoryRoot> {
        val root = Flowable.fromPublisher(pu.findByProperty(CxConstant.NAME.v, CxConstant.ROOT.v)).firstElement()
        return root.switchIfEmpty(Single.fromPublisher(pu.insert(newRoot()))).flatMap { x -> PNode.fromPNode(x, pu) }
    }

    override fun resolve(path: String): Maybe<out Node> {
        return NodeResolver().resolve(root(), path)
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