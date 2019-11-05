package contentx.core

object RepositoryCredentialUtil {

    val testingCredential: RepositoryCredential = MongoRepositoryCredential.Builder()
            .user("tester")
            .password("password")
            .database("contentx")
            .collection("data")
            .build()

}
