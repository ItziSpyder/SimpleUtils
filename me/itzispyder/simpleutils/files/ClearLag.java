package me.itzispyder.simpleutils.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ClearLag {

    private static File file;
    private static FileConfiguration data;

    public static void setUp() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SimpleUtils").getDataFolder(),"options/clearlag.yml");

        try {
            if (file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            // empty
        }

        data = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = data.getConfigurationSection("server.options");
        if (section == null) {
            data.set("server.options.resume",false);
            data.set("server.options.interval",120);
            data.set("server.options.cleartype","#COMMON");
            save();
        }
    }

    public static void save() {
        try {
            data.save(file);
        } catch (IOException exception) {
            // empty
        }
    }

    public static FileConfiguration get() {
        return data;
    }

    public static void reload() {
        data = YamlConfiguration.loadConfiguration(file);
    }

    public static boolean isResumed() {
        return data.getBoolean("server.options.resume");
    }

    public static double getInterval() {
        return data.getDouble("server.options.interval");
    }

    public static String getClearType() {
        return data.getString("server.options.cleartype");
    }
}
