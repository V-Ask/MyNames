package com.r1ckp1ckle.mynames.utils;

import com.r1ckp1ckle.mynames.MyNamesCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

public class ConfigurationUtils {
    private MyNamesCore myNamesCore;

    private File configFile, dataFile;
    private FileConfiguration config, data;

    public ConfigurationUtils(MyNamesCore myNamesCore) {
        this.myNamesCore = myNamesCore;
        initConfig();
    }

    private void initConfig() {
        if (!myNamesCore.getDataFolder().exists()) {
            myNamesCore.getDataFolder().mkdirs();
        }
        configFile = new File(myNamesCore.getDataFolder(), "config.yml");
        if (!configFile.exists()) myNamesCore.saveResource("config.yml", false);
        config = new YamlConfiguration();
        dataFile = new File(myNamesCore.getDataFolder(), "data.yml");
        if (!dataFile.exists()) myNamesCore.saveResource("data.yml", false);
        data = new YamlConfiguration();
        try {
            config.load(configFile);
            data.load(dataFile);
        } catch (Exception e) {
            myNamesCore.getLogger().log(Level.SEVERE, "Failed to load plugin files.");
            myNamesCore.getLogger().log(Level.SEVERE, "Error: " + e.getMessage());
        }
    }

    public void reloadFiles() {
        try {
            config.load(configFile);
            data.load(dataFile);
        } catch (Exception e) {
            myNamesCore.getLogger().log(Level.SEVERE, "Failed to load plugin files.");
            myNamesCore.getLogger().log(Level.SEVERE, "Error: " + e.getMessage());
        }
    }

    public void saveFiles() {
        try {
            config.save(configFile);
            data.save(dataFile);
        } catch (Exception e) {
            myNamesCore.getLogger().log(Level.SEVERE, "Failed to save plugin files.");
            myNamesCore.getLogger().log(Level.SEVERE, "Error: " + e.getMessage());
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getData() {
        return data;
    }
}
