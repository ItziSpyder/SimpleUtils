package me.itzispyder.simpleutils.server;

import me.itzispyder.simpleutils.utils.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {
    @EventHandler
    public static void PlayerChatEvent(AsyncPlayerChatEvent e) {
        e.setMessage(Messages.implementColors(e.getMessage()));
    }
}
