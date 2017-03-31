package com.mail.sender.util;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/*
 * Class that extracts properties from the prop file.
 */
public class ResourcesLoader {
    private ResourcesLoader() {
    }

    public static String loadFile(String path) {
        try {
            return IOUtils.toString(getFileStream(path));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static InputStream getFileStream(String path) {
        try {
            return new FileInputStream(path);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
