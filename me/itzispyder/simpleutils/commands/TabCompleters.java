package me.itzispyder.simpleutils.commands;

import me.itzispyder.simpleutils.events.EntityEvents;
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
            argus.add("§7<warp name of your choice>");
        } else if (command.getName().equalsIgnoreCase("fakechat")) {
            if (args.length >= 2) {
                argus.add("§7<message>");
            } else if (args.length == 1) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    argus.add(online.getName());
                }
            }
        } else if (command.getName().equalsIgnoreCase("spawncontrol")) {
            if (args.length >= 2) {
                argus.add("true");
                argus.add("false");
            } else if (args.length == 1) {
                argus.add("#ALL");
                argus.addAll(EntityEvents.getEntityTypes());
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
        } else if (command.getName().equalsIgnoreCase("clearall")) {
            if (args.length == 1) {
                argus.add("#COMMON");
                argus.add("#ALL");
                argus.addAll(EntityEvents.getEntityTypes());
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
        }

        return argus;
    }
}
