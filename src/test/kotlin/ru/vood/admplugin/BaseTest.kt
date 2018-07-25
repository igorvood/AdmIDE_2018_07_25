package ru.vood.admplugin

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount
import com.jeeconf.hibernate.performancetuning.sqltracker.QueryCountInfoHolder.getQueryInfo
import org.junit.Before
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.GenericApplicationContext
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.AfterTransaction
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity
import ru.vood.admplugin.infrastructure.spring.intf.*
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes
import ru.vood.admplugin.infrastructure.spring.referenceBook.RootObjects
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepFirstLoad.TuneChainStepsFirstLoad
import ru.vood.admplugin.infrastructure.tune.PluginTunes
import java.util.*
import javax.persistence.EntityManager

@TestExecutionListeners(TransactionalTestExecutionListener::class, DependencyInjectionTestExecutionListener::class, DbUnitTestExecutionListener::class)
abstract class BaseTest {
    private val DB_UNIT_SET_UP = arrayOf("", "****************************************************************", "*************** RUN TEST ***************", "****************************************************************")

    protected lateinit var ctx: GenericApplicationContext
    protected lateinit var em: EntityManager
    protected lateinit var colomnsEntityService: VBdColumnsEntityService
    protected lateinit var tableEntityService: VBdTableEntityService
    protected lateinit var indexEntityService: VBdIndexEntityService
    protected lateinit var commonFunctionService: CommonFunctionService
    protected lateinit var objectEntityService: VBdObjectEntityService
    protected lateinit var objectTypeEntityService: VBdObjectTypeEntityService
    protected lateinit var pluginTunes: PluginTunes

    protected lateinit var vBdObjectEntityParentTable: VBdObjectEntity

    protected lateinit var tableEntity: VBdTableEntity
    var tabName = "CODE_TABLE"

    protected lateinit var tableEntityWithParent: VBdTableEntity
    protected var tabNameWithParent = "CODE_TABLE_WITH_PARENT"

    //--------------------------------
    private var vBdTableEntity = VBdTableEntity()

    //protected lateinit var session: Session

    @Before
    fun dbAllSet() {
        val args: Array<String>
        Arrays.stream(DB_UNIT_SET_UP).forEach { println(it) }
        AssertSqlCount.reset()

        //ctx = AnnotationConfigApplicationContext(AdmpluginApplication::class.java)
        if (!ctx.isRunning) {
            //ctx.load("classpath:spring-config.xml")
            ctx.refresh()
            em = ctx.getBean(EntityManager::class.java)
        }

//        val drop = em.createNativeQuery(" call VOOD.DROP_ALL_TABLES()")
//        try {
//            drop.resultList
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
        try {
            RootObjects.getTABLE()
        } catch (e: Exception) {
            LoadedCTX.getService(TuneChainStepsFirstLoad::class.java).run()
        }

//*******************************************************
        pluginTunes = ctx.getBean(PluginTunes::class.java)

        colomnsEntityService = ctx.getBean(VBdColumnsEntityService::class.java)
        tableEntityService = ctx.getBean(VBdTableEntityService::class.java)
        indexEntityService = ctx.getBean(VBdIndexEntityService::class.java)
        commonFunctionService = ctx.getBean(CommonFunctionService::class.java)
        objectEntityService = ctx.getBean(VBdObjectEntityService::class.java)
        objectTypeEntityService = ctx.getBean(VBdObjectTypeEntityService::class.java)
//*******************************************************

        vBdObjectEntityParentTable = RootObjects.getTABLE()

        tableEntity = VBdTableEntity()
        tableEntity.code = tabName
        tableEntity.name = tabName + " Name"
        tableEntity.javaClass = VBdTableEntity::class.java.toString()
        tableEntity.parent = vBdObjectEntityParentTable
        tableEntity.typeObject = ObjectTypes.getTABLE()

        tableEntityWithParent = VBdTableEntity()
        tableEntityWithParent.code = tabNameWithParent
        tableEntityWithParent.name = tabNameWithParent + " Name"
        tableEntityWithParent.javaClass = VBdTableEntity::class.java.toString()
        tableEntityWithParent.parent = tableEntity
        tableEntityWithParent.typeObject = ObjectTypes.getTABLE()


    }

    @AfterTransaction
    fun showSqlCount() {
        System.out.printf("\n-------------------------------------Sql count: " + getQueryInfo().countAll())
    }
}