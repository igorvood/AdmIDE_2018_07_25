package ru.vood.admplugin.infrastructure.spring.referenceBook;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.except.CoreExeption;
import ru.vood.admplugin.infrastructure.spring.intf.VBdTableEntityService;

import java.util.HashMap;
import java.util.Map;

@Repository
@Service
public class Tables {

    private static Map<String, VBdTableEntity> bdObjectEntityMap;

    private static VBdTableEntity get(String s) {
        if (bdObjectEntityMap == null) {
            bdObjectEntityMap = new HashMap<>();
        }
        VBdTableEntity entity = bdObjectEntityMap.get(s);
        if (entity == null) {
            VBdTableEntityService tableEntityService = LoadedCTX.getService(VBdTableEntityService.class);
            try {
                entity = tableEntityService.findByCode(s);
                bdObjectEntityMap.put(entity.getCode(), entity);
            } catch (CoreExeption e) {
                entity = null;
            }
        }
        return entity;
    }

    public static VBdTableEntity getOBJECT() {
        return get("OBJECT");
    }

    public static VBdTableEntity getDATE() {
        return get("DATE");
    }

    public static VBdTableEntity getBOOLEAN() {
        return get("BOOLEAN");
    }

    public static VBdTableEntity getREFERENCE() {
        return get("REFERENCE");
    }

    public static VBdTableEntity getARRAY() {
        return get("ARRAY");
    }

    public static VBdTableEntity getSTRING() {
        return get("STRING");
    }

    public static VBdTableEntity getNUMBER() {
        return get("NUMBER");
    }

    public static VBdTableEntity getTABLE() {
        return get("TABLE");
    }

    public static VBdTableEntity getAny(String tableName) {
        return get(tableName);
    }

/*    @PostConstruct
    private Map<String, VBdTableEntity> getMainTables() {
        if (bdObjectEntityMap == null) {
            Iterable<VBdTableEntity> iterable = objectEntityRepository.findAll();
            bdObjectEntityMap = StreamSupport.stream(iterable.spliterator(), false)
                    .collect(Collectors.toMap(p -> p.getCode(), q -> q));
        }
        return bdObjectEntityMap;
    }*/


}
