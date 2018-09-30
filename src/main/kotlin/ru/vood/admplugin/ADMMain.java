package ru.vood.admplugin;

import com.google.gson.Gson;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.vood.admplugin.dialogs.ADMDialog;
import ru.vood.admplugin.dialogs.ADMTuneDialog;
import ru.vood.admplugin.dialogs.MessageWin;
import ru.vood.admplugin.infrastructure.gson.GsonTune;
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.tune.Configurations;
import ru.vood.core.runtime.exception.CoreRuntimeException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.vood.admplugin.infrastructure.applicationConst.Const.CONNECTIONS;


public class ADMMain {

    private static GenericXmlApplicationContext ctx;


    public ADMMain() {
        ADMDialog admDialog = new ADMDialog();
        admDialog.pack();
        admDialog.setVisible(true);
    }


    private static void init() {
        Gson gson = LoadedCTX.getService(GsonTune.class).getGson();


        StringBuffer gsonStr = new StringBuffer();
        Configurations tunesList = new Configurations();
        try {
            Files.lines(Paths.get(CONNECTIONS), StandardCharsets.UTF_8).forEach((s1) -> {
                gsonStr.append(s1);
            });
            tunesList = gson.fromJson(gsonStr.toString(), tunesList.getClass());
        } catch (IOException e) {

        }

        ADMTuneDialog dialog = new ADMTuneDialog(tunesList);
        dialog.pack();
        dialog.setVisible(true);
    }

    public static void main(String[] args) {

        ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:spring-config.xml"); //move from src.main.java to src.main.resources
        ctx.refresh();

        /*
        JAddDialog dialog = new SelectedDialog(null);
        dialog.pack();
        dialog.setVisible(true);
        System.out.println(dialog);*/


        /*JFileChooser fileopen = new JFileChooser();
        fileopen.setDialogType(JFileChooser.OPEN_DIALOG);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Storage", "zip");
        fileopen.setFileFilter(filter);
        int ret = fileopen.showDialog(null, "Load File");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            JAddDialog dialog = new SelectedDialog(file);
            dialog.pack();
            dialog.setVisible(true);
            System.out.println(dialog);
        }*/

        try {
            new ADMMain();
        } catch (CoreRuntimeException e) {
            new MessageWin(e.toString());
        }
    }


    public static GenericXmlApplicationContext getCtx() {
        return ctx;
    }

}
