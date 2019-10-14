package contentx

class StateRepository : Repository {

    override fun root(): Node {
        return SimpleNode()
    }

}