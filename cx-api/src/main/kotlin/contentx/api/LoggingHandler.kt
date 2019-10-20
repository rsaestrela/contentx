package contentx.api

import ratpack.handling.Context
import ratpack.handling.Handler

class LoggingHandler : Handler {

    override fun handle(context: Context) {
        println("Received: ${context.request.uri}")
        context.next()
    }

}
