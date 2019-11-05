package contentx.core

class MongoRepositoryCredential private constructor(
        private val user: String,
        private val password: String,
        private val database: String,
        private val collection: String) : RepositoryCredential {

    override fun user(): String {
        return user
    }

    override fun password(): String {
        return password
    }

    override fun database(): String {
        return database
    }

    override fun collection(): String {
        return collection
    }

    data class Builder(
            var user: String = "",
            var password: String = "",
            var database: String = "",
            var collection: String = "") {
        fun user(user: String) = apply { this.user = user }
        fun password(password: String) = apply { this.password = password }
        fun database(database: String) = apply { this.database = database }
        fun collection(collection: String) = apply { this.collection = collection }
        fun build() = MongoRepositoryCredential(user, password, database, collection)
    }

}