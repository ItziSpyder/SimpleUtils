package me.itzispyder.simpleutils.commands;

import me.itzispyder.simpleutils.SimpleUtils;
import me.itzispyder.simpleutils.events.EntityEvents;
import me.itzispyder.simpleutils.files.PlayerHomes;
import me.itzispyder.simpleutils.files.SpawnControl;
import me.itzispyder.simpleutils.files.WarpLocations;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompleters implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> argus = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("removewarp")
                || command.getName().equalsIgnoreCase("warp")
        ) {
            return WarpLocations.getEntries();
        } else if (command.getName().equalsIgnoreCase("addwarp")) {
            argus.add("§8<warp name of your choice>");
        } else if (command.getName().equalsIgnoreCase("fakechat")) {
            if (args.length >= 2) {
                argus.add("§8<message>");
            } else if (args.length == 1) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    argus.add(online.getName());
                }
            }
        } else if (command.getName().equalsIgnoreCase("spawn")) {
            if (args.length == 1) {
                argus.add("setnew");
                argus.add("remove");
            }
        } else if (command.getName().equalsIgnoreCase("server-timedreload")
                || command.getName().equalsIgnoreCase("server-timedrestart")
                || command.getName().equalsIgnoreCase("server-timedshutdown")
        ) {
            if (args.length == 1) {
                argus.add("§8<wait time in seconds>");
            }
        } else if (command.getName().equalsIgnoreCase("spawnentities")) {
            if (args.length == 1) {
                argus.addAll(EntityEvents.getEntityTypes());
            } else if (args.length == 2) {
                argus.add("§8<spawn count>");
            }
        } else if (command.getName().equalsIgnoreCase("discord")) {
            if (args.length == 1) {
                argus.add("setlink");
                argus.add("remove");
            } else if (args.length == 2) {
                argus.add("§8<your link here>");
            }
        } else if (command.getName().equalsIgnoreCase("home")
                || command.getName().equalsIgnoreCase("delhome")
        ) {
            return PlayerHomes.getHomes((Player) sender);
        } else if (command.getName().equalsIgnoreCase("sethome")) {
            argus.add("§8<home name>");
        } else if (command.getName().equalsIgnoreCase("broadcast")) {
            argus.add("§8<message>");
        }

        // CoffeeCup imports (my separate clear lag plugin)
        switch (command.getName().toLowerCase()) {
            case "spawncontrol":
                switch (args.length) {
                    case 1:
                        return SpawnControl.allGroups();
                    case 2:
                        argus.add("true");
                        argus.add("false");
                        break;
                }
                break;
            case "clearall":
                switch (args.length) {
                    case 1:
                        return SpawnControl.allGroups();
                }
                break;
            case "clearlag":
                switch (args.length) {
                    case 1:
                        argus.add("resume");
                        argus.add("setClearType");
                        argus.add("setInterval");
                        break;
                    case 2:
                        switch (args[0].toLowerCase()) {
                            case "resume":
                                argus.add("true");
                                argus.add("false");
                                break;
                            case "setcleartype":
                                return SpawnControl.allGroups();
                            case "setinterval":
                                argus.add("§8<seconds: double>");
                                break;
                        }
                        break;
                }
                break;
            case "pl":
                switch (args.length) {
                    case 1:
                        argus.add("menu");
                        argus.add("disable");
                        argus.add("enable");
                        argus.add("reload");
                        argus.add("rl");
                        break;
                    case 2:
                        switch (args[0].toLowerCase()) {
                            case "disable":
                            case "enable":
                            case "reload":
                            case "rl":
                                return SimpleUtils.getPluginList();
                        }
                }
        }

        return argus;
    }
}
