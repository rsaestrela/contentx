package contentx.core.persistent.unit

class MongoRepositoryCredential private constructor(
        val user: String,
        val password: String,
        val database: String,
        val collection: String) : RepositoryCredential {

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