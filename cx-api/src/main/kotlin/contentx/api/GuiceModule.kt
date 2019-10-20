package contentx.api

import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import contentx.core.Repository
import contentx.core.StateRepository
import ratpack.handling.HandlerDecorator

class GuiceModule : AbstractModule() {

    override fun configure() {
        bind(Repository::class.java).to(StateRepository::class.java)
        bind(ContentService::class.java).to(ContentServiceImpl::class.java)
        bind(ContentHandler::class.java)
        Multibinder.newSetBinder(binder(), HandlerDecorator::class.java)
                .addBinding()
                .toInstance(HandlerDecorator.prepend(LoggingHandler()))
    }
}
