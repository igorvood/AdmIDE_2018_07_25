package ru.vood.admplugin.infrastructure.spring.intf;

import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.except.CoreExeption;

import java.util.List;

public interface VBdColumnsEntityService {

    VBdColumnsEntity save(VBdColumnsEntity entity);

    void delete(VBdColumnsEntity entity);

    List<VBdColumnsEntity> findByParent(VBdTableEntity parent);

    VBdColumnsEntity findColumn(VBdTableEntity parent, String code) throws CoreExeption;

    List<VBdColumnsEntity> findAllByParent(VBdTableEntity parent);
    //List<VBdObjectEntity> findByTypeObjectCodeIn(String... codeS

    /**
     * поиск колонок являющимися ссылками на указанный тип
     *
     * @param entity
     * @return
     */
    List<VBdColumnsEntity> findColumnRefIn(VBdTableEntity entity);
}
