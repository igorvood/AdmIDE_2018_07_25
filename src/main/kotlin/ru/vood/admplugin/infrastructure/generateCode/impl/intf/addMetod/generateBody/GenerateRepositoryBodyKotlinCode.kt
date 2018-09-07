package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateBody

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateMethod.GenerateSpecificMethodService
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

@Component
class GenerateRepositoryBodyKotlinCode : GenerateServiceBodyService {
    @Autowired
    @Qualifier("generateSaveMethod")
    lateinit var generateSaveMethod: GenerateSpecificMethodService

    @Autowired
    @Qualifier("generateSaveListMethod")
    lateinit var generateSaveListMethod: GenerateSpecificMethodService


    override fun genCode(entity: VBdObjectEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        return StringBuilder()
    }

}