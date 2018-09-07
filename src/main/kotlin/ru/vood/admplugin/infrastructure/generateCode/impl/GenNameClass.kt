package ru.vood.admplugin.infrastructure.generateCode.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenNameClassService
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddAnyClassService
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddJavaClassToImport
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity
import ru.vood.amdWeb.util.EntityInterface
import ru.vood.amdWeb.util.abstraction.AbstractEditorKT
import ru.vood.amdWeb.util.abstraction.AbstractGridKT
import ru.vood.amdWeb.util.abstraction.RepositoryForView
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
            TypeOfGenClass.EDITOR_CLASS -> predicate = "class "
            TypeOfGenClass.GRID_CLASS -> predicate = "class "
        }


        if (typeOfGenClass == TypeOfGenClass.ENTITY_CLASS) {


        }
        //Формирование класса родителя для сущности
        if (typeOfGenClass == TypeOfGenClass.ENTITY_CLASS) {
            if (!genCodeCommonFunction.isRootEntity(entity))
                extend.append(genCodeCommonFunction.getExtendsClassName(entity, typeOfGenClass))
            else {
                addJavaClassToImport.getCode(EntityInterface::class.java)
                extend.append(": EntityInterface ")
            }
        }


        //Формирование класса родителя для имплементации сервиса
        if (typeOfGenClass == TypeOfGenClass.IMPL_CLASS) {
            extend.append(" : ")
                    .append(addAnyClassService.getFullNameClass(entity, TypeOfGenClass.SERVICE_CLASS))
        }

        //Формирование класса родителя для класса сервиса
        if (typeOfGenClass == TypeOfGenClass.SERVICE_CLASS) {
            addJavaClassToImport.getCode(RepositoryForView::class.java)
            addJavaClassToImport.getCode(BigDecimal::class.java)
            addAnyClassService.getCode(entity, TypeOfGenClass.ENTITY_CLASS)
            extend.append(" : ")
                    .append("RepositoryForView<${genCodeCommonFunction.getClassName(entity, TypeOfGenClass.ENTITY_CLASS)}, BigDecimal>")
        }


        //Формирование класса родителя для класса репозитория
        if (typeOfGenClass == TypeOfGenClass.REPOSITORY_CLASS) {
            addJavaClassToImport.getCode(JpaRepository::class.java)
            addJavaClassToImport.getCode(BigDecimal::class.java)
            addAnyClassService.getCode(entity, TypeOfGenClass.ENTITY_CLASS)
            extend.append(" : ")
                    .append("JpaRepository<${genCodeCommonFunction.getClassName(entity, TypeOfGenClass.ENTITY_CLASS)}, BigDecimal>")
        }

        //Формирование класса родителя для класса редактора
        if (typeOfGenClass == TypeOfGenClass.EDITOR_CLASS) {
            addAnyClassService.getCode(entity, TypeOfGenClass.ENTITY_CLASS)
            addAnyClassService.getCode(entity, TypeOfGenClass.SERVICE_CLASS)
            addJavaClassToImport.getCode(AbstractEditorKT::class.java)
            extend.append(" : ")
                    .append("AbstractEditorKT<")
                    .append(genCodeCommonFunction.getClassName(entity, TypeOfGenClass.ENTITY_CLASS))
                    .append(", ")
                    .append(genCodeCommonFunction.getClassName(entity, TypeOfGenClass.SERVICE_CLASS))
                    .append(">")
        }

        //Формирование класса родителя для класса таблицы
        if (typeOfGenClass == TypeOfGenClass.GRID_CLASS) {
            //(repo: CustomerRepository, editor: TestEditor1)


            addAnyClassService.getCode(entity, TypeOfGenClass.ENTITY_CLASS)
            addAnyClassService.getCode(entity, TypeOfGenClass.SERVICE_CLASS)
            addAnyClassService.getCode(entity, TypeOfGenClass.EDITOR_CLASS)
            addJavaClassToImport.getCode(AbstractGridKT::class.java)
            //формирование параметров для конструктора
            extend.append("(")
                    .append(genCodeCommonFunction.getParametrName(entity, TypeOfGenClass.SERVICE_CLASS))
                    .append(":")
                    .append(genCodeCommonFunction.getClassName(entity, TypeOfGenClass.SERVICE_CLASS))
                    .append(", ")
                    .append(genCodeCommonFunction.getParametrName(entity, TypeOfGenClass.EDITOR_CLASS))
                    .append(":")
                    .append(genCodeCommonFunction.getClassName(entity, TypeOfGenClass.EDITOR_CLASS))
                    .append(")\n")
            //формирование родителя текущего класса
            extend.append(" : ")
                    .append("AbstractGridKT<")
                    .append(genCodeCommonFunction.getClassName(entity, TypeOfGenClass.ENTITY_CLASS))
                    .append(", ")
                    .append(genCodeCommonFunction.getClassName(entity, TypeOfGenClass.SERVICE_CLASS))
                    .append(", ")
                    .append(genCodeCommonFunction.getClassName(entity, TypeOfGenClass.EDITOR_CLASS))
                    .append(">")

            //формирование парметров вызова конструктора родителя
            extend.append(" ( ")
                    .append(genCodeCommonFunction.getParametrName(entity, TypeOfGenClass.SERVICE_CLASS)).append(", ")
                    .append(genCodeCommonFunction.getClassName(entity)).append("::class.java").append(", ")
                    .append(genCodeCommonFunction.getParametrName(entity, TypeOfGenClass.EDITOR_CLASS))
                    .append(" ) ")

        }

        val clazz = "/* ${typeOfGenClass.comment} для таблицы БД - ${entity.name} */\n" +
                predicate + genCodeCommonFunction.getClassName(entity, typeOfGenClass) + extend

        return StringBuilder(clazz)
    }
}