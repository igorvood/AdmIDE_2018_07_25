package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod

interface GenerateTypeService {

    fun getCode(wrappedType: WrappedType): StringBuilder
/*
    fun getCode(clazz: Type, wrapperClass: WrapperClass = WrapperClass.NO_WRAPPER): StringBuilder

    fun getCode(clazz: VBdObjectEntity, wrapperClass: WrapperClass = WrapperClass.NO_WRAPPER): StringBuilder
*/

}
