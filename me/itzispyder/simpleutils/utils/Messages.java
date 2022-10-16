package me.itzispyder.simpleutils.utils;

import me.itzispyder.simpleutils.server.Server;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Messages {

    // §
    static HashMap<String,Long> msgCooldown = new HashMap<>();

    public static String starter = "§8>> §";
    public static String noperms = starter + "4You do not have access to this!";
    public static String cantuse = starter + "cYou cannot do this here!";
    public static String cooldown = starter + "CThis action is on cooldown!";
    public static String invalidCmd = starter + "CIncomplete or invalid command, please do \"/help\" for help!!";

    public static void send(Player player, String message) {
        if (!(msgCooldown.containsKey(player.getName()) && msgCooldown.get(player.getName()) > System.currentTimeMillis())) {
            msgCooldown.put(player.getName(),System.currentTimeMillis() + (1000));
            player.sendMessage(message);
        }
    }

    public static void bm(String message) {
        if (!(msgCooldown.containsKey("server") && msgCooldown.get("server") > System.currentTimeMillis())) {
            msgCooldown.put("server",System.currentTimeMillis() + (1000));
            Bukkit.getServer().broadcastMessage(message);
        }
    }

    public static void bmConditional(String message, boolean condition) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (condition) {
                online.sendMessage(message);
            }
        }
    }

    public static void bmOp(String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.isOp()) {
                online.sendMessage(message);
            }
        }
    }

    public static void titleAll(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    public static void soundAllWithin(Location location, Sound sound, float volume, float pitch, double radius) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getWorld() == location.getWorld() && online.getLocation().distanceSquared(location) < radius) {
                online.playSound(location,sound,volume,pitch);
            }
        }
    }

    public static void soundAll(Sound sound, float volume, float pitch) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.playSound(online.getLocation(),sound,volume,pitch);

        }
    }

    public static String implementColors(String string) {
        return string.replaceAll("&","§");
    }

    public static String implementSymbols(String string, Player player) {
        String name = string.replaceAll("%player.name%",player.getName());
        String players = name.replaceAll("%server.players%",String.valueOf(Server.getOnline()));
        String ping = players.replaceAll("%player.ping%",String.valueOf(player.getPing()));
        String tps = ping.replaceAll("%server.tps%",String.valueOf(Server.getTps()));
        String memory = tps.replaceAll("%server.memory%", Server.getMemory());
        String staffs = memory.replaceAll("%server.staffs%",String.valueOf(Server.getStaffs()));
        String performance = staffs.replaceAll("%server.performance%",Server.getOverallPerformance());
        String uptime = performance.replaceAll("%server.uptime%",Server.getUptime());
        String max = uptime.replaceAll("%server.max%",String.valueOf(Server.getMaxOnline()));
        return max.replaceAll("%server.time%",Server.getTime());
    }

}
