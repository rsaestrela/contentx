package contentx.core.persistent

import contentx.core.Node
import contentx.core.Property
import contentx.core.Repository
import contentx.core.RepositoryRoot
import contentx.core.persistent.PNode.Companion.newRoot
import contentx.core.persistent.unit.MongoPersistenceUnit
import contentx.core.persistent.unit.MongoRepositoryCredential
import contentx.core.persistent.unit.PersistenceUnit
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

class PersistentRepository(repositoryCredential: MongoRepositoryCredential) : Repository {

    private val pu: PersistenceUnit = MongoPersistenceUnit(repositoryCredential)

    override fun root(): Single<RepositoryRoot> {
        val root = Flowable.fromPublisher(pu.findByProperty(Property.NAME.key, Property.ROOT.key)).firstElement()
        return root.switchIfEmpty(Single.fromPublisher(pu.insert(newRoot()))).flatMap { x -> PNode.fromPNode(x, pu) }
    }

    override fun resolve(path: String): Maybe<Node> {
        return Maybe.error(NotImplementedError())
    }

}