package ru.vood.admplugin.infrastructure.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexEntity;

import java.math.BigDecimal;
import java.util.List;

public interface VBdIndexEntityRepository extends CrudRepository<VBdIndexEntity, BigDecimal> {


    List<VBdIndexEntity> findByCode(String code);

}
