package ru.vood.admplugin.infrastructure.generateCode.impl.intf.addMetod

import com.sun.org.apache.xpath.internal.operations.String

interface GenerateMethodService/*<Q : VBdObjectEntity>*/ /*: GenAnyPartKT<VBdTableEntity>*/ {
    fun genCode(retType : WrappedType, nameMethod: String, innerParameters: InnerParameters): StringBuilder

}


