package ru.vood.admplugin.infrastructure.spring.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.intf.CommonFunctionService;

import java.io.Serializable;

public class GeneratorId implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        CommonFunctionService service = LoadedCTX.getService(CommonFunctionService.class);
        return service.nextId();

    }
}
