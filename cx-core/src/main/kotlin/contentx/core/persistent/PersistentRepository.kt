package contentx.core.persistent

import contentx.core.AbstractRepository
import contentx.core.CxConstant
import contentx.core.Node
import contentx.core.RepositoryRoot
import contentx.core.persistent.PNode.Companion.newRoot
import contentx.core.persistent.unit.MongoPersistenceUnit
import contentx.core.persistent.unit.MongoRepositoryCredential
import contentx.core.persistent.unit.PersistenceUnit
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

class PersistentRepository(repositoryCredential: MongoRepositoryCredential) : AbstractRepository() {

    private val pu: PersistenceUnit = MongoPersistenceUnit(repositoryCredential)

    override fun root(): Single<RepositoryRoot> {
        val root = Flowable.fromPublisher(pu.findByProperty(CxConstant.NAME.v, CxConstant.ROOT.v)).firstElement()
        return root.switchIfEmpty(Single.fromPublisher(pu.insert(newRoot()))).flatMap { x -> PNode.fromPNode(x, pu) }
    }

    override fun resolve(path: String): Maybe<out Node> {
        return super.resolve(root(), path)
    }

}