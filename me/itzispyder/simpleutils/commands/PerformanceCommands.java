package me.itzispyder.simpleutils.commands;

import me.itzispyder.simpleutils.SimpleUtils;
import me.itzispyder.simpleutils.events.EntityEvents;
import me.itzispyder.simpleutils.events.ModerationStuff;
import me.itzispyder.simpleutils.files.SpawnControl;
import me.itzispyder.simpleutils.utils.ItemManger;
import me.itzispyder.simpleutils.utils.StringManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class PerformanceCommands implements CommandExecutor {

    // instance of the main class
    static SimpleUtils plugin;
    public PerformanceCommands(SimpleUtils plugin) {
        PerformanceCommands.plugin = plugin;
    }

    // Commands
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            // Player commands
            if (command.getName().equalsIgnoreCase("spawncontrol")) {
                if (p.isOp()) {
                    if (args.length >= 2) {
                        String target = args[0].toLowerCase();
                        boolean toggle = Boolean.parseBoolean(args[1].toLowerCase());

                        if (EntityEvents.getEntityTypes().contains(target)) {
                            SpawnControl.get().set("server.spawning." + target, toggle);
                            SpawnControl.save();
                            Bukkit.getServer().broadcastMessage(StringManager.starter + "7Set §f" + target  + " §7spawning to §f" + toggle);
                        } else if (Objects.equals(args[0],"#ALL")) {
                            for (String type : EntityEvents.getEntityTypes()) {
                                SpawnControl.get().set("server.spawning." + type, toggle);
                                SpawnControl.save();
                            }
                            Bukkit.getServer().broadcastMessage(StringManager.starter + "7Set §f" + target  + " §7spawning to §f" + toggle);
                        } else {
                            p.sendMessage(StringManager.starter + "cWhoa there, this ain't no modded server! No custom mobs here lmfao!");
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
                }
            } else if (command.getName().equalsIgnoreCase("receivecommandlogs")) {
                if (p.isOp()) {
                    if (!EntityEvents.receivingCommands.contains(p)) {
                        EntityEvents.receivingCommands.add(p);
                        p.sendMessage(StringManager.starter + "aYou will now start receiving command logs!");
                    } else {
                        EntityEvents.receivingCommands.remove(p);
                        p.sendMessage(StringManager.starter + "aYou will no longer receive command logs!");
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
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
                        ItemManger.setAsPlayerStats(paper,target);
                        p.getWorld().dropItemNaturally(p.getLocation(),paper);
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
                }
            } else if (command.getName().equalsIgnoreCase("server-timedreload")) {
                if (p.isOp()) {
                    if (args.length >= 1) {
                        try {
                            int waitTime = Integer.parseInt(args[0]);
                            runTimedCommand("reload confirm",waitTime);
                        } catch (IllegalArgumentException exception) {
                            StringManager.send(p,StringManager.invalidCmd);
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
                }
            } else if (command.getName().equalsIgnoreCase("server-timedrestart")) {
                if (p.isOp()) {
                    if (args.length >= 1) {
                        try {
                            int waitTime = Integer.parseInt(args[0]);
                            runTimedCommand("restart",waitTime);
                        } catch (IllegalArgumentException exception) {
                            StringManager.send(p,StringManager.invalidCmd);
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
                }
            } else if (command.getName().equalsIgnoreCase("server-timedshutdown")) {
                if (p.isOp()) {
                    if (args.length >= 1) {
                        try {
                            int waitTime = Integer.parseInt(args[0]);
                            runTimedCommand("stop",waitTime);
                        } catch (IllegalArgumentException exception) {
                            StringManager.send(p,StringManager.invalidCmd);
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
                }
            } else if (command.getName().equalsIgnoreCase("clearchat")) {
                if (p.isOp()) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < 690; i ++) {
                        builder.append("\n ");
                    }
                    builder.append(StringManager.starter).append("f").append(p.getName()).append(" §7cleared chat");
                    Bukkit.getServer().broadcastMessage(builder.toString());
                } else {
                    StringManager.send(p,StringManager.noperms);
                }
            } else if (command.getName().equalsIgnoreCase("clearall")) {
                if (p.isOp()) {
                    int count = 0;
                    if (args.length == 0) {
                        for (World world : Bukkit.getServer().getWorlds()) {
                            for (Entity entity : world.getEntities()) {
                                if (!(entity instanceof Player)) {
                                    entity.remove();
                                    count ++;
                                }
                            }
                        }
                        StringManager.bmOp(StringManager.starter + "f" + p.getName() + " §7cleared all entities §f(" + count + ")");
                    } else {
                        if (EntityEvents.getEntityTypes().contains(args[0])) {
                            for (World world : Bukkit.getServer().getWorlds()) {
                                for (Entity entity : world.getEntities()) {
                                    if (entity.getType().name().equalsIgnoreCase(args[0])) {
                                        entity.remove();
                                        count ++;
                                    }
                                }
                            }
                        } else {
                            switch (args[0]) {
                                case "#ALL":
                                    for (World world : Bukkit.getServer().getWorlds()) {
                                        for (Entity entity : world.getEntities()) {
                                            if (!(entity instanceof Player)) {
                                                entity.remove();
                                                count ++;
                                            }
                                        }
                                    }
                                    break;
                                case "#COMMON":
                                    for (World world : Bukkit.getServer().getWorlds()) {
                                        for (Entity entity : world.getEntities()) {
                                            if (!(entity instanceof Player)
                                                    || entity instanceof Item
                                                    || entity instanceof ExperienceOrb
                                                    || entity instanceof Arrow
                                                    || entity instanceof FallingBlock
                                                    || entity instanceof Trident
                                            ) {
                                                entity.remove();
                                                count ++;
                                            }
                                        }
                                    }
                                    break;
                            }
                        }
                        StringManager.bmOp(StringManager.starter + "f" + p.getName() + " §7cleared all " + args[0] + " §f(" + count + ")");
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
                }
            } else if (command.getName().equalsIgnoreCase("mute")) {
                if (p.isOp()) {
                    if (args.length >= 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            if (ModerationStuff.isMuted(target)) {
                                ModerationStuff.unmute(target);
                                p.sendMessage(StringManager.starter + "7You have unmuted §f" + target.getName());
                            } else {
                                ModerationStuff.mute(target);
                                p.sendMessage(StringManager.starter + "7You have muted §f" + target.getName());
                            }
                        } else {
                            p.sendMessage(StringManager.starter + "cThat player is either offline or null!");
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
                }
            } else if (command.getName().equalsIgnoreCase("freeze")) {
                if (p.isOp()) {
                    if (args.length >= 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            if (ModerationStuff.isFrozen(target)) {
                                ModerationStuff.unfreeze(target);
                                p.sendMessage(StringManager.starter + "7You have unfrozen §f" + target.getName());
                            } else {
                                ModerationStuff.freeze(target);
                                p.sendMessage(StringManager.starter + "7You have frozen §f" + target.getName());
                            }
                        } else {
                            p.sendMessage(StringManager.starter + "cThat player is either offline or null!");
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
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
                                StringManager.bmOp(StringManager.starter + "f" + p.getName() + " §7spawned §f" + count + " " + type.name().toLowerCase() + "(s)");
                            } catch (IllegalArgumentException | NullPointerException exception) {
                                p.sendMessage(StringManager.starter + "cSeems like there's an error, try switching up the command a bit!");
                            }
                        } else {
                            p.sendMessage(StringManager.starter + "cWhoa! Not a modded server means no customs mobs lmao");
                        }
                    } else {
                        StringManager.send(p,StringManager.invalidCmd);
                    }
                } else {
                    StringManager.send(p,StringManager.noperms);
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
                                    p.sendMessage(StringManager.starter + "7Linked new discord link! §3" + link);
                                } else {
                                    p.sendMessage(StringManager.starter + "cPlease send a valid discord link!");
                                }
                                break;
                        }
                    } else {
                        StringManager.send(p,StringManager.noperms);
                    }
                } else if (args.length == 1) {
                    if (p.isOp()) {
                        switch (args[0]) {
                            case "remove":
                                plugin.getConfig().set("server.discord_link",null);
                                plugin.saveConfig();
                                p.sendMessage(StringManager.starter + "7Removed the discord link");
                                break;
                        }
                    } else {
                        StringManager.send(p,StringManager.noperms);
                    }
                } else {
                    String link = plugin.getConfig().getString("server.discord_link");
                    if (link != null) {
                        TextComponent message = new TextComponent(link);
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,link));
                        p.sendMessage(StringManager.starter + "7Join our discord!");
                        p.spigot().sendMessage(message);
                    } else {
                        p.sendMessage(StringManager.starter + "7No discord link linked yet!");
                    }
                }
            }

            return true;
        } else {
            // Console Commands
        }

        return false;
    }

    // Methods
    public static void runTimedCommand(String command, int waitSec) {
        new BukkitRunnable() {
            int iterations = 0;

            @Override
            public void run() {
                if (iterations < waitSec) {
                    int timeLeft = waitSec - iterations;
                    if (timeLeft <= 10) {
                        StringManager.titleAll("§e" + command,"§cin §e" + timeLeft + " §cseconds",0,20,0);
                        StringManager.soundAll(Sound.ENTITY_BLAZE_HURT,10,0.1F);
                    } else if (timeLeft == waitSec) {
                        StringManager.titleAll("§e" + command,"§cin §e" + timeLeft + " §cseconds",0,100,0);
                        StringManager.soundAll(Sound.ENTITY_WITHER_AMBIENT,10,0.1F);
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
