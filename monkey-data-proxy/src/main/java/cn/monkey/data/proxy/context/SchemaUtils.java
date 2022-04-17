package cn.monkey.data.proxy.context;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public abstract class SchemaUtils {

    public static File[] getSchemaFiles(String path) throws FileNotFoundException {
        File file = ResourceUtils.getFile(path);
        List<File> schemaFiles = getSchemaFiles(file);
        return schemaFiles.toArray(File[]::new);
    }

    public static List<File> getSchemaFiles(File file) {
        List<File> fileList = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    fileList.addAll(getSchemaFiles(f));
                }
            }
        } else {
            if (file.getName().endsWith(".schemas")) {
                fileList.add(file);
            }
        }
        return fileList;
    }
}
