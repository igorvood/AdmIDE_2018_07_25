package ru.vood.admplugin.infrastructure.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;

import java.math.BigDecimal;
import java.util.List;

public interface VBdTableEntityRepository extends JpaRepository<VBdTableEntity, BigDecimal> {

    VBdTableEntity findById(BigDecimal id);

    //------------------------------individual-----------------------------

    List<VBdTableEntity> findByCode(String code);

    List<VBdTableEntity> findByTypeObjectCodeIn(String... codeS);

    List<VBdTableEntity> findByParent(VBdObjectEntity parent);


}
