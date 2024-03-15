package com.file2chart.service.utils;

import java.io.File;
import java.util.Objects;

public class FileUtils {

    public static String getFileExtension(String fullName) {
        Objects.requireNonNull(fullName);
        String fileName = (new File(fullName)).getName();
        int dotIndex = fileName.lastIndexOf(46);
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    public static String getNameWithoutExtension(String file) {
        Objects.requireNonNull(file);
        String fileName = (new File(file)).getName();
        int dotIndex = fileName.lastIndexOf(46);
        return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
    }
}
