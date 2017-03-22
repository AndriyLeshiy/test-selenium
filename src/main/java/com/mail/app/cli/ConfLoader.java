package com.mail.app.cli;

import com.google.common.base.Strings;
import com.mail.sender.Receiver;
import com.mail.sender.util.ResourcesLoader;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by serg on 26.03.17.
 */
public class ConfLoader {
    private static final String DEFAULT_CONF_FILE = "/conf.properties";
    private static final String CONF_ENV          = "CONF";

    private static final String DEFAULT_RECEIVERS_FILE = "/receivers.properties";
    private static final String RECEIVERS_ENV          = "RECEIVERS";

    private static final String TEMPLATE_PROPERTY     = "email.template";
    private static final String DEFAULT_TEMPLATE_FILE = "template.html";

    private ConfLoader() {
    }

    public static String loadProperty(String key) {
        Properties props = new Properties();
        try {
            props.load(getOrDefault(System.getenv(CONF_ENV), DEFAULT_CONF_FILE));
        } catch (IOException e) {
            throw new RuntimeException("");
        }

        String value = "";
        if (key != null) {
            value = props.getProperty(key);
        }
        return value;
    }

    public static String loadTemplate() {
        try {
            return IOUtils.toString(getOrDefault(loadProperty(TEMPLATE_PROPERTY),
                                                 DEFAULT_TEMPLATE_FILE));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static List<Receiver> loadReceivers() {
        Properties props = new Properties();
        try {
            props.load((getOrDefault(System.getenv(RECEIVERS_ENV), DEFAULT_RECEIVERS_FILE)));
        } catch (IOException e) {
            throw new RuntimeException("");
        }

        List<Receiver> result = new ArrayList<>();
        for (String email : props.stringPropertyNames()) {
            String name = props.getProperty(email);
            result.add(new Receiver(email, name));
        }
        return result;
    }

    private static InputStream getOrDefault(String configured, String defaultResource) {
        if (!Strings.isNullOrEmpty(configured)) {
            return ResourcesLoader.getFileStream(configured);
        } else {
            return ResourcesLoader.getResourceInputStream(defaultResource);
        }
    }
}
