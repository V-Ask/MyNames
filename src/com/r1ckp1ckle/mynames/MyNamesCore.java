package com.r1ckp1ckle.mynames;

import com.r1ckp1ckle.mynames.commands.GlobalTag;
import com.r1ckp1ckle.mynames.commands.ResetTag;
import com.r1ckp1ckle.mynames.commands.Tag;
import com.r1ckp1ckle.mynames.handler.NameChanger;
import com.r1ckp1ckle.mynames.utils.ConfigurationUtils;
import com.r1ckp1ckle.mynames.utils.ItemNamingUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MyNamesCore extends JavaPlugin {
    private static MyNamesCore instance;
    public List<Material> tweakedMats = new ArrayList<>();
    private ConfigurationUtils configurationUtils;

    public static MyNamesCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        System.out.println("=======================================");
        instance = this;
        configurationUtils = new ConfigurationUtils(this);
        System.out.println("Compiling tweaked materials...");
        ItemNamingUtils.CompileGlobalNames(tweakedMats);
        registerListeners();
        registerCommands();
        System.out.println("MyNames Enabled");
        System.out.println("=======================================");
    }

    @Override
    public void onDisable() {
        System.out.println("=======================================");
        System.out.println("MyNames Disabled");
        System.out.println("=======================================");
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new NameChanger(), this);
    }

    private void registerCommands() {
        getCommand("tag").setExecutor(new Tag());
        getCommand("globaltag").setExecutor(new GlobalTag());
        getCommand("resettag").setExecutor(new ResetTag());
    }

    public ConfigurationUtils getConfigurationUtils() {
        return configurationUtils;
    }

    public List<Material> getTweakedMats() {
        return tweakedMats;
    }
}
