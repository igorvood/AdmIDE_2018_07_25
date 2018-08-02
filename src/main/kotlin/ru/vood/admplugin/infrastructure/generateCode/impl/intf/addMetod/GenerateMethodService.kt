package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod


interface GenerateMethodService/*<Q : VBdObjectEntity>*/ /*: GenAnyPartKT<VBdTableEntity>*/ {
    fun genCode(retType: WrappedType, nameMethod: String, innerParameters: InnerParameters): StringBuilder

}


