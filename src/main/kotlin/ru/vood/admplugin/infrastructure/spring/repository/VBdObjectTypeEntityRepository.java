package ru.vood.admplugin.infrastructure.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectTypeEntity;

import java.math.BigDecimal;
import java.util.List;

public interface VBdObjectTypeEntityRepository extends JpaRepository<VBdObjectTypeEntity, BigDecimal> {


    //------------------------------individual-----------------------------

    List<VBdObjectTypeEntity> findByCodeIn(String... code);

    List<VBdObjectTypeEntity> findByCode(String code);

}
