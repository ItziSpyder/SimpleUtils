package me.itzispyder.simpleutils.files;

import me.itzispyder.simpleutils.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerListTab {
    private static File file;
    private static FileConfiguration data;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SimpleUtils").getDataFolder(),"TABCONFIG.yml");

        try {
            if (file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            // empty
        }

        data = YamlConfiguration.loadConfiguration(file);
        List<String> demo = new ArrayList<>(Arrays.asList(
                "%player.ping% = player ping",
                "%player.name% = player name",
                "%server.tps% = server tps",
                "%server.players% = server online players",
                "%server.max% = server max players",
                "%server.staffs% = server online staffs",
                "%server.uptime% = server uptime",
                "%server.time% = server time",
                "%server.memory% = server memory usage",
                "%server.performance% = server overall performance",
                "to use colors '§' will replace '&' but ofc u can stuff use '§'"
        ));
        if (data.getConfigurationSection("server.tablist") == null) {
            data.set("server.tablist.header",new ArrayList<>());
            data.set("server.tablist.footer",new ArrayList<>());
        }
        data.set("SPECIAL-KEYS",demo);
        save();
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

    public static String getHeader(Player player) {
        try {
            StringBuilder header = new StringBuilder();
            for (String tabLine : data.getStringList("server.tablist.header")) {
                header.append(tabLine).append("\n");
            }
            return Messages.implementSymbols(Messages.implementColors(String.valueOf(header)),player).trim();
        } catch (IllegalArgumentException | NullPointerException exception) {
            return "";
        }
    }

    public static String getFooter(Player player) {
        try {
            StringBuilder footer = new StringBuilder();
            for (String tabLine : data.getStringList("server.tablist.header")) {
                footer.append(tabLine).append("\n");
            }
            return Messages.implementSymbols(Messages.implementColors(String.valueOf(footer)),player).trim();
        } catch (IllegalArgumentException | NullPointerException exception) {
            return "";
        }
    }
}
