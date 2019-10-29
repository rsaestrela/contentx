package contentx.api

import ratpack.guice.BindingsSpec
import ratpack.guice.Guice
import ratpack.handling.Chain
import ratpack.server.BaseDir
import ratpack.server.RatpackServer
import ratpack.server.RatpackServerSpec
import ratpack.server.ServerConfigBuilder

object ContentX {

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            createServer().start()
        } catch (e: Exception) {
            // TODO Logging
        }
    }

    private fun createServer(): RatpackServer = serverOf {
        serverConfig {
            baseDir(BaseDir.find())
        }
        guiceRegistry {
            module(GuiceModule())
        }
        handlers {
            all(ContentHandler::class.java)
        }
    }

    private fun serverOf(cb: KServerSpec.() -> Unit): RatpackServer = RatpackServer.of { KServerSpec(it).cb() }

}

class KChain(private val delegate: Chain) : Chain by delegate

class KServerSpec(private val delegate: RatpackServerSpec) {
    fun serverConfig(cb: ServerConfigBuilder.() -> Unit): RatpackServerSpec = delegate.serverConfig { it.cb() }
    fun guiceRegistry(cb: BindingsSpec.() -> Unit): RatpackServerSpec =
            delegate.registry(Guice.registry { bindings: BindingsSpec -> bindings.cb() })

    fun handlers(cb: KChain.() -> Unit): RatpackServerSpec = delegate.handlers { KChain(it).cb() }
}
