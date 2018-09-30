package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod

import org.junit.Test
import ru.vood.admplugin.BaseTest
import ru.vood.admplugin.infrastructure.generateCode.impl.GenClassServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import java.math.BigDecimal

class GenerateTypeTest : BaseTest() {

    @Test
    fun getCode() {
        val generateType = ctx.getBean(GenerateTypeService::class.java)
        //val generateSpecificMethodService = ctx.getBean(GenerateSpecificMethodService::class.java)
        val genClassServiceKT = ctx.getBean(GenClassServiceKT::class.java)


        println(generateType.getCode(WrappedType(BigDecimal::class.java)))
        println(generateType.getCode(WrappedType(BigDecimal::class.java, WrapperClass.LIST_WRAPPER)))

        println("======================================================")


/*
        println(generateSpecificMethodService.genCode(tableEntity, TypeOfGenClass.IMPL_CLASS))
        println(generateSpecificMethodService.genCode(tableEntity, TypeOfGenClass.SERVICE_CLASS))
        println(generateSpecificMethodService.genCode(tableEntity, TypeOfGenClass.REPOSITORY_CLASS))
*/

        //println(genClassServiceKT.genCode(tableEntity, TypeOfGenClass.REPOSITORY_CLASS))
        println(genClassServiceKT.genCode(tableEntity, TypeOfGenClass.SERVICE_CLASS))
        //println(genClassServiceKT.genCode(tableEntity, TypeOfGenClass.IMPL_CLASS))


    }
}