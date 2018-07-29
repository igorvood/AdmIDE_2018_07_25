package ru.vood.admplugin.infrastructure.generateCode.impl.createFiles

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.GenCodeCommonFunctionKT
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenAnyPartKT
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption


@Component

class GenerateOneFile {


    //    @Autowired
//    @Qualifier("genClassServiceKT")
    var genAnyPartKT: GenAnyPartKT<VBdTableEntity>

    //    @Autowired
    var genCodeCommonFunctionKT: GenCodeCommonFunctionKT

    @Autowired
    constructor(@Qualifier("genClassServiceKT")
                genAnyPartKT: GenAnyPartKT<VBdTableEntity>,

                genCodeCommonFunctionKT: GenCodeCommonFunctionKT) {
        this.genAnyPartKT = genAnyPartKT
        this.genCodeCommonFunctionKT = genCodeCommonFunctionKT
    }


    @JvmOverloads
    fun getFile(startPath: Path, tableEntity: VBdTableEntity, typeOfGenClass: TypeOfGenClass = TypeOfGenClass.ENTITY_CLASS): Path? {
        val packageName = genCodeCommonFunctionKT.getPackageName(typeOfGenClass)
        val generatedClass = genAnyPartKT.genCode(tableEntity, typeOfGenClass)
        val path = Paths.get(createDirs(startPath, packageName.toString()).toString() + "\\" + genCodeCommonFunctionKT.getClassName(tableEntity, typeOfGenClass) + ".kt")
        val retPath = Files.write(path, generatedClass.lines(), Charset.forName("UTF-8"), StandardOpenOption.CREATE)
        return retPath
    }

    private fun createDirs(startPath: Path, packageName: String): Path {
        //val beginDir = "C:\\temp\\1" //todo убрать это позже,
        val beginDir = startPath.toString() //todo убрать это позже,
        val dirs = beginDir + "\\" + packageName.replace(".", "\\")// .split(".")
        val dir = Files.createDirectories(Paths.get(dirs))
        return dir
    }

}