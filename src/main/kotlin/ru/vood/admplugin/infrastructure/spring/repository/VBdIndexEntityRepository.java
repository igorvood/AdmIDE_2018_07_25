package ru.vood.admplugin.infrastructure.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexEntity;

import java.math.BigDecimal;
import java.util.List;

public interface VBdIndexEntityRepository extends JpaRepository<VBdIndexEntity, BigDecimal> {


    List<VBdIndexEntity> findByCode(String code);

}
