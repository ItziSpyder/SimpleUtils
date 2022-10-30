package me.itzispyder.simpleutils.commands;

import me.itzispyder.simpleutils.SimpleUtils;
import me.itzispyder.simpleutils.events.EntityEvents;
import me.itzispyder.simpleutils.events.ModerationStuff;
import me.itzispyder.simpleutils.files.PlayerHomes;
import me.itzispyder.simpleutils.files.WarpLocations;
import me.itzispyder.simpleutils.inventory.InventoryManager;
import me.itzispyder.simpleutils.server.Server;
import me.itzispyder.simpleutils.utils.ItemManager;
import me.itzispyder.simpleutils.utils.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Commands implements CommandExecutor {

    // instance of the main class
    static SimpleUtils plugin;
    public Commands(SimpleUtils plugin) {
        Commands.plugin = plugin;
    }

    // Variables
    static HashMap<String,String> tpa = new HashMap<>();
    static HashMap<String,String> tpahere = new HashMap<>();

    // Commands
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            // Player commands
            if (command.getName().equalsIgnoreCase("invsee")) {
                if (p.isOp()) {
                    if (args.length >= 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            InventoryManager.openPlayerInventory(p,target);
                            p.sendMessage(Messages.starter + "dOpened up " + target.getName() + "'s inventory!");
                        } else {
                            p.sendMessage(Messages.starter + "cThat player is either offline or null!");
                        }
                    } else {
                        Messages.send(p, Messages.invalidCmd);
                    }
                } else {
                    Messages.send(p, Messages.noperms);
                }

                return true;
            } else if (command.getName().equalsIgnoreCase("ec")) {
                if (args.length >= 1) {
                    if (p.isOp()) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            InventoryManager.openPlayerEnderchest(p,target);
                            p.sendMessage(Messages.starter + "dOpened up " + target.getName() + "'s ender chest!");
                        } else {
                            p.sendMessage(Messages.starter + "cThat player is either offline or null!");
                        }
                    } else {
                        Messages.send(p, Messages.noperms);
                    }
                } else {
                    InventoryManager.openPlayerEnderchest(p,p);
                }


                return true;
            } else if (command.getName().equalsIgnoreCase("server-info")) {
                p.sendMessage(
                        "\n§7Server-Info:" +
                                "\n §7TPS: §f" + Server.getTps() +
                                "\n §7MSPT: §f" + Server.getMspt() +
                                "\n §7Memory: §f" + Server.getMemory() +
                                "\n §7Uptime: §f" + Server.getUptime() +
                                "\n §7Current time: §f" + Server.getTime() +
                                "\n §7Online players: §f" + Server.getOnline() + " / " + Server.getMaxOnline() +
                                "\n §7Online staff: §f" + Server.getStaffs() +
                                "\n §7View distance: §f" + Server.getServerRender() +
                                "\n §7World size: §f" + Server.getWorldSize() +
                                "\n §7Plugin count: §f" + Server.getPluginCount() +
                                "\n §7World count: §f" + Server.getWorldCount() +
                                "\n §7Server Version: §f" + Server.getVersion() +
                                "\n §7§n§oOverall Performance:§r " + Server.getOverallPerformance() +
                                "\n "
                );

                return true;
            } else if (command.getName().equalsIgnoreCase("trash")) {
                p.openInventory(Bukkit.createInventory(p,27, Messages.starter + "cDisposal"));
                return true;
            } else if (command.getName().equalsIgnoreCase("broadcast")) {
                StringBuilder message = new StringBuilder();
                for (String arg : args) {
                    message.append(arg).append(" ");
                }
                Messages.bm(Messages.starter + "8[§6Broadcast§8] §f§l" + Messages.implementColors(String.valueOf(message)));
            }

            // Player homes
            if (command.getName().equalsIgnoreCase("sethome")) {
                if (args.length >= 1) {
                    String home = args[0].toLowerCase();
                    if (!PlayerHomes.hasHome(p,home)) {
                        PlayerHomes.get().set("server.players." + p.getName() + "." + home, p.getLocation());
                        PlayerHomes.save();
                        p.sendMessage(Messages.starter + "7Set a new home §f" + home);
                    } else {
                        p.sendMessage(Messages.starter + "cHome §f" + home + " §calready exists!");
                    }
                } else {
                    Messages.send(p, Messages.invalidCmd);
                }
            } else if (command.getName().equalsIgnoreCase("delhome")) {
                if (args.length >= 1) {
                    String home = args[0].toLowerCase();
                    if (PlayerHomes.hasHome(p,home)) {
                        PlayerHomes.deleteHome(p,home);
                        p.sendMessage(Messages.starter + "7Deleted home §f" + home);
                    } else {
                        p.sendMessage(Messages.starter + "cHome §f" + home + " §cdoes not exist!");
                    }
                } else {
                    Messages.send(p, Messages.invalidCmd);
                }
            } else if (command.getName().equalsIgnoreCase("home")) {
                String home;
                if (args.length >= 1) {
                    home = args[0].toLowerCase();
                } else {
                    home = "home";
                }
                if (PlayerHomes.hasHome(p,home)) {
                    p.teleport(PlayerHomes.getHome(p,home));
                    p.sendMessage(Messages.starter + "7Teleported to home §f" + home);
                } else {
                    p.sendMessage(Messages.starter + "cHome §f" + home + " §cdoes not exist!");
                }
            }

            // Trolls
            if (command.getName().equalsIgnoreCase("fakechat")) {
                if (p.isOp()) {
                    if (args.length >= 2) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            StringBuilder message = new StringBuilder();
                            for (int i = 0; i < args.length - 1; i ++) {
                                message.append(args[i + 1]).append(" ");
                            }
                            p.sendMessage("§7§o[Forcing " + target.getName() + " to say: " + message.toString().trim() + "]");
                            target.chat(String.valueOf(message));
                        } else {
                            p.sendMessage(Messages.starter + "cThat player is either offline or null!");
                        }
                    } else {
                        Messages.send(p, Messages.invalidCmd);
                    }
                } else {
                    Messages.send(p, Messages.noperms);
                }

                return true;
            } else if (command.getName().equalsIgnoreCase("fakeop")) {
                if (p.isOp()) {
                    if (args.length >= 1) {
                        String target = args[0];
                        Bukkit.getServer().broadcastMessage("§7§o[" + p.getName() + ": Made " + target + " a server operator]");
                    } else {
                        Messages.send(p, Messages.invalidCmd);
                    }
                } else {
                    Messages.send(p, Messages.noperms);
                }

                return true;
            }

            // Warps
            if (command.getName().equalsIgnoreCase("addwarp")) {
                if (p.isOp()) {
                    if (args.length >= 1) {
                        Location loc = p.getLocation();
                        String warp = args[0].toLowerCase();
                        WarpLocations.get().set("server.locations." + warp, loc);
                        WarpLocations.save();

                        p.sendMessage(Messages.starter + "7Added new warp §f" + warp);
                    } else {
                        Messages.send(p, Messages.invalidCmd);
                    }
                } else {
                    Messages.send(p, Messages.noperms);
                }

                return true;
            } else if (command.getName().equalsIgnoreCase("removewarp")) {
                if (p.isOp()) {
                    if (args.length >= 1) {
                        String warp = args[0].toLowerCase();

                        p.sendMessage(Messages.starter + "7Deleting warp §f" + warp + "§7...");
                        if (WarpLocations.getEntries().contains(warp)) {
                            WarpLocations.deleteEntry(warp);
                            p.sendMessage(Messages.starter + "7Deleted warp §f" + warp);
                        } else {
                            p.sendMessage(Messages.starter + "cFailed to delete warp §f" + warp + "§c; That location does not exist!");
                        }
                    } else {
                        Messages.send(p, Messages.invalidCmd);
                    }
                } else {
                    Messages.send(p, Messages.noperms);
                }

                return true;
            } else if (command.getName().equalsIgnoreCase("warp")) {
                if (args.length >= 1) {
                    String warp = args[0].toLowerCase();
                    Location loc = WarpLocations.getEntry(warp);

                    p.sendMessage(Messages.starter + "7Warping to §f" + warp + "§7...");
                    if (WarpLocations.getEntries().contains(warp)) {
                        p.teleport(loc);
                        p.sendMessage(Messages.starter + "7Warped you to §f" + warp);
                    } else {
                        p.sendMessage(Messages.starter + "cFailed to warp to §f" + warp + "§c; That location does not exist!");
                    }
                } else {
                    Messages.send(p, Messages.invalidCmd);
                }

                return true;
            } else if (command.getName().equalsIgnoreCase("spawn")) {
                if (args.length >= 1) {
                    switch (args[0]) {
                        case "setnew":
                            p.chat("/addwarp spawn");
                            break;
                        case "remove":
                            p.chat("/removewarp spawn");
                            break;
                    }
                } else {
                    p.chat("/warp spawn");
                }

                return true;
            }

            // TPA
            try {
                if (command.getName().equalsIgnoreCase("tpa")) {
                    if (args.length >= 1) {
                        if (!hasOutgoingRequest(p)) {
                            Player target = Bukkit.getPlayer(args[0]);
                            tpa.put(p.getName(),target.getName());

                            target.sendMessage(Messages.starter + "f" + p.getName() + " §7has requested to teleport to you\n§a/tpaccept §7to accept and §c/tpdeny §7to deny");
                            p.sendMessage(Messages.starter + "7You have requested to teleport to §f" + target.getName() + "\n§6/tpcancel §7to cancel this request");

                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if (hasOutgoingRequest(p)) {
                                        p.sendMessage(Messages.starter + "7Your teleportation request to §f" + tpa.get(p.getName()) + " §7has expired");
                                        tpa.remove(p.getName());
                                    }
                                }
                            },20 * 60);
                        } else {
                            p.sendMessage(Messages.starter + "cYou already have an outgoing request!");
                        }
                    } else {
                        Messages.send(p,Messages.invalidCmd);
                    }
                } else if (command.getName().equalsIgnoreCase("tpahere")) {
                    if (args.length >= 1) {
                        if (!hasOutgoingRequest(p)) {
                            Player target = Bukkit.getPlayer(args[0]);
                            tpahere.put(p.getName(),target.getName());

                            target.sendMessage(Messages.starter + "f" + p.getName() + " §7has requested you to teleport to them\n§a/tpaccept §7to accept and §c/tpdeny §7to deny");
                            p.sendMessage(Messages.starter + "7You have requested §f" + target.getName() + " §7to teleport to you\n§6/tpcancel §7to cancel this request");

                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if (hasOutgoingRequest(p)) {
                                        p.sendMessage(Messages.starter + "7Your teleportation request to §f" + tpa.get(p.getName()) + " §7has expired");
                                        tpa.remove(p.getName());
                                    }
                                }
                            },20 * 60);
                        } else {
                            p.sendMessage(Messages.starter + "cYou already have an outgoing request!");
                        }
                    } else {
                        Messages.send(p,Messages.invalidCmd);
                    }
                } else if (command.getName().equalsIgnoreCase("tpcancel")) {
                    if (hasOutgoingRequest(p)) {
                        tpa.remove(p.getName());
                        tpahere.remove(p.getName());

                        p.sendMessage(Messages.starter + "7You have cancelled all outgoing teleport requests");

                    } else {
                        p.sendMessage(Messages.starter + "cYou do not have any outgoing requests!");
                    }
                } else if (command.getName().equalsIgnoreCase("tpaccept")) {
                    if (hasIncomingRequest(p)) {
                        Player tpaRequester = getTpaRequester(p);
                        Player tpahereRequester = getTpahereRequester(p);

                        p.sendMessage(Messages.starter + "7You have accepted the teleport request");
                        if (tpaRequester != null) {
                            tpaRequester.teleport(p.getLocation());
                            tpaRequester.sendMessage(Messages.starter + "f" + p.getName() + " §7has accepted the teleport request");
                            tpa.remove(tpaRequester.getName());
                            return true;
                        }
                        if (tpahereRequester != null) {
                            p.teleport(tpahereRequester.getLocation());
                            tpahereRequester.sendMessage(Messages.starter + "f" + p.getName() + " §7has accepted the teleport request");
                            tpahere.remove(tpahereRequester.getName());
                            return true;
                        }

                    } else {
                        p.sendMessage(Messages.starter + "cYou do not have any incoming requests!");
                    }
                } else if (command.getName().equalsIgnoreCase("tpdeny")) {
                    if (hasIncomingRequest(p)) {
                        Player tpaRequester = getTpaRequester(p);
                        Player tpahereRequester = getTpahereRequester(p);

                        p.sendMessage(Messages.starter + "7You have accepted the teleport request");
                        if (tpaRequester != null) {
                            tpaRequester.sendMessage(Messages.starter + "f" + p.getName() + " §7has denied the teleport request");
                            tpa.remove(tpaRequester.getName());
                            return true;
                        }
                        if (tpahereRequester != null) {
                            tpahereRequester.sendMessage(Messages.starter + "f" + p.getName() + " §7has denied the teleport request");
                            tpahere.remove(tpahereRequester.getName());
                            return true;
                        }

                    } else {
                        p.sendMessage(Messages.starter + "cYou do not have any incoming requests!");
                    }
                }

                // OTHER
                if (command.getName().equalsIgnoreCase("receivecommandlogs")) {
                    if (p.isOp()) {
                        if (!EntityEvents.receivingCommands.contains(p)) {
                            EntityEvents.receivingCommands.add(p);
                            p.sendMessage(Messages.starter + "aYou will now start receiving command logs!");
                        } else {
                            EntityEvents.receivingCommands.remove(p);
                            p.sendMessage(Messages.starter + "aYou will no longer receive command logs!");
                        }
                    } else {
                        Messages.send(p, Messages.noperms);
                    }
                } else if (command.getName().equalsIgnoreCase("afk")) {
                    if (EntityEvents.isAfk(p)) {
                        EntityEvents.removeAfk(p);
                    } else {
                        EntityEvents.addAfk(p);
                    }
                } else if (command.getName().equalsIgnoreCase("statspaper")) {
                    if (p.isOp()) {
                        if (args.length >= 1) {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                            ItemStack paper = new ItemStack(Material.PAPER);
                            ItemManager.setAsPlayerStats(paper,target);
                            p.getWorld().dropItemNaturally(p.getLocation(),paper);
                        } else {
                            Messages.send(p, Messages.invalidCmd);
                        }
                    } else {
                        Messages.send(p, Messages.noperms);
                    }
                } else if (command.getName().equalsIgnoreCase("server-timedreload")) {
                    if (p.isOp()) {
                        if (args.length >= 1) {
                            try {
                                int waitTime = Integer.parseInt(args[0]);
                                runTimedCommand("reload confirm",waitTime);
                            } catch (IllegalArgumentException exception) {
                                Messages.send(p, Messages.invalidCmd);
                            }
                        } else {
                            Messages.send(p, Messages.invalidCmd);
                        }
                    } else {
                        Messages.send(p, Messages.noperms);
                    }
                } else if (command.getName().equalsIgnoreCase("server-timedrestart")) {
                    if (p.isOp()) {
                        if (args.length >= 1) {
                            try {
                                int waitTime = Integer.parseInt(args[0]);
                                runTimedCommand("restart",waitTime);
                            } catch (IllegalArgumentException exception) {
                                Messages.send(p, Messages.invalidCmd);
                            }
                        } else {
                            Messages.send(p, Messages.invalidCmd);
                        }
                    } else {
                        Messages.send(p, Messages.noperms);
                    }
                } else if (command.getName().equalsIgnoreCase("server-timedshutdown")) {
                    if (p.isOp()) {
                        if (args.length >= 1) {
                            try {
                                int waitTime = Integer.parseInt(args[0]);
                                runTimedCommand("stop",waitTime);
                            } catch (IllegalArgumentException exception) {
                                Messages.send(p, Messages.invalidCmd);
                            }
                        } else {
                            Messages.send(p, Messages.invalidCmd);
                        }
                    } else {
                        Messages.send(p, Messages.noperms);
                    }
                } else if (command.getName().equalsIgnoreCase("clearchat")) {
                    if (p.isOp()) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < 690; i ++) {
                            builder.append("\n ");
                        }
                        builder.append(Messages.starter).append("f").append(p.getName()).append(" §7cleared chat");
                        Bukkit.getServer().broadcastMessage(builder.toString());
                    } else {
                        Messages.send(p, Messages.noperms);
                    }
                } else if (command.getName().equalsIgnoreCase("mute")) {
                    if (p.isOp()) {
                        if (args.length >= 1) {
                            Player target = Bukkit.getPlayer(args[0]);
                            if (target != null) {
                                if (ModerationStuff.isMuted(target)) {
                                    ModerationStuff.unmute(target);
                                    p.sendMessage(Messages.starter + "7You have unmuted §f" + target.getName());
                                } else {
                                    ModerationStuff.mute(target);
                                    p.sendMessage(Messages.starter + "7You have muted §f" + target.getName());
                                }
                            } else {
                                p.sendMessage(Messages.starter + "cThat player is either offline or null!");
                            }
                        } else {
                            Messages.send(p, Messages.invalidCmd);
                        }
                    } else {
                        Messages.send(p, Messages.noperms);
                    }
                } else if (command.getName().equalsIgnoreCase("freeze")) {
                    if (p.isOp()) {
                        if (args.length >= 1) {
                            Player target = Bukkit.getPlayer(args[0]);
                            if (target != null) {
                                if (ModerationStuff.isFrozen(target)) {
                                    ModerationStuff.unfreeze(target);
                                    p.sendMessage(Messages.starter + "7You have unfrozen §f" + target.getName());
                                } else {
                                    ModerationStuff.freeze(target);
                                    p.sendMessage(Messages.starter + "7You have frozen §f" + target.getName());
                                }
                            } else {
                                p.sendMessage(Messages.starter + "cThat player is either offline or null!");
                            }
                        } else {
                            Messages.send(p, Messages.invalidCmd);
                        }
                    } else {
                        Messages.send(p, Messages.noperms);
                    }
                } else if (command.getName().equalsIgnoreCase("spawnentities")) {
                    if (p.isOp()) {
                        if (args.length >= 2) {
                            if (EntityEvents.getEntityTypes().contains(args[0])) {
                                try {
                                    EntityType type = EntityType.valueOf(args[0].toUpperCase());
                                    int count = Integer.parseInt(args[1]);
                                    for (int i = 0; i < count; i ++) {
                                        p.getWorld().spawnEntity(p.getLocation(),type);
                                    }
                                    Messages.bmOp(Messages.starter + "f" + p.getName() + " §7spawned §f" + count + " " + type.name().toLowerCase() + "(s)");
                                } catch (IllegalArgumentException | NullPointerException exception) {
                                    p.sendMessage(Messages.starter + "cSeems like there's an error, try switching up the command a bit!");
                                }
                            } else {
                                p.sendMessage(Messages.starter + "cWhoa! Not a modded server means no customs mobs lmao");
                            }
                        } else {
                            Messages.send(p, Messages.invalidCmd);
                        }
                    } else {
                        Messages.send(p, Messages.noperms);
                    }
                } else if (command.getName().equalsIgnoreCase("discord")) {
                    if (args.length >= 2) {
                        if (p.isOp()) {
                            switch (args[0]) {
                                case "setlink":
                                    String link = args[1];
                                    if (link.contains("https://discord.gg/")) {
                                        plugin.getConfig().set("server.discord_link",link);
                                        plugin.saveConfig();
                                        p.sendMessage(Messages.starter + "7Linked new discord link! §3" + link);
                                    } else {
                                        p.sendMessage(Messages.starter + "cPlease send a valid discord link!");
                                    }
                                    break;
                            }
                        } else {
                            Messages.send(p, Messages.noperms);
                        }
                    } else if (args.length == 1) {
                        if (p.isOp()) {
                            switch (args[0]) {
                                case "remove":
                                    plugin.getConfig().set("server.discord_link",null);
                                    plugin.saveConfig();
                                    p.sendMessage(Messages.starter + "7Removed the discord link");
                                    break;
                            }
                        } else {
                            Messages.send(p, Messages.noperms);
                        }
                    } else {
                        String link = plugin.getConfig().getString("server.discord_link");
                        if (link != null) {
                            TextComponent message = new TextComponent(link);
                            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,link));
                            p.sendMessage(Messages.starter + "7Join our discord!");
                            p.spigot().sendMessage(message);
                        } else {
                            p.sendMessage(Messages.starter + "7No discord link linked yet!");
                        }
                    }
                }
            } catch (IllegalArgumentException | NullPointerException exception) {
                p.sendMessage(Messages.starter + "cOops! There seems to be a problem! Please check your command again!");
            }

            return true;
        } else {
            // Console Commands
        }

        return false;
    }

    // Methods
    public static boolean hasOutgoingRequest(Player player) {
        return tpa.containsKey(player.getName()) || tpahere.containsKey(player.getName());
    }

    public static boolean hasIncomingRequest(Player player) {
        return tpa.containsValue(player.getName()) || tpahere.containsValue(player.getName());
    }

    public static Player getTpaRequester(Player player) {
        for (Map.Entry<String,String> entry : tpa.entrySet()) {
            if (entry.getValue().equals(player.getName())) {
                return Bukkit.getPlayer(entry.getKey());
            }
        }
        return null;
    }

    public static Player getTpahereRequester(Player player) {
        for (Map.Entry<String,String> entry : tpahere.entrySet()) {
            if (entry.getValue().equals(player.getName())) {
                return Bukkit.getPlayer(entry.getKey());
            }
        }
        return null;
    }

    public static void runTimedCommand(String command, int waitSec) {
        new BukkitRunnable() {
            int iterations = 0;

            @Override
            public void run() {
                if (iterations < waitSec) {
                    int timeLeft = waitSec - iterations;
                    if (timeLeft <= 10) {
                        Messages.titleAll("§e" + command,"§cin §e" + timeLeft + " §cseconds",0,20,0);
                        Messages.soundAll(Sound.ENTITY_BLAZE_HURT,10,0.1F);
                    } else if (timeLeft == waitSec) {
                        Messages.titleAll("§e" + command,"§cin §e" + timeLeft + " §cseconds",0,100,0);
                        Messages.soundAll(Sound.ENTITY_WITHER_AMBIENT,10,0.1F);
                    }
                    iterations ++;
                } else {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),command);
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin,0,20);
    }
}
