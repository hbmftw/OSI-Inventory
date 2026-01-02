package resources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigManager {
    //Define the application folder and file name
    private static final String APP_FOLDER = "OSI-Config";
    private static final String CONFIG_FILE_NAME = "config.properties";
    private Properties props = new Properties();
    private Path configFilePath;

    /*
    * Constructor to initialize configuration manager
    *Build the path: User's Home Directory + APP_FOLDER + CONFIG_FILE_NAME
    *Window: C:\Users\Username\OSI-Config\config.properties
    *Mac/Linux: /Users/Username/OSI-Config/config.properties
    */
    public ConfigManager() {
        configFilePath = Path.of(System.getProperty("user.home"), APP_FOLDER, CONFIG_FILE_NAME);
        setupConfig();
    }

    public void setupConfig() {
       //If the file does not exist, create it with default properties
       try {
            if (Files.notExists(configFilePath)){System.out.println("First run detected. Creating default configuration file.");
                //Ensure the application directory exists
                Files.createDirectories(configFilePath.getParent());

                //Copy the template from src/resources/default/config.properties to the configFilePath
                try (InputStream input = getClass().getClassLoader().getResourceAsStream("default/config.properties")) {
                    if (input == null) {
                        throw new IOException("Template file 'default/config.properties' not found in classpath.");
                    }
                    Files.copy(input, configFilePath);
                }
            }
            //Load properties from the configuration file
            try (InputStream input = Files.newInputStream(configFilePath)) {
                props.load(input);
            }
       }
       catch (IOException e) {
            System.err.println("Error setting up configuration: " + e.getMessage());
            e.printStackTrace();
       }
    }
    //Get property value by key
    public String getProperty(String key) {
        return props.getProperty(key);
    }

}
