package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateBody

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateMethod.GenerateSpecificMethodService
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

@Component
class GenerateServiceBodyKotlinCode : GenerateServiceBodyService {

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


    override fun genCode(entity: VBdObjectEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        val ret = StringBuilder()
        //ret.append(generateSaveMethod.genCode(entity, typeOfGenClass).append("\n\n"))
        ret.append(generateSaveListMethod.genCode(entity, typeOfGenClass).append("\n\n"))

        //ret.append(generateDeleteMethod.genCode(entity, typeOfGenClass).append("\n\n"))
        ret.append(generateDeleteByIdMethod.genCode(entity, typeOfGenClass).append("\n\n"))
        ret.append(generateDeleteAllMethod.genCode(entity, typeOfGenClass).append("\n\n"))

        //ret.append(generateFindByIdMethod.genCode(entity, typeOfGenClass).append("\n\n"))
        //ret.append(generateFindAllMethod.genCode(entity, typeOfGenClass).append("\n\n"))

        return ret
    }
}