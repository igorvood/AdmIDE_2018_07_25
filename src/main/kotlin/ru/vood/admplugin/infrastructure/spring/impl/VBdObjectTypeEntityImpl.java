package ru.vood.admplugin.infrastructure.spring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectTypeEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdObjectTypeEntityService;
import ru.vood.admplugin.infrastructure.spring.repository.VBdObjectTypeEntityRepository;

@Service
@Repository
@Transactional
public class VBdObjectTypeEntityImpl /*extends ParentForAllImpl*/ implements VBdObjectTypeEntityService {

    private VBdObjectTypeEntityRepository bdObjectTypeEntityRepository;

    @Autowired
    public VBdObjectTypeEntityImpl(VBdObjectTypeEntityRepository bdObjectTypeEntityRepository) {
        this.bdObjectTypeEntityRepository = bdObjectTypeEntityRepository;
    }

    @Override
    public VBdObjectTypeEntity save(VBdObjectTypeEntity bdObjectTypeEntity) {
        return bdObjectTypeEntityRepository.save(bdObjectTypeEntity);
    }

    @Override
    public void delete(VBdObjectTypeEntity bdObjectTypeEntity) {
        bdObjectTypeEntityRepository.delete(bdObjectTypeEntity);
    }

    @Override
    public VBdObjectTypeEntity findByCode(String code) {
        return bdObjectTypeEntityRepository.findByCode(code).get(0);
    }
}
