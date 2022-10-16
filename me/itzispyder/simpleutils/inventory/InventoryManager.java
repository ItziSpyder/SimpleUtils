package me.itzispyder.simpleutils.inventory;

import me.itzispyder.simpleutils.SimpleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager implements Listener {

    // Instance of the main class
    static SimpleUtils plugin;
    public InventoryManager(SimpleUtils plugin) {
        InventoryManager.plugin = plugin;
    }

    // Events
    @EventHandler
    public static void InventoryCloseEvent(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        String title = e.getView().getTitle();
        Inventory inv = e.getInventory();

        try {
            if (!inv.getType().equals(InventoryType.PLAYER)) {
                if (title.contains("'s inventory")) {
                    Player target = Bukkit.getPlayer(title.substring(0,title.length() - 12));
                    assert target != null;
                    for (int i = 0; i < 41; i ++) {
                        ItemStack item = inv.getItem(i);
                        if (item != null) {
                            target.getInventory().setItem(i,item);
                        } else {
                            target.getInventory().setItem(i,new ItemStack(Material.AIR));
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | NullPointerException exception) {
            // empty
        }
    }

    // Invsee Manager
    public static void openPlayerInventory(Player player, Player target) {
        Inventory menu = Bukkit.createInventory(player,54,target.getName() + "'s inventory");

        menu.setContents(target.getInventory().getContents());

        player.openInventory(menu);
    }

    public static void openPlayerEnderchest(Player player, Player target) {
        player.openInventory(target.getEnderChest());
    }
}
