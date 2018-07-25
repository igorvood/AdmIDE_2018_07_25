package ru.vood.admplugin.infrastructure.gson.lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Component
public class ZipUtil {

    @Autowired
    private CommonFunctionUploads helpFunction;

    public void doZip(File dir, File zip) throws IOException {
        helpFunction.getListFileInDir(dir);

        List<File> list = helpFunction.getListFileInDir(dir);

        String zipName = zip.toString();
        if (!zipName.endsWith(".zip")) {
            zipName = zipName + ".zip";
        }
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipName));

        for (File f : list) {
            /*if (f.isDirectory())
                doZip(f, zip);
            else {*/
            //out.putNextEntry(new ZipEntry(f.getPath()));
            out.putNextEntry(new ZipEntry(f.getName()));
            write(new FileInputStream(f), out);
            //}
        }

        out.close();
    }

    public void readZip(File zipFile) throws IOException {
        ZipFile zip = new ZipFile(zipFile);
        Enumeration entries = zip.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            System.out.println(entry.getName());

            if (entry.isDirectory()) {
                new File(zipFile.getParent(), entry.getName()).mkdirs();
            } else {
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(
                        new File(zipFile.getParent(), entry.getName())));
                write(zip.getInputStream(entry), outputStream);
                outputStream.close();
            }
        }
        zip.close();
    }

    private void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();

    }
}
