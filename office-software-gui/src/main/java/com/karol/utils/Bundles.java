package com.karol.utils;

import java.io.IOException;
import java.util.Properties;

public class Bundles {

    private static Properties properties = load();

    private static Properties load() {
        Properties prop = new Properties();
        try {
            prop.load(Bundles.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            System.out.println("Cannot load properties file");
        }
        return prop;
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
