package me.itzispyder.simpleutils.files;

import me.itzispyder.simpleutils.events.EntityEvents;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SpawnControl {
    private static File file;
    private static FileConfiguration data;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SimpleUtils").getDataFolder(),"spawncontrol.yml");

        try {
            if (file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            // empty
        }

        data = YamlConfiguration.loadConfiguration(file);
        if (data.getConfigurationSection("server.spawning") == null) {
            for (String type : EntityEvents.getEntityTypes()) {
                data.set("server.spawning." + type, true);
            }
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
}
