package ru.vood.admplugin.infrastructure.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;

import java.math.BigDecimal;
import java.util.List;

public interface VBdColumnsEntityRepository extends JpaRepository<VBdColumnsEntity, BigDecimal> {


    //------------------------------individual-----------------------------

    List<VBdColumnsEntity> findByCode(String code);

    List<VBdColumnsEntity> findByParent(VBdTableEntity parent);

}
