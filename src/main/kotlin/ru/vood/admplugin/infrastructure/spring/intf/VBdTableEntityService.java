package ru.vood.admplugin.infrastructure.spring.intf;

import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.except.CoreExeption;

import java.util.List;

public interface VBdTableEntityService /*extends ParentForAllServise/*<S extends VBdTableEntity> extends VBdObjectEntityService*/ {

    VBdTableEntity save(VBdTableEntity entity);

    void delete(VBdTableEntity entity);

    List<VBdTableEntity> findByParent(VBdObjectEntity parent);

    VBdTableEntity findByCode(String code) throws CoreExeption;

    List<VBdTableEntity> findByTypeObjectCodeIn(String... codeS);
}
