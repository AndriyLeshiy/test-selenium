package com.google.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
 * Class that extracts properties from the prop file.
 * 
 * @author Sebastiano Armeli-Battana
 */
public class PropertyLoader {

    private static final String PROP_FILE = "/application.properties";

    private PropertyLoader() {
    }

    public static String loadProperty(String name) {
        String propFile = System.getenv("SELENIUM_PROPERTIES");
        Properties props = new Properties();

        if (propFile == null) {
            try {
                props.load(PropertyLoader.class.getResourceAsStream(PROP_FILE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                props.load(new FileInputStream(new File(propFile)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String value = "";
        if (name != null) {
            value = props.getProperty(name);
        }
        return value;
    }
}