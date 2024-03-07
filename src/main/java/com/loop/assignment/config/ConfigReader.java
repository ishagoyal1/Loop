package main.java.com.loop.assignment.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private Properties properties;

    public ConfigReader() {
        try {
            // Load the properties file
            InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
            properties = new Properties();
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

    public String getWebsite() {
        return properties.getProperty("website");
    }

    public String getExcelSavedPath() {
        return properties.getProperty("excel.saved.path");
    }


    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader();
        System.out.println("Username: " + configReader.getUsername());
        System.out.println("Password: " + configReader.getPassword());
        System.out.println("Website: " + configReader.getWebsite());
        System.out.println("Saved Path: " + configReader.getExcelSavedPath());
    }
}


