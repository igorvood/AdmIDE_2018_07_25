package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateBody

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.GenCodeCommonFunctionKT
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateMethod.GenerateSpecificMethodService
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddAnyClassService
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.AddJavaClassToImportService
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

@Component
class GenerateImplementationBodyKotlinCode : GenerateServiceBodyService {
    @Autowired
    @Qualifier("generateSaveMethod")
    lateinit var generateSaveMethod: GenerateSpecificMethodService
    @Autowired
    @Qualifier("generateSaveListMethod")
    lateinit var generateSaveListMethod: GenerateSpecificMethodService

    @Autowired
    @Qualifier("generateDeleteMethod")
    lateinit var generateDeleteMethod: GenerateSpecificMethodService
    @Autowired
    @Qualifier("generateDeleteByIdMethod")
    lateinit var generateDeleteByIdMethod: GenerateSpecificMethodService
    @Autowired
    @Qualifier("generateDeleteAllMethod")
    lateinit var generateDeleteAllMethod: GenerateSpecificMethodService

    @Autowired
    @Qualifier("generateFindByIdMethod")
    lateinit var generateFindByIdMethod: GenerateSpecificMethodService

    @Autowired
    @Qualifier("generateFindAllMethod")
    lateinit var generateFindAllMethod: GenerateSpecificMethodService

    @Autowired
    lateinit var genCodeCommonFunctionKT: GenCodeCommonFunctionKT

    @Autowired
    @Qualifier("addAnnotationClass")
    lateinit var addAnnotationClass: AddJavaClassToImportService

    @Autowired
    //@Qualifier("addJavaClassToImport")
    lateinit var addAnyClassService: AddAnyClassService

    override fun genCode(entity: VBdObjectEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        val ret = genFields(entity)

        ret.append(generateSaveMethod.genCode(entity, typeOfGenClass).append("\n\n"))
        ret.append(generateSaveListMethod.genCode(entity, typeOfGenClass).append("\n\n"))

        ret.append(generateDeleteMethod.genCode(entity, typeOfGenClass).append("\n\n"))
        ret.append(generateDeleteByIdMethod.genCode(entity, typeOfGenClass).append("\n\n"))
        ret.append(generateDeleteAllMethod.genCode(entity, typeOfGenClass).append("\n\n"))

        ret.append(generateFindByIdMethod.genCode(entity, typeOfGenClass).append("\n\n"))
        ret.append(generateFindAllMethod.genCode(entity, typeOfGenClass).append("\n\n"))

        return ret
    }

    private fun genFields(entity: VBdObjectEntity): StringBuilder {
        val ret = StringBuilder()
        addAnyClassService.getCode(entity, TypeOfGenClass.SERVICE_CLASS)
        ret.append(addAnnotationClass.getCode(Autowired::class.java))
        ret.append("lateinit var ")
        ret.append(genCodeCommonFunctionKT.getParametrName(entity, TypeOfGenClass.SERVICE_CLASS)).append(" : ")
        ret.append(genCodeCommonFunctionKT.getClassName(entity, TypeOfGenClass.SERVICE_CLASS)).append("\n\n")

        return ret
    }

}