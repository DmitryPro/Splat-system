package com.splat.provider;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by ִלטענטי on 25.08.2015.
 */
public class PropertiesLoader {

    private static PropertiesLoader instance;
    private Properties properties;
    Logger logger = Logger.getLogger(PropertiesLoader.class.getName());

    /**
     *
     */
    PropertiesLoader() {
        this.properties = new Properties();
        try {
            load();
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * @return
     */
    public static PropertiesLoader getInstance() {
        if(instance == null) {
            instance = new PropertiesLoader();
        }
        return instance;
    }

    /**
     * @throws IOException
     */
    private void load() throws IOException{

        properties = new Properties();
        String fileName = "config.properties";

        InputStream inputStream = new FileInputStream(fileName);

        if(inputStream != null) {
            properties.load(inputStream);
        }
        else {
            throw new FileNotFoundException("Can't find a properties file");
        }
    }

    /**
     * @param property
     * @return
     */
    public String getValue(String property) {
        return properties.getProperty(property);
    }

    /**
     * @param property
     * @param defaultValue
     * @return value of this property in config file or default value
     */
    public String getOrDefault(String property,String defaultValue) {
        return properties.getProperty(property,defaultValue);
    }
}
