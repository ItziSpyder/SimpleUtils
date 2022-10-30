package me.itzispyder.simpleutils.server;

import me.itzispyder.simpleutils.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatManager implements Listener {

    @EventHandler
    public static void PlayerChatEvent(AsyncPlayerChatEvent e) {
        e.setMessage(Messages.implementColors(e.getMessage()));
    }

    @EventHandler
    public static void PlayerCommandEvent(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();

        switch (msg.toLowerCase().trim()) {
            case "/plugins":
            case "/plugin":
            case "/pl":
                e.setMessage("/simpleutils:pl");
                break;
            case "/tps":
                e.setMessage("/simpleutils:tps");
                break;
            case "/rl":
            case "/reload":
                e.setMessage("/reload confirm");
                break;
        }
    }
}
