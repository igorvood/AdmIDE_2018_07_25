package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.message

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import java.lang.reflect.Type

@Component
class AddingClassPublisher : ApplicationContextAware {

    private lateinit var ctx: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        ctx = applicationContext
    }

    fun publish(source: Any, type: Type) {
        publish(source, type.toString().substringAfterLast(" "))
    }

    fun publish(source: Any, fullNameClass: String) {
        ctx.publishEvent(AddImportEvent(source, fullNameClass))
    }

}