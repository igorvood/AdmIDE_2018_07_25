package ru.vood.admplugin.infrastructure.generateCode.impl.createFiles

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity
import java.nio.file.Path

@Component
class GenerateFiles {

    @Autowired
    lateinit var generateOneFile: GenerateOneFile

    @JvmOverloads
    fun genFiles(startPath: Path, tables: List<VBdTableEntity>, types: Array<TypeOfGenClass> = arrayOf(TypeOfGenClass.ENTITY_CLASS)): List<Path> {
        if ((tables.size * types.size) == 0) {
            return ArrayList<Path>()
        }
        var listPair = ArrayList<PairTableAndTypeOfGenClass>(tables.size * types.size)
        for (tab in tables) {
            for (typ in types) {
                listPair.add(PairTableAndTypeOfGenClass(tab, typ))
            }
        }

        val arrayList = ArrayList<Path>()
        for (lp in listPair) {
            val path = generateOneFile.getFile(startPath, lp.key, lp.value)
            arrayList.add(path!!)
        }
        return arrayList
    }
}