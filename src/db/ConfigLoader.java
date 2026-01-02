package db;

import resources.ConfigManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    // Placeholder for configuration loading logic
    private String dbUrl = "jdbc:mysql://localhost:3306/osidb";
    private String dbUser = "your_username";
    private String dbPassword = "your_password";

    private Properties properties = new Properties();

    // Constructor to load properties
    public ConfigLoader() {
        //Load the file from classpath
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("resources/config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties trying to create one");
                ConfigManager configManager = new ConfigManager();
                configManager.setupConfig();
                return;
            }

            //Load a properties file from class path, inside static method
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }                                                                                               
    }

    // Getters for database configuration
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getDbUrl() {
        dbUrl = properties.getProperty("db.address", dbUrl);
        return dbUrl;
    }
    public String getDbUser() {
        dbUser = properties.getProperty("db.username", dbUser);
        return dbUser;
    }   
    public String getDbPassword() {
        dbPassword = properties.getProperty("db.password", dbPassword);
        return dbPassword;
    }   

}


/*
    public ConfigLoader() {
        //Load the file from classpath
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            //Load a properties file from class path, inside static method
            Properties prop = new Properties();
            prop.load(input);

            //Get the property values
            dbUrl = prop.getProperty("db.address");
            dbUser = prop.getProperty("db.user");
            dbPassword = prop.getProperty("db.password");

        } catch (IOException ex) {
            ex.printStackTrace(); */