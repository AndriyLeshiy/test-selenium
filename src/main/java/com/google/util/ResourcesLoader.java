package com.google.util;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*
 * Class that extracts properties from the prop file.
 */
public class ResourcesLoader {
    private static final String PROP_FILE = "/application.properties";

    private ResourcesLoader() {
    }

    public static String loadProperty(String name) {
        String propFile = System.getenv("SELENIUM_PROPERTIES");
        Properties props = new Properties();

        if (propFile == null) {
            try {
                props.load(ResourcesLoader.class.getResourceAsStream(PROP_FILE));
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

    public static String loadBody(String receiver) {
        String template;
        try {
            template = IOUtils.toString(ResourcesLoader.class.getClassLoader()
                                                             .getResourceAsStream("template.html"));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        StringTemplate hello = new StringTemplate(template, DefaultTemplateLexer.class);
        hello.setAttribute("name", receiver);
        return hello.toString();
    }

    public static List<Pair> loadReceivers() {
        String propFile = System.getenv("RECEIVERS_PROPERTIES");
        Properties props = new Properties();

        if (propFile == null) {
            try {
                props.load(ResourcesLoader.class.getResourceAsStream("/receivers.properties"));
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

        List<Pair> result = new ArrayList<>();
        for (String email : props.stringPropertyNames()) {
            String name = props.getProperty(email);
            result.add(new Pair(email, name));
        }
        return result;
    }
}