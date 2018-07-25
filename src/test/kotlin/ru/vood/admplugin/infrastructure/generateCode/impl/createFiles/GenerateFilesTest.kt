package ru.vood.admplugin.infrastructure.generateCode.impl.createFiles

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import ru.vood.admplugin.infrastructure.generateCode.impl.GenCodeCommonFunctionKT
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenAnyPartKT
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity
import java.nio.file.Paths

@RunWith(SpringRunner::class)
class GenerateFilesTest {

    @MockBean
    lateinit var genCodeCommonFunctionKT: GenCodeCommonFunctionKT

    @MockBean
    lateinit var genAnyPartKT: GenAnyPartKT<VBdTableEntity>

    @MockBean
    lateinit var generateOneFile: GenerateOneFile


    @Test
    fun genFiles() {
        var tables: List<VBdTableEntity>
        var t1 = VBdTableEntity()
        t1.code = "t1"
        t1.name = t1.code + " name "
        var t2 = VBdTableEntity()
        t2.code = "t2"
        t2.name = t2.code + " name "

        tables = arrayListOf(t1, t2)
        //var pare = Pair<VBdTableEntity, TypeOfGenClass>(t1, TypeOfGenClass.ENTITY_CLASS)

        var types: Array<TypeOfGenClass> = arrayOf(TypeOfGenClass.ENTITY_CLASS, TypeOfGenClass.IMPL_CLASS)
        var listPair = ArrayList<PairTableAndTypeOfGenClass>(tables.size * types.size)


        val vBdTableEntity = VBdTableEntity()
        vBdTableEntity.code = "TTT_QQQ"

        Mockito.`when`(genAnyPartKT.genCode(vBdTableEntity, TypeOfGenClass.ENTITY_CLASS)).thenReturn(StringBuilder("this is code"))
        Mockito.`when`(genCodeCommonFunctionKT.getPackageName(TypeOfGenClass.ENTITY_CLASS)).thenReturn(StringBuilder("ru.vood." + TypeOfGenClass.ENTITY_CLASS))
        Mockito.`when`(genCodeCommonFunctionKT.getPackageName(TypeOfGenClass.ENTITY_CLASS)).thenReturn(StringBuilder("ru.vood." + TypeOfGenClass.IMPL_CLASS))
        Mockito.`when`(genCodeCommonFunctionKT.getClassName(t1, TypeOfGenClass.ENTITY_CLASS)).thenReturn(StringBuilder(t1.code))
        Mockito.`when`(genCodeCommonFunctionKT.getClassName(t2, TypeOfGenClass.ENTITY_CLASS)).thenReturn(StringBuilder(t2.code))
        Mockito.`when`(genCodeCommonFunctionKT.getClassName(t1, TypeOfGenClass.IMPL_CLASS)).thenReturn(StringBuilder(t1.code))
        Mockito.`when`(genCodeCommonFunctionKT.getClassName(t2, TypeOfGenClass.IMPL_CLASS)).thenReturn(StringBuilder(t2.code))

        val generateFile = GenerateOneFile(genAnyPartKT, genCodeCommonFunctionKT)
        val path = Paths.get("C:\\temp\\1")
        //generateFile.getFile(path, vBdTableEntity)

        Mockito.`when`(generateOneFile.getFile(path, t1, TypeOfGenClass.ENTITY_CLASS)).thenReturn(generateFile.getFile(path, t1, TypeOfGenClass.ENTITY_CLASS))
        Mockito.`when`(generateOneFile.getFile(path, t2, TypeOfGenClass.ENTITY_CLASS)).thenReturn(generateFile.getFile(path, t2, TypeOfGenClass.ENTITY_CLASS))
        Mockito.`when`(generateOneFile.getFile(path, t1, TypeOfGenClass.ENTITY_CLASS)).thenReturn(generateFile.getFile(path, t1, TypeOfGenClass.IMPL_CLASS))
        Mockito.`when`(generateOneFile.getFile(path, t2, TypeOfGenClass.ENTITY_CLASS)).thenReturn(generateFile.getFile(path, t2, TypeOfGenClass.IMPL_CLASS))

        val generateFilesTest = GenerateFiles()
        val res = generateFilesTest.genFiles(path, tables, types)

        println(res)


/*
        for (tab in tables) {
            //println(tab)
            for (typ in types) {
                listPair.add(PairTableAndTypeOfGenClass(tab, typ))
            }
        }
*/

        println(listPair.toString())
    }

}
