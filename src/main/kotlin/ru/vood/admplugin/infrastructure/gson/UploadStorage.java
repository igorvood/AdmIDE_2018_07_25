package ru.vood.admplugin.infrastructure.gson;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vood.admplugin.infrastructure.gson.lists.*;
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdIndexEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.except.CoreExeption;
import ru.vood.admplugin.infrastructure.spring.intf.VBdObjectEntityService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UploadStorage {

    @Autowired
    private ZipUtil zipUtil;

    @Autowired
    private CommonFunctionUploads helpFunction;

    @Autowired
    private GsonTune gsonTune;

    @Autowired
    private VBdObjectEntityService bdObjectEntityService;

    public void upload(File file, List<VBdObjectEntity> entityList) {
        Gson gson = gsonTune.getGson();

        VBdTableEntityList bdTableEntityList = (VBdTableEntityList) helpFunction.division(entityList, VBdTableEntity.class);
        String sbdTableEntityList = gson.toJson(bdTableEntityList);

        VBdColumnsEntityList vBdColumnsEntityList = (VBdColumnsEntityList) helpFunction.division(entityList, VBdColumnsEntity.class);
        String svBdColumnsEntityList = gson.toJson(vBdColumnsEntityList);

        VBdIndexEntityList vBdIndexEntityList = (VBdIndexEntityList) helpFunction.division(entityList, VBdIndexEntity.class);
        String svBdIndexEntityList = gson.toJson(vBdIndexEntityList);

        HashMap<Class, String> uploadMap = new HashMap<>();
        uploadMap.put(VBdTableEntity.class, sbdTableEntityList);
        uploadMap.put(VBdColumnsEntity.class, svBdColumnsEntityList);
        uploadMap.put(VBdIndexEntity.class, svBdIndexEntityList);

        upload(file, uploadMap);
    }

    private void upload(File file, HashMap<Class, String> filesInStorage) {
        if (file.isDirectory()) {
            for (Class cl : filesInStorage.keySet()) {
                try {
                    Files.write(getName(file, cl), filesInStorage.get(cl).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            upload(new File(file.getParent()), filesInStorage);
            try {
                File dir = new File(file.getParent());
                zipUtil.doZip(dir, file);

                List<File> fileList = helpFunction.getListFileInDir(dir);
                helpFunction.deleteFiles(fileList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Path getName(File path, Class aClass) {
        return Paths.get(path.getPath() + "\\" + aClass.getSimpleName() + ".table");
    }

    private String getName(Class aClass) {
        return aClass.getSimpleName() + ".table";
    }


    public ArrayList<VBdObjectEntity> load(@NotNull File file) {
        List<VBdObjectEntity> objectEntities = new ArrayList<>();
        try {
            // распаковка
            zipUtil.readZip(file);
            Gson gson = gsonTune.getGson();
            List<File> fileList = helpFunction.getListFileInDir(file);
            // все классы лежат по своим файлам, вычитать все в единый список
            for (File etityFile : fileList) {

                StringBuffer gsonStr = new StringBuffer();
                Files.lines(Paths.get(etityFile.getPath()), StandardCharsets.UTF_8)
                        .forEach((s1) -> {
                            gsonStr.append(s1);
                        });
                List entities = null;
                if (etityFile.getName().equals(getName(VBdIndexEntity.class))) {
                    entities = gson.fromJson(gsonStr.toString(), VBdIndexEntityList.class);
                } else if (etityFile.getName().equals(getName(VBdTableEntity.class))) {
                    entities = gson.fromJson(gsonStr.toString(), VBdTableEntityList.class);
                } else if (etityFile.getName().equals(getName(VBdColumnsEntity.class))) {
                    entities = gson.fromJson(gsonStr.toString(), VBdColumnsEntityList.class);
                } else {
                    entities = new ArrayList();
                }
                objectEntities.addAll(entities);
            }
            helpFunction.deleteFiles(fileList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!objectEntities.isEmpty()) {
            HashMap<BigDecimal, VBdObjectEntity> objectEntityHashMap = new HashMap<>();
            // все в одну мапу, так работать удобнее
            objectEntities.stream().forEach(entity -> addEntity(entity, objectEntityHashMap));

            // надо проставить родителей из мапы, так удобнее работать
            for (VBdObjectEntity entity : objectEntityHashMap.values()) {
                if (entity.getParent() != null) {
                    entity.setParent(objectEntityHashMap.get(entity.getParent().getId()));
                }
            }

            /* теперь надо поискать настоящие ID в базе и записать их в нужные экземпляры,
             * найденный в базе буду обновлять не найденны создавать
             */
            for (Map.Entry<BigDecimal, VBdObjectEntity> entry : objectEntityHashMap.entrySet()) {
                VBdObjectEntity entity = entry.getValue();
                VBdObjectEntity entityInBase;
                try {
                    String parentCode = entity.getParent() == null ? null : entity.getParent().getCode();
                    entityInBase = bdObjectEntityService.findByCodeAndParenCode(entity.getCode(), parentCode);
                    entity.setId(entityInBase.getId());
                } catch (CoreExeption coreExeption) {
                    coreExeption.printStackTrace();
                    entity.setId(null);
                }
            }
            Comparator comparator = (o1, o2) -> {
                if (o1.getClass().equals(o2.getClass())) return 0;

                Integer priorO1 = 1001;
                if (o1 instanceof VBdObjectEntity) priorO1 = 10;
                if (o1 instanceof VBdTableEntity) priorO1 = 100;
                if (o1 instanceof VBdColumnsEntity) priorO1 = 200;
                if (o1 instanceof VBdIndexEntity) priorO1 = 300;

                Integer priorO2 = 1001;
                if (o2 instanceof VBdObjectEntity) priorO2 = 10;
                if (o2 instanceof VBdTableEntity) priorO2 = 100;
                if (o2 instanceof VBdColumnsEntity) priorO2 = 200;
                if (o2 instanceof VBdIndexEntity) priorO2 = 300;

                return priorO1.compareTo(priorO2);
            };
            // отсортирую, так дальше будет удобнее. сначала объекты -> таблицы -> колонки -> индексы
            objectEntities = objectEntityHashMap.values()
                    .stream()
                    .sorted(comparator::compare)
                    .collect(Collectors.toList());

        }

        return (ArrayList<VBdObjectEntity>) objectEntities;
    }

    private void addEntity(VBdObjectEntity entity, HashMap<BigDecimal, VBdObjectEntity> entityHashMap) {

        if (entity.getParent() == null) {
            puntInMapByPrior(entity, entityHashMap);
        } else {
            if (entityHashMap.containsKey(entity.getParent().getId())) {
                puntInMapByPrior(entity, entityHashMap);
            } else {
                addEntity(entity.getParent(), entityHashMap);
                puntInMapByPrior(entity, entityHashMap);
            }
        }
        System.out.println(entityHashMap);
    }

    private void puntInMapByPrior(VBdObjectEntity entity, HashMap<BigDecimal, VBdObjectEntity> entityHashMap) {
        if (entityHashMap.containsKey(entity.getId())) {
            if (getPrior(entity) < getPrior(entityHashMap.get(entity.getId()))) {
                entityHashMap.put(entity.getId(), entity);
            }
        } else {
            entityHashMap.put(entity.getId(), entity);
        }

    }

    private int getPrior(VBdObjectEntity entity) {
        int prior = 1000;
        if (entity instanceof VBdTableEntity) {
            prior = 10;
        } else if (entity instanceof VBdColumnsEntity) {
            prior = 20;
        } else if (entity instanceof VBdIndexEntity) {
            prior = 30;
        }
        return prior;
    }

}