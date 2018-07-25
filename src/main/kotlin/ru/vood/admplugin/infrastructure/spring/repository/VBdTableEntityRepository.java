package ru.vood.admplugin.infrastructure.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface VBdTableEntityRepository extends CrudRepository<VBdTableEntity, BigDecimal> {

    Optional<VBdTableEntity> findById(BigDecimal id);

    //------------------------------individual-----------------------------

    List<VBdTableEntity> findByCode(String code);

    List<VBdTableEntity> findByTypeObjectCodeIn(String... codeS);

    List<VBdTableEntity> findByParent(VBdObjectEntity parent);


}
