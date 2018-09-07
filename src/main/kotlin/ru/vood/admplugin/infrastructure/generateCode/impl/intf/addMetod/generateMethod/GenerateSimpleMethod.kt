package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.generateMethod

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.GenCodeCommonFunctionKT
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.GenerateMethodService
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.InnerParameters
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod.WrappedType
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

@Component
class GenerateSimpleMethod : GenerateSimpleMethodService {
    @Autowired
    lateinit var generateMethodService: GenerateMethodService

    @Autowired
    lateinit var genCodeCommonFunctionKT: GenCodeCommonFunctionKT

    override fun genCode(bdClass: VBdObjectEntity, typeOfGenClass: TypeOfGenClass, nameMethod: String, retType: WrappedType, inType: WrappedType?): StringBuilder {
        val nameParam = StringBuilder()
        val innerParameters = InnerParameters()
        if (inType != null) {
            nameParam.append("entity")
            innerParameters.addOne(nameParam.toString(), inType)
        }

        var headMethod =
                when (typeOfGenClass) {
                    TypeOfGenClass.SERVICE_CLASS -> generateMethodService.genCode(retType, nameMethod, innerParameters)
                    TypeOfGenClass.IMPL_CLASS -> generateMethodService.genCode(retType, nameMethod, innerParameters)
                    else -> StringBuilder()
                }

        val autowiredParameter = genCodeCommonFunctionKT.getParametrName(bdClass, TypeOfGenClass.SERVICE_CLASS)

        val bodyMeth = when (typeOfGenClass) {
            TypeOfGenClass.IMPL_CLASS -> StringBuilder()
                    .append("{")
                    .append("return ${autowiredParameter}.${nameMethod}(${nameParam})")
                    .append("}")
            else -> StringBuilder()
        }

        return if (typeOfGenClass == TypeOfGenClass.IMPL_CLASS) StringBuilder().append(" override ").append(headMethod.append(bodyMeth))
        else headMethod.append(bodyMeth)
    }
}