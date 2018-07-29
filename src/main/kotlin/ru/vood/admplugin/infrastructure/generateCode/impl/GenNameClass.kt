package ru.vood.admplugin.infrastructure.generateCode.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenNameClassService
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddAnyClassService
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddJavaClassToImport
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity
import java.math.BigDecimal

@Component
class GenNameClass(@Autowired
                   val genCodeCommonFunction: GenCodeCommonFunctionKT,
                   val addAnyClassService: AddAnyClassService,
                   val addJavaClassToImport: AddJavaClassToImport
) : GenNameClassService {

    override fun genCode(entity: VBdTableEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        var extend = StringBuilder()
        var predicate: String
        when (typeOfGenClass) {
            TypeOfGenClass.ENTITY_CLASS -> predicate = "open class "
            TypeOfGenClass.IMPL_CLASS -> predicate = "open class "
            TypeOfGenClass.SERVICE_CLASS -> predicate = "interface "
            TypeOfGenClass.REPOSITORY_CLASS -> predicate = "interface "
        }
        if (!genCodeCommonFunction.isRootEntity(entity) && typeOfGenClass == TypeOfGenClass.ENTITY_CLASS)
            extend.append(genCodeCommonFunction.getExtendsClassName(entity, typeOfGenClass))


        if (typeOfGenClass == TypeOfGenClass.IMPL_CLASS) {
            extend.append(" : ")
                    .append(addAnyClassService.getFullNameClass(entity, TypeOfGenClass.SERVICE_CLASS))
        }


        if (typeOfGenClass == TypeOfGenClass.REPOSITORY_CLASS) {
            addJavaClassToImport.getCode(CrudRepository::class.java)
            addJavaClassToImport.getCode(BigDecimal::class.java)
            addAnyClassService.getCode(entity, TypeOfGenClass.ENTITY_CLASS)
            extend.append(" : ")
                    .append("CrudRepository<${genCodeCommonFunction.getClassName(entity, TypeOfGenClass.ENTITY_CLASS)}, BigDecimal>")
        }

        val clazz = "/* ${typeOfGenClass.comment} для таблицы БД - ${entity.name} */\n" +
                predicate + genCodeCommonFunction.getClassName(entity, typeOfGenClass) + extend

        return StringBuilder(clazz)
    }
}