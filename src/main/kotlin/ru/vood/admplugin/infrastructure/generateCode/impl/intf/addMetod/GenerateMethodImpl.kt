package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod

import com.sun.org.apache.xpath.internal.operations.String
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GenerateMethodImpl : GenerateMethodService {
    @Autowired
    lateinit var generateTypeService: GenerateTypeService

    override fun genCode(retType: WrappedType, nameMethod: String, innerParameters: InnerParameters): StringBuilder {
        val reduce = innerParameters.asSequence()
                .map { inParam -> genInParametrWithType(inParam.value, inParam.key) }
                .reduce { s1, s2 -> s1.append(" , ").append(s2) }

        return reduce
    }

    private fun genInParametrWithType(inParametr: WrappedType, nameParametr: kotlin.String): StringBuilder {
        val ret = StringBuilder().append(nameParametr)
                .append(" : ")
                .append(generateTypeService.getCode(inParametr))
        return ret
    }
}