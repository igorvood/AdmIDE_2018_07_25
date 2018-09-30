package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.message.AddingClassPublisher
import java.lang.reflect.Type

@Component
open class AddJavaClassToImport : AddJavaClassToImportService {
    @Autowired
    protected lateinit var addingClassPublisher: AddingClassPublisher

    override fun getFullNameClass(type: Type): String = type.toString().substringAfterLast(" ")

    override fun getCode(type: Type): String {
        addingClassPublisher.publish(this, type)
        return type.toString().substringAfterLast(".")
    }
}