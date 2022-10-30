package me.itzispyder.simpleutils.events;

import me.itzispyder.simpleutils.files.SpawnControl;
import me.itzispyder.simpleutils.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityEvents implements Listener {

    // Variables
    public static List<Player> receivingCommands = new ArrayList<>();
    public static HashMap<Player, Location> afkList = new HashMap<>();
    static HashMap<String,Long> clickCooldown = new HashMap<>();

    // Events
    @EventHandler
    public static void PlayerCommandEvent(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        removeAfk(p);
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.isOp() && receivingCommands.contains(online)) {
                online.sendMessage("§a§o" + p.getName() + ": " + e.getMessage());
            }
        }
    }

    @EventHandler
    public static void ServerCommandEvent(ServerCommandEvent e) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.isOp() && receivingCommands.contains(online)) {
                online.sendMessage("§a§oServer: /" + e.getCommand());
            }
        }
    }

    // Players
    @EventHandler
    public static boolean PlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        removeAfk(p);

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            try {
                if (clickCooldown.containsKey(p.getName()) && clickCooldown.get(p.getName()) > System.currentTimeMillis()) {
                    return false;
                }

                clickCooldown.put(p.getName(),System.currentTimeMillis() + (200));
                ItemStack main = p.getInventory().getItemInMainHand();

                if (p.getInventory().getHelmet().getType().name().contains("HELMET") && main.getType().name().contains("HELMET")) {
                    ItemStack helmet = p.getInventory().getHelmet();
                    p.getInventory().setHelmet(p.getInventory().getItemInMainHand());
                    p.getInventory().setItemInMainHand(helmet);
                    return true;
                }
                if ((p.getInventory().getChestplate().getType().name().contains("CHESTPLATE") || p.getInventory().getChestplate().getType().name().contains("ELYTRA"))
                        && (main.getType().name().contains("CHESTPLATE") || main.getType().name().contains("ELYTRA"))
                ) {
                    ItemStack chestplate = p.getInventory().getChestplate();
                    p.getInventory().setChestplate(p.getInventory().getItemInMainHand());
                    p.getInventory().setItemInMainHand(chestplate);
                    return true;
                }
                if (p.getInventory().getLeggings().getType().name().contains("LEGGINGS") && main.getType().name().contains("LEGGINGS")) {
                    ItemStack leggings = p.getInventory().getLeggings();
                    p.getInventory().setLeggings(p.getInventory().getItemInMainHand());
                    p.getInventory().setItemInMainHand(leggings);
                    return true;
                }
                if (p.getInventory().getBoots().getType().name().contains("BOOTS") && main.getType().name().contains("BOOTS")) {
                    ItemStack boots = p.getInventory().getBoots();
                    p.getInventory().setBoots(p.getInventory().getItemInMainHand());
                    p.getInventory().setItemInMainHand(boots);
                    return true;
                }
            } catch (NullPointerException exception) {
                // empty
            }
        }

        return true;
    }

    @EventHandler
    public static void PlayerMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (isAfk(p) && afkList.get(p).getWorld() == p.getWorld() && afkList.get(p).distanceSquared(p.getLocation()) > 1) {
            removeAfk(p);
        }
    }

    @EventHandler
    public static void PlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        removeAfk(p);
    }

    // Methods
    public static List<String> getEntityTypes() {
        List<String> types = new ArrayList<>();
        for (EntityType entityType : EntityType.class.getEnumConstants()) {
            types.add(entityType.name().toLowerCase());
        }
        types.remove("player");
        return types;
    }

    public static void addAfk(Player player) {
        if (!isAfk(player)) {
            afkList.put(player,player.getLocation());
            Bukkit.getServer().broadcastMessage(Messages.starter + "4§l§oAFK §7§o" + player.getName() + " is now afk");
        }
    }

    public static void removeAfk(Player player) {
        if (isAfk(player)) {
            afkList.remove(player);
            Bukkit.getServer().broadcastMessage(Messages.starter + "4§l§oAFK §7§o" + player.getName() + " is no longer afk");
        }
    }

    public static boolean isAfk(Player player) {
        return afkList.containsKey(player);
    }
}
