package ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import ru.vood.admplugin.infrastructure.spring.except.CoreExeption
import ru.vood.admplugin.infrastructure.sql.QueryTableNew
import java.sql.Connection
import javax.sql.DataSource

abstract class ChainQueryServise {
    private val lOG = LoggerFactory.getLogger(ChainQueryServise::class.java)!!
    @Autowired
    protected lateinit var dataSource: DataSource

    fun runQueryes(queryTable: QueryTableNew) {
        var conn: Connection = dataSource.connection
        for (q in queryTable) {
            if (!conn.isClosed) {
                var stmt = conn.createStatement()
                if (lOG.isDebugEnabled) {
                    lOG.debug("Попытка выполнить запрос '$q'")
                }
                try {
                    var r = stmt.executeQuery(q)
                    r?.close()
                } catch (e: Exception) {
                    lOG.error("Попытка выполнить запрос '$q'", e)
                    throw CoreExeption("Не удалось выполнить запрос \n ${q}")
                }

//                val i = queryTable.indexOf(q)
//                if (i == 25) {
//                    println("икуфл")
//                }

                stmt?.close()
            }
        }
        conn.close()
    }

}