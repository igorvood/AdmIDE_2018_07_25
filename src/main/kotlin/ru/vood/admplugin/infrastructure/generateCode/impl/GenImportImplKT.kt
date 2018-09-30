package ru.vood.admplugin.infrastructure.generateCode.impl

import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.GenImportServiceKT
import ru.vood.admplugin.infrastructure.generateCode.impl.intf.addingImport.message.AddImportEvent

@Component
class GenImportImplKT : GenImportServiceKT, ApplicationListener<AddImportEvent> {

    private var listImports: HashSet<String> = HashSet()


    override fun genCode(): StringBuilder {
        if (listImports.size > 0) {
            var par = StringBuilder(listImports.asSequence()
                    .sorted()
                    .map { q -> getOneImport(q) }
                    .reduce { s1, s2 -> s1 + s2 })
            return par.append("\n\n")
        }
        return StringBuilder("")
    }

    private fun getOneImport(fullNameClass: String) = "import $fullNameClass;\n"

    override fun onApplicationEvent(event: AddImportEvent) {
        if (listImports == null) listImports = HashSet()
        listImports.add(event.fullNameClass)
    }

    override fun clearImports() {
        listImports.clear()
    }

}