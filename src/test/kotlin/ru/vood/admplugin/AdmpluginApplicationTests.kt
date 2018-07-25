package ru.vood.admplugin

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import javax.sql.DataSource
import javax.transaction.Transactional

@RunWith(SpringJUnit4ClassRunner::class)

//@SpringBootTest(classes = arrayOf(AdmpluginApplication::class))
////@SpringBootTest
//@TestExecutionListeners(TransactionalTestExecutionListener::class)
////@TestExecutionListeners({
////    TransactionalTestExecutionListener::class.java,
////    DependencyInjectionTestExecutionListener::class.java,
////    DbUnitTestExecutionListener::class.java
////})
//@Transactional
@ContextConfiguration()
class AdmpluginApplicationTests {

//    @Autowired
//    lateinit var vBdObjectTypeEntityService: VBdObjectTypeEntityService

/*
    @Autowired
    lateinit var vBdObjectTypeEntityService: DataSource

    @Before
    fun bef() {
        val s = arrayOf("")
        //main(s)
    }

    @Test
    fun contextLoads() {
        println(vBdObjectTypeEntityService)
    }
*/

}
