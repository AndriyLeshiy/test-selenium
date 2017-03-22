package com.mail.sender.util;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
 * Class that extracts properties from the prop file.
 */
public class ResourcesLoader {
    private ResourcesLoader() {
    }

    public static Properties loadProperties(String path) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(path));
            return props;
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

    public static String loadResource(String path) {
        try {
            return IOUtils.toString(getResourceInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

    public static InputStream getResourceInputStream(String path) {
        InputStream resourceStream = ResourcesLoader.class.getResourceAsStream(path);
        if (resourceStream != null) {
            return resourceStream;
        } else {
            throw new RuntimeException("");
        }
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
