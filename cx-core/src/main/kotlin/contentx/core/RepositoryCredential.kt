package contentx.core

interface RepositoryCredential {

    fun user(): String

    fun password(): String

    fun database(): String

    fun collection(): String

}