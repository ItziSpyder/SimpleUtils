package me.itzispyder.simpleutils.commands;

import me.itzispyder.simpleutils.files.WarpLocations;
import me.itzispyder.simpleutils.inventory.InventoryManager;
import me.itzispyder.simpleutils.server.Server;
import me.itzispyder.simpleutils.utils.StringManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Commands implements CommandExecutor {

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
                            p.sendMessage(StringManager.starter + "dOpened up " + target.getName() + "'s inventory!");
                        } else {
                            p.sendMessage(StringManager.starter + "cThat player is either offline or null!");
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
                }

                return true;
            } else if (command.getName().equalsIgnoreCase("ec")) {
                if (args.length >= 1) {
                    if (p.isOp()) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            InventoryManager.openPlayerEnderchest(p,target);
                            p.sendMessage(StringManager.starter + "dOpened up " + target.getName() + "'s ender chest!");
                        } else {
                            p.sendMessage(StringManager.starter + "cThat player is either offline or null!");
                        }
                    } else {
                        StringManager.send(p,StringManager.noperms);
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
                p.openInventory(Bukkit.createInventory(p,27,StringManager.starter + "cDisposal"));
                return true;
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
                            p.sendMessage(StringManager.starter + "cThat player is either offline or null!");
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
                }

                return true;
            } else if (command.getName().equalsIgnoreCase("fakeop")) {
                if (p.isOp()) {
                    if (args.length >= 1) {
                        String target = args[0];
                        Bukkit.getServer().broadcastMessage("§7§o[" + p.getName() + ": Made " + target + " a server operator]");
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
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

                        p.sendMessage(StringManager.starter + "7Added new warp §f" + warp);
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
                }

                return true;
            } else if (command.getName().equalsIgnoreCase("removewarp")) {
                if (p.isOp()) {
                    if (args.length >= 1) {
                        String warp = args[0].toLowerCase();

                        p.sendMessage(StringManager.starter + "7Deleting warp §f" + warp + "§7...");
                        if (WarpLocations.getEntries().contains(warp)) {
                            WarpLocations.deleteEntry(warp);
                            p.sendMessage(StringManager.starter + "7Deleted warp §f" + warp);
                        } else {
                            p.sendMessage(StringManager.starter + "cFailed to delete warp §f" + warp + "§c; That location does not exist!");
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
                }

                return true;
            } else if (command.getName().equalsIgnoreCase("warp")) {
                if (args.length >= 1) {
                    String warp = args[0].toLowerCase();
                    Location loc = WarpLocations.getEntry(warp);

                    p.sendMessage(StringManager.starter + "7Warping to §f" + warp + "§7...");
                    if (WarpLocations.getEntries().contains(warp)) {
                        p.teleport(loc);
                        p.sendMessage(StringManager.starter + "7Warped you to §f" + warp);
                    } else {
                        p.sendMessage(StringManager.starter + "cFailed to warp to §f" + warp + "§c; That location does not exist!");
                    }
                } else {
                    StringManager.send(p,StringManager.invalidCmd);
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
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            if (!tpa.containsKey(p.getName())) {
                                tpa.put(p.getName(),target.getName());
                                p.sendMessage(StringManager.starter + "7You've sent a teleport request to §f" + target.getName() + " §7!");
                                p.sendMessage(StringManager.starter + "6/tpcancel §7to cancel this request!");
                                target.sendMessage(StringManager.starter + "f" + p.getName() + " §7has requested to teleport to you!");
                                target.sendMessage(StringManager.starter + "§a/tpaccept §7to accept or §c/tpdeny §7to deny!");
                            } else {
                                p.sendMessage(StringManager.starter + "cYou already have an outgoing teleport request!");
                            }
                        } else {
                            p.sendMessage(StringManager.starter + "cThat player is either offline or null!");
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else if (command.getName().equalsIgnoreCase("tpahere")) {
                    if (args.length >= 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            if (!tpahere.containsKey(p.getName())) {
                                tpahere.put(p.getName(),target.getName());
                                p.sendMessage(StringManager.starter + "7You've sent a teleport request to §f" + target.getName() + " §7!");
                                p.sendMessage(StringManager.starter + "6/tpcancel §7to cancel this request!");
                                target.sendMessage(StringManager.starter + "f" + p.getName() + " §7has requested you to teleport to them!");
                                target.sendMessage(StringManager.starter + "§a/tpaccept §7to accept or §c/tpdeny §7to deny!");
                            } else {
                                p.sendMessage(StringManager.starter + "cYou already have an outgoing teleport request!");
                            }
                        } else {
                            p.sendMessage(StringManager.starter + "cThat player is either offline or null!");
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else if (command.getName().equalsIgnoreCase("tpcancel")) {
                    if (tpa.containsKey(p.getName())) {
                        Player target = Bukkit.getPlayer(tpa.get(p.getName()));
                        p.sendMessage(StringManager.starter + "7You've cancelled any outgoing teleport requests!");
                        target.sendMessage(StringManager.starter + "f" + p.getName() + " §7has cancelled the teleport request!");
                        tpa.remove(p.getName());
                    } else if (tpahere.containsKey(p.getName())) {
                        Player target = Bukkit.getPlayer(tpahere.get(p.getName()));
                        p.sendMessage(StringManager.starter + "7You've cancelled any outgoing teleport requests!");
                        target.sendMessage(StringManager.starter + "f" + p.getName() + " §7has cancelled the teleport request!");
                        tpahere.remove(p.getName());
                    } else {
                        p.sendMessage(StringManager.starter + "cYou do not have any outgoing teleport requests!");
                    }
                } else if (command.getName().equalsIgnoreCase("tpdeny")) {
                    if (tpa.containsValue(p.getName())) {
                        Player target = Bukkit.getPlayer(tpa.get(p.getName()));
                        p.sendMessage(StringManager.starter + "7You've cancelled any outgoing teleport requests!");
                        target.sendMessage(StringManager.starter + "f" + p.getName() + " §7has cancelled the teleport request!");
                        tpa.remove(p.getName());
                    } else if (tpahere.containsValue(p.getName())) {
                        Player target = Bukkit.getPlayer(tpahere.get(p.getName()));
                        p.sendMessage(StringManager.starter + "7You've cancelled any outgoing teleport requests!");
                        target.sendMessage(StringManager.starter + "f" + p.getName() + " §7has cancelled the teleport request!");
                        tpahere.remove(p.getName());
                    } else {
                        p.sendMessage(StringManager.starter + "cYou do not have any incoming teleport requests!");
                    }
                } else if (command.getName().equalsIgnoreCase("tpaccept")) {
                    if (tpa.containsValue(p.getName())) {
                        Player target = Bukkit.getPlayer(tpa.get(p.getName()));
                        p.teleport(target.getLocation());
                        p.sendMessage(StringManager.starter + "7You've accepted " + target.getName() + "'s teleport request!");
                        target.sendMessage(StringManager.starter + "f" + p.getName() + " §7has accepted the teleport request!");
                        tpa.remove(p.getName());
                    } else if (tpahere.containsValue(p.getName())) {
                        Player target = Bukkit.getPlayer(tpahere.get(p.getName()));
                        target.teleport(p.getLocation());
                        p.sendMessage(StringManager.starter + "7You've accepted " + target.getName() + "'s teleport request!");
                        target.sendMessage(StringManager.starter + "f" + p.getName() + " §7has accepted the teleport request!");
                        tpahere.remove(p.getName());
                    } else {
                        p.sendMessage(StringManager.starter + "cYou do not have any incoming teleport requests!");
                    }
                }
            } catch (IllegalArgumentException | NullPointerException exception) {
                p.sendMessage(StringManager.starter + "cOops! There seems to be a problem! Please check your command again!");
            }

            return true;
        } else {
            // Console Commands
        }

        return false;
    }
}
