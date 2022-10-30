package me.itzispyder.simpleutils.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerHomes {
    private static File file;
    private static FileConfiguration data;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SimpleUtils").getDataFolder(),"locations/playerhomes.yml");

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

    public static void deleteHome(Player player, String home) {
        data.set("server.players." + player.getName() + "." + home, null);
        save();
    }

    public static boolean hasHome(Player player, String home) {
        return getHomes(player).contains(home);
    }

    public static Location getHome(Player player, String home) {
        if (hasHome(player,home)) {
            return data.getLocation("server.players." + player.getName() + "." + home);
        }
        return null;
    }

    public static List<String> getHomes(Player player) {
        List<String> homes;
        try {
            homes = new ArrayList<>(data.getConfigurationSection("server.players." + player.getName()).getKeys(false));
        } catch (NullPointerException exception) {
            homes = new ArrayList<>();
        }
        return homes;
    }
}
