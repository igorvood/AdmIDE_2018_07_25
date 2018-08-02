package ru.vood.admplugin.infrastructure.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexedColumnsEntity;

import java.math.BigDecimal;
import java.util.List;

public interface VBdIndexedColumnsEntityRepository extends JpaRepository<VBdIndexedColumnsEntity, BigDecimal> {

    List<VBdIndexedColumnsEntity> findByCollectionId(BigDecimal collectionId);

    List<VBdIndexedColumnsEntity> findByCollectionIdIn(List<BigDecimal> collectionId);
}
