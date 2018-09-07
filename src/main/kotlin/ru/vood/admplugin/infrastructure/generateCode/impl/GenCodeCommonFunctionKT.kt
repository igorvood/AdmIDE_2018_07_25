package ru.vood.admplugin.infrastructure.generateCode.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity
import ru.vood.admplugin.infrastructure.spring.referenceBook.RootObjects
import ru.vood.admplugin.infrastructure.tune.PluginTunes

@Component
class GenCodeCommonFunctionKT(@Autowired
                              val pluginTunes: PluginTunes) {

    fun getTableName(entity: VBdObjectEntity): StringBuilder {
        return StringBuilder((pluginTunes.prefixTable + entity.code).toUpperCase())
    }

    @JvmOverloads
    fun getClassName(entity: VBdObjectEntity
                     , typeOfGenClassKT: TypeOfGenClass = TypeOfGenClass.ENTITY_CLASS): StringBuilder {
        return StringBuilder(toCamelCase(getTableName(entity).toString() + "_" + typeOfGenClassKT)!!)
    }

    @JvmOverloads
    fun getParametrName(entity: VBdObjectEntity
                        , typeOfGenClassKT: TypeOfGenClass = TypeOfGenClass.ENTITY_CLASS): StringBuilder {
        val parameterName = getClassName(entity, typeOfGenClassKT)
        return StringBuilder(parameterName.get(0).toLowerCase() + parameterName.substring(1) + "Val")
    }

    fun getPackageName(typeOfGenClassKT: TypeOfGenClass): StringBuilder {
        return StringBuilder(pluginTunes.packageIn + "." + typeOfGenClassKT.toString().toLowerCase())
    }

    @JvmOverloads
    fun getFullClassName(entity: VBdObjectEntity
                         , typeOfGenClassKT: TypeOfGenClass = TypeOfGenClass.ENTITY_CLASS): StringBuilder {
        val code = StringBuilder(getPackageName(typeOfGenClassKT)).append(".")
        return code.append(getClassName(entity, typeOfGenClassKT))
    }

/*
    @Deprecated("move to fields")
    fun getIdField(): StringBuilder = StringBuilder("    @Id\n" +
            "    @GenericGenerator(name = \"seqId\", strategy = \"ru.vood.admplugin.infrastructure.spring.entity.GeneratorId\")\n" +
            "    @GeneratedValue(generator = \"seqId\")\n" +
            "    @Column(name = \"ID\", nullable = false, precision = 0)\n" +
            "    private BigDecimal id;\n")
*/

    fun genFieldName(entity: VBdObjectEntity): StringBuilder {
        val s = toCamelCase(entity.code)
        val res = StringBuilder()
        res.append(s!!.get(0).toLowerCase()).append(s.substring(1))
        return res
    }

    @JvmOverloads
    fun isRootEntity(
            entity: VBdObjectEntity,
            typeOfGenClassKT: TypeOfGenClass = TypeOfGenClass.ENTITY_CLASS
    ): Boolean {
        return if (typeOfGenClassKT == TypeOfGenClass.ENTITY_CLASS) {
            entity.parent != null && RootObjects.isRoot(entity.parent) /*&& entity.parent.typeObject.equals(ObjectTypes.getTABLE())*/
        } else false
    }

    @JvmOverloads
    fun getExtendsClassName(entity: VBdObjectEntity, typeOfGenClassKT: TypeOfGenClass = TypeOfGenClass.ENTITY_CLASS): StringBuilder {
        val code = StringBuilder("")
        return if (!isRootEntity(entity, typeOfGenClassKT)) {
            code.append(" : ").append(getFullClassName(entity.parent, typeOfGenClassKT)).append("()")
        } else code
    }


    fun toCamelCase(s: String?): StringBuilder? {
        if (s == null)
            return null

        val ret = StringBuilder(s.length)

        for (word in s.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase())
                ret.append(word.substring(1).toLowerCase())
            }
        }
        return ret
    }

}