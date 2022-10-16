package me.itzispyder.simpleutils.events;

import me.itzispyder.simpleutils.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class ModerationStuff implements Listener {

    // Variables
    public static List<Player> muted = new ArrayList<>();
    public static List<Player> frozen = new ArrayList<>();

    // Events
    @EventHandler
    public static void PlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (isMuted(p)) {
            e.setCancelled(true);
            Messages.send(p, Messages.starter + "4You are muted");
        }
    }

    @EventHandler
    public static void PlayerMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (isFrozen(p)) {
            e.setCancelled(true);
            p.sendTitle("§4You are frozen","",0,40,0);
        }
    }

    @EventHandler
    public static void PlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (isFrozen(p)) {
            e.setCancelled(true);
            p.sendTitle("§4You are frozen","",0,40,0);
        }
    }

    @EventHandler
    public static void PlayerCommandEvent(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String command = e.getMessage();
        if (isMuted(p)) {
            if (command.contains("/me")
                    || command.contains("/say")
                    || command.contains("/minecraft:")
            ) {
                e.setCancelled(true);
                Messages.send(p, Messages.starter + "4You are muted");
            }
        }
    }

    // Methods
    public static boolean isMuted(Player player) {
        return muted.contains(player);
    }

    public static void mute(Player player) {
        if (!isMuted(player)) {
            muted.add(player);
            player.sendMessage(Messages.starter + "4You have been muted");
        }
    }

    public static void unmute(Player player) {
        if (isMuted(player)) {
            muted.remove(player);
            player.sendMessage(Messages.starter + "4You have been unmuted");
        }
    }

    public static boolean isFrozen(Player player) {
        return frozen.contains(player);
    }

    public static void freeze(Player player) {
        if (!isFrozen(player)) {
            frozen.add(player);
            player.sendMessage(Messages.starter + "4You have been frozen");
        }
    }

    public static void unfreeze(Player player) {
        if (isFrozen(player)) {
            frozen.remove(player);
            player.sendMessage(Messages.starter + "4You have been unfrozen");
        }
    }
}
