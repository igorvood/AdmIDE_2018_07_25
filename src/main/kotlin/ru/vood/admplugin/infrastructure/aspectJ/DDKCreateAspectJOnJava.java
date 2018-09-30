package ru.vood.admplugin.infrastructure.aspectJ;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.except.ApplicationException;
import ru.vood.admplugin.infrastructure.spring.intf.VBdObjectEntityService;

import javax.transaction.Transactional;

@Aspect
@Component
@Transactional
public class DDKCreateAspectJOnJava {

    private final static Logger lOG = LoggerFactory.getLogger(DDKCreateAspectJOnJava.class);

    @Pointcut("execution(* ru.vood.admplugin.infrastructure.spring.intf.*.save(..))")
    // @Pointcut("execution(* ru.vood.Plugin.infrastructure.spring.impl.*.save(..)) ")
    //@Pointcut("execution(* ru.vood.Plugin.dialogs.ADMDialog.addOrEditColumn(..))")
    public void addOrEditObj() {
    }

//    @Pointcut("execution(* ru.vood.Plugin.infrastructure.spring.intf.*.save(..))")
//    public void addOrEditObjBefore(Object o) {
//    }
//
//
//    @Before("addOrEditObjBefore(o)")
//    public void beforeAdd( Object o) {
//        DDLSave ddlSave = LoadedCTX.getService(DDLSave.class);
//        ddlSave.beforeTest(o);
//    }

    @Around("addOrEditObj()")
    public Object addOrEditObjArround(ProceedingJoinPoint proceedingJoinPoint) {
        long startTime = System.nanoTime();
        Object[] adding = proceedingJoinPoint.getArgs();
        DDLSave ddlSave = LoadedCTX.getService(DDLSave.class);
        ddlSave.checkRun(proceedingJoinPoint, adding[0]);
        boolean create = false;
        VBdObjectEntity newEntity = null;
        VBdObjectEntity oldEntity = null;
        if (adding[0] instanceof VBdObjectEntity) {
            newEntity = (VBdObjectEntity) adding[0];
            create = newEntity.getId() == null;
            if (!create) {
                oldEntity = (VBdObjectEntity) LoadedCTX.getService(VBdObjectEntityService.class).findOne(newEntity.getId()).copy();
            }
            ddlSave.before(proceedingJoinPoint, adding, oldEntity);
        }
        //System.out.println(adding);

        // ========================================= Вызов основного метода==========================================
        Object ret;
        try {
            ret = proceedingJoinPoint.proceed(adding);
        } catch (Throwable throwable) {
            ddlSave.error(throwable);
            ret = null;
            lOG.error("Не удалось выполнить сохрание ", throwable);
            throw new ApplicationException("Не удалось выполнить сохрание ", throwable);
        }
        // ========================================= Вызов основного метода==========================================
        if (ret != null) {
            if (adding[0] instanceof VBdObjectEntity) {
                try {
                    ddlSave.afterSave(ret, create, oldEntity);
                } catch (Exception e) {
                    long endTime = System.nanoTime();
                    System.out.println("Method " + proceedingJoinPoint.getSignature().toShortString() + " took " + (endTime - startTime));
                    throw new ApplicationException(e.getMessage(), e);
                }
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Method " + proceedingJoinPoint.getSignature().toShortString() + " took " + (endTime - startTime));
        return ret;
    }

    @Pointcut("execution(* ru.vood.Plugin.admPlugin.spring.intf.*.delete(..))")
    public void deleteObj() {
    }

    @Around("deleteObj()")
    public Object deleteObjObjArround(ProceedingJoinPoint proceedingJoinPoint) {
        long startTime = System.nanoTime();
        Object[] droped = proceedingJoinPoint.getArgs();
        DDLSave ddlSave = LoadedCTX.getService(DDLSave.class);
        //ddlSave.checkRun(proceedingJoinPoint, adding[0]);
        Object ret;
        try {
            ret = proceedingJoinPoint.proceed(droped);
        } catch (Throwable throwable) {
            ddlSave.error(throwable);
            lOG.error("Не удалось выполнить Удаление ", throwable);
            throw new ApplicationException("Не удалось выполнить Удаление ", throwable);
        }
        try {
            ddlSave.afterDrop(droped);
        } catch (Exception e) {
            long endTime = System.nanoTime();
            System.out.println("Method " + proceedingJoinPoint.getSignature().toShortString() + " took " + (endTime - startTime));
            throw new ApplicationException(e.getMessage(), e);
        }

        long endTime = System.nanoTime();
        System.out.println("Method " + proceedingJoinPoint.getSignature().toShortString() + " took " + (endTime - startTime));

        return ret;

    }
}
