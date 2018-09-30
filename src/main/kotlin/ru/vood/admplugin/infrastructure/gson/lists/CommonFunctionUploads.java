package ru.vood.admplugin.infrastructure.gson.lists;

import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommonFunctionUploads {

    /**
     * распределение разных записей по своим спискам
     *
     * @param entityList общий список сущностей
     * @param aClass     клаас - фильтр, толко эти сущности попадут в результурующий список
     * @return - список только с экземплярами aClass
     */
    public ArrayList division(List<VBdObjectEntity> entityList, Class aClass) {
        ArrayList ts = null;
        if (aClass.equals(VBdTableEntity.class)) {
            ts = new VBdTableEntityList();
        } else if (aClass.equals(VBdColumnsEntity.class)) {
            ts = new VBdColumnsEntityList();
        } else if (aClass.equals(VBdIndexEntity.class)) {
            ts = new VBdIndexEntityList();
        }

        if (entityList != null && !entityList.isEmpty()) {
            List qe = entityList.stream()
                    .filter(q -> q.getClass().equals(aClass))
                    .collect(Collectors.toList());
            ts.addAll(qe);
        }
        return ts;
    }

//    public List<VBdObjectEntity> reverseDivision(Class aClass){
//
//    }

    public List<File> getListFileInDir(File dir) {
        File tmp = dir.isDirectory() ? dir : new File(dir.getParent());

        return Arrays.asList(tmp.listFiles()).stream()
                .filter(f -> f.getName().endsWith(".table"))
                .collect(Collectors.toList());
    }

    public void deleteFiles(List<File> fileList) {
        fileList.stream().peek(f -> f.delete());
    }

}
