package me.itzispyder.simpleutils.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WarpLocations {
    private static File file;
    private static FileConfiguration data;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SimpleUtils").getDataFolder(),"warplocations.yml");

        try {
            if (file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            // empty
        }

        data = YamlConfiguration.loadConfiguration(file);
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

    public static List<String> getEntries() {
        List<String> entries = new ArrayList<>();

        try {
           entries = new ArrayList<>(data.getConfigurationSection("server.locations").getKeys(false));
        } catch (NullPointerException exception) {
            // empty
        }

        return entries;
    }

    public static void deleteEntry(String entry) {
        if (getEntries().contains(entry)) {
            data.set("server.locations." + entry, null);
        }
    }

    public static Location getEntry(String entry) {
        if (getEntries().contains(entry)) {
            return data.getLocation("server.locations." + entry);
        }
        return null;
    }
}
