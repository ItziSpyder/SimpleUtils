package me.itzispyder.simpleutils.server;

import me.itzispyder.simpleutils.SimpleUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Server {

    public static double getTps() {
        return SimpleUtils.tps;
    }

    public static String getUptime() {
        return SimpleUtils.getUptime();
    }

    public static String getTime() {
        return SimpleUtils.getTime();
    }

    public static String getMemory() {
        Runtime run = Runtime.getRuntime();
        double maxMem = Math.floor((run.maxMemory() / 1000000) * 100) / 100;
        double usedMem = maxMem - (Math.floor((run.freeMemory() / 1000000) * 100) / 100);
        return usedMem + "MB / " + maxMem + "MB";
    }

    public static double getMspt() {
        return SimpleUtils.tps * 2.5;
    }

    public static int getOnline() {
        return Bukkit.getServer().getOnlinePlayers().size();
    }

    public static int getMaxOnline() {
        return Bukkit.getServer().getMaxPlayers();
    }

    public static int getServerRender() {
        return Bukkit.getServer().getViewDistance();
    }

    public static String getVersion() {
        return Bukkit.getServer().getVersion();
    }

    public static int getPluginCount() {
        return Bukkit.getServer().getPluginManager().getPlugins().length;
    }

    public static int getWorldSize() {
        return Bukkit.getServer().getMaxWorldSize();
    }

    public static int getWorldCount() {
        return Bukkit.getServer().getWorlds().size();
    }

    public static int getStaffs() {
        return getOnlineStaffs().size();
    }

    public static List<Player> getOnlineStaffs() {
        List<Player> staffs = new ArrayList<>();

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.isOp()) {
                staffs.add(online);
            }
        }

        return staffs;
    }

    public static String getOverallPerformance() {
        double percentage = Math.floor((((getTps() + getMspt()) / 70) * 100) * 100) / 100;
        if (percentage >= 90) {
            return "§a" + percentage + "%";
        } else if (percentage >= 80) {
            return "§e" + percentage + "%";
        } else if (percentage >= 70) {
            return "§6" + percentage + "%";
        } else if (percentage >= 60) {
            return "§c" + percentage + "%";
        } else {
            return "§4" + percentage + "%";
        }
    }
}
