package contentx

class ContentXLauncher {
    fun init(): Repository {
        return StateRepository()
    }
}

fun main() {
    ContentXLauncher().init()
}
