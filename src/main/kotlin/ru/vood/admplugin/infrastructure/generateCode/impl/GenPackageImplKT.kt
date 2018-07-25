package ru.vood.admplugin.infrastructure.generateCode.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenAnyPartKT
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity

@Component
class GenPackageImplKT(@Autowired
                       val genCodeCommonFunction: GenCodeCommonFunctionKT) : GenAnyPartKT<VBdObjectEntity> {

    override fun genCode(entity: VBdObjectEntity, typeOfGenClass: TypeOfGenClass): StringBuilder {
        return StringBuilder("package " + genCodeCommonFunction.getPackageName(typeOfGenClass) + ";\n\n")
    }
}