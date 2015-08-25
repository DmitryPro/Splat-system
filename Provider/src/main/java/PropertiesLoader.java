import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ִלטענטי on 25.08.2015.
 */
public class PropertiesLoader {

    private Properties properties;
    Logger logger = Logger.getLogger(PropertiesLoader.class.getName());

    PropertiesLoader() {
        this.properties = new Properties();
        try {
            load();
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    private void load() throws IOException{

        properties = new Properties();
        String fileName = "config.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

        if(inputStream != null) {
            properties.load(inputStream);
        }
        else {
            throw new FileNotFoundException("Can't find a properties file");
        }
    }

    public String getValue(String property) {
        return properties.getProperty(property);
    }

    public String getOrDefault(String property,String defaultValue) {
        return properties.getProperty(property,defaultValue);
    }
}
