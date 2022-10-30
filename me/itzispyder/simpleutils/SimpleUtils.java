package me.itzispyder.simpleutils;

import me.itzispyder.simpleutils.commands.Commands;
import me.itzispyder.simpleutils.commands.PerformanceCommands;
import me.itzispyder.simpleutils.commands.TabCompleters;
import me.itzispyder.simpleutils.events.EntityEvents;
import me.itzispyder.simpleutils.events.ModerationStuff;
import me.itzispyder.simpleutils.events.SpawnEvent;
import me.itzispyder.simpleutils.files.*;
import me.itzispyder.simpleutils.inventory.InventoryManager;
import me.itzispyder.simpleutils.server.ChatManager;
import me.itzispyder.simpleutils.server.PluginMenu;
import me.itzispyder.simpleutils.utils.ItemManager;
import me.itzispyder.simpleutils.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class SimpleUtils extends JavaPlugin {
    public static double tps;
    private static int[] uptime = {0,0,0,0,0};
    public static double timer = 0;


    @Override
    public void onEnable() {
        // Plugin startup logic
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(Messages.starter + "a§l( ✔ )");
        }
        getServer().getLogger().warning("Enabling SimpleUtils...");

        // Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        SpawnControl.setUp();
        SpawnControl.get().options().copyDefaults(true);
        SpawnControl.save();
        WarpLocations.setup();
        WarpLocations.get().options().copyDefaults(true);
        WarpLocations.save();
        PlayerHomes.setup();
        PlayerHomes.get().options().copyDefaults(true);
        PlayerHomes.save();
        PlayerListTab.setup();
        PlayerListTab.get().options().copyDefaults(true);
        PlayerListTab.save();
        ClearLag.setUp();
        ClearLag.get().options().copyDefaults(true);
        ClearLag.save();

        // Commands
        getCommand("invsee").setExecutor(new Commands(this));
        getCommand("ec").setExecutor(new Commands(this));
        getCommand("tpa").setExecutor(new Commands(this));
        getCommand("tpahere").setExecutor(new Commands(this));
        getCommand("tpcancel").setExecutor(new Commands(this));
        getCommand("tpaccept").setExecutor(new Commands(this));
        getCommand("tpdeny").setExecutor(new Commands(this));
        getCommand("addwarp").setExecutor(new Commands(this));
        getCommand("addwarp").setTabCompleter(new TabCompleters());
        getCommand("removewarp").setExecutor(new Commands(this));
        getCommand("removewarp").setTabCompleter(new TabCompleters());
        getCommand("warp").setExecutor(new Commands(this));
        getCommand("warp").setTabCompleter(new TabCompleters());
        getCommand("fakechat").setExecutor(new Commands(this));
        getCommand("fakechat").setTabCompleter(new TabCompleters());
        getCommand("fakeop").setExecutor(new Commands(this));
        getCommand("server-info").setExecutor(new Commands(this));
        getCommand("spawncontrol").setExecutor(new PerformanceCommands());
        getCommand("spawncontrol").setTabCompleter(new TabCompleters());
        getCommand("spawn").setExecutor(new Commands(this));
        getCommand("spawn").setTabCompleter(new TabCompleters());
        getCommand("trash").setExecutor(new Commands(this));
        getCommand("receivecommandlogs").setExecutor(new Commands(this));
        getCommand("afk").setExecutor(new Commands(this));
        getCommand("statspaper").setExecutor(new Commands(this));
        getCommand("server-timedreload").setExecutor(new Commands(this));
        getCommand("server-timedreload").setTabCompleter(new TabCompleters());
        getCommand("server-timedrestart").setExecutor(new Commands(this));
        getCommand("server-timedrestart").setTabCompleter(new TabCompleters());
        getCommand("server-timedshutdown").setExecutor(new Commands(this));
        getCommand("server-timedshutdown").setTabCompleter(new TabCompleters());
        getCommand("clearchat").setExecutor(new Commands(this));
        getCommand("clearall").setExecutor(new PerformanceCommands());
        getCommand("clearall").setTabCompleter(new TabCompleters());
        getCommand("mute").setExecutor(new Commands(this));
        getCommand("freeze").setExecutor(new Commands(this));
        getCommand("spawnentities").setExecutor(new Commands(this));
        getCommand("spawnentities").setTabCompleter(new TabCompleters());
        getCommand("discord").setExecutor(new Commands(this));
        getCommand("discord").setTabCompleter(new TabCompleters());
        getCommand("home").setExecutor(new Commands(this));
        getCommand("home").setTabCompleter(new TabCompleters());
        getCommand("sethome").setExecutor(new Commands(this));
        getCommand("sethome").setTabCompleter(new TabCompleters());
        getCommand("delhome").setExecutor(new Commands(this));
        getCommand("delhome").setTabCompleter(new TabCompleters());
        getCommand("broadcast").setExecutor(new Commands(this));
        getCommand("broadcast").setTabCompleter(new TabCompleters());
        getCommand("pl").setExecutor(new PerformanceCommands());
        getCommand("pl").setTabCompleter(new TabCompleters());
        getCommand("clearlag").setExecutor(new PerformanceCommands());
        getCommand("clearlag").setTabCompleter(new TabCompleters());

        // Events
        getServer().getPluginManager().registerEvents(new InventoryManager(this),this);
        getServer().getPluginManager().registerEvents(new EntityEvents(),this);
        getServer().getPluginManager().registerEvents(new ModerationStuff(),this);
        getServer().getPluginManager().registerEvents(new ChatManager(),this);
        getServer().getPluginManager().registerEvents(new SpawnEvent(),this);
        getServer().getPluginManager().registerEvents(new PluginMenu(),this);

        // Items
        ItemManager.setItems();

        // Loops
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            long sec;
            long currentSec;
            int ticks;

            @Override
            public void run() {
                sec = System.currentTimeMillis()/1000;
                if (currentSec == sec) {
                    ticks ++;
                    uptime[4] ++;
                    if (uptime[4] >= 20) {
                        uptime[3] ++;
                        uptime[4] = 0;
                    }
                    if (uptime[3] >= 60) {
                        uptime[2] ++;
                        uptime[3] = 0;
                    }
                    if (uptime[2] >= 60) {
                        uptime[1] ++;
                        uptime[2] = 0;
                    }
                    if (uptime[1] >= 24) {
                        uptime[0] ++;
                        uptime[1] = 0;
                    }
                } else {
                    currentSec = sec;
                    tps = (tps == 0 ? ticks : ((ticks + tps)/2)) + 1;
                    if (tps > 20) {
                        tps = 20;
                    }
                    ticks = 0;
                }
            }
        },0,1);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (ClearLag.isResumed()) {
                    if (timer < ClearLag.getInterval()) {
                        int timeLeft = (int) (ClearLag.getInterval() - timer);
                        switch (timeLeft) {
                            case 60:
                            case 30:
                            case 10:
                                getServer().broadcastMessage(Messages.starter + "cClearing all §7\"" + ClearLag.getClearType() + "\" §cin §7" + timeLeft + " §cseconds!");
                                break;
                        }
                        timer ++;
                    } else {
                        getServer().broadcastMessage(Messages.starter + "cCleared all §7\"" + ClearLag.getClearType() + "\" §!");
                        getServer().dispatchCommand(getServer().getConsoleSender(),"clearall " + ClearLag.getClearType());
                        timer = 0;
                    }
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (PlayerListTab.isEnabled()) {
                        p.setPlayerListHeader(PlayerListTab.getHeader(p));
                        p.setPlayerListFooter(PlayerListTab.getFooter(p));
                    }
                }
            }
        }.runTaskTimer(this,0,20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(Messages.starter + "c§l( ✕ )");
        }
        getServer().getLogger().warning("Disabling SimpleUtils...");
    }


    public static String getUptime() {
        return uptime[0] + "d " +
                uptime[1] + "h " +
                uptime[2] + "m " +
                uptime[3] + "s " +
                uptime[4] + "t ";
    }

    public static String getTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.getHour() + ":" +
                now.getMinute() + ":" +
                now.getSecond();
    }

    public FileConfiguration config() {
        return getConfig();
    }


    public static Plugin getPlugin(String name) {
        if (getPluginList().contains(name)) {
            return Bukkit.getServer().getPluginManager().getPlugin(name);
        }
        return null;
    }
    public static List<String> getPluginList() {
        List<String> list = new ArrayList<>();
        for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
            list.add(plugin.getName());
        }
        return list;
    }

    public static List<String> getEnabledPlugins() {
        List<String> list = new ArrayList<>();
        for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
            if (plugin.isEnabled()) {
                list.add(plugin.getName());
            }
        }
        return list;
    }
}
























