package me.itzispyder.simpleutils.utils;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    public static void setItems() {
        setPane_red();
        setPane_black();
        setPane_white();
        setCompass();
        setNext();
        setPane_brown();
        setPrevious();
    }

    public static ItemStack pane_red;
    public static ItemStack pane_white;
    public static ItemStack pane_black;
    public static ItemStack pane_brown;
    public static ItemStack air = new ItemStack(Material.AIR);
    public static ItemStack compass;
    public static ItemStack previous;
    public static ItemStack next;


    public static void setPane_red() {
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");

        item.setItemMeta(meta);
        pane_red = item;
    }
    public static void setPane_white() {
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");

        item.setItemMeta(meta);
        pane_white = item;
    }
    public static void setPane_black() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");

        item.setItemMeta(meta);
        pane_black = item;
    }

    public static void setAsPlayerStats(ItemStack item, OfflinePlayer player) {
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§8>> §6" + player.getName() + "'s Statistics §8<<");
        List<String> lore = new ArrayList<>();
        for (Statistic stats : Statistic.class.getEnumConstants()) {
            try {
                lore.add("§7" + stats.name().toLowerCase() + ": §f" + player.getStatistic(stats));
            } catch (IllegalArgumentException exception) {
                // empty
            }
        }
        meta.setLore(lore);
        meta.addEnchant(Enchantment.KNOCKBACK,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
    }

    private static void setPane_brown() {
        ItemStack item = new ItemStack(Material.BROWN_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");

        item.setItemMeta(meta);
        pane_brown = item;
    }

    private static void setNext() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Next Page");

        item.setItemMeta(meta);
        next = item;
    }

    private static void setPrevious() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Previous Page");

        item.setItemMeta(meta);
        previous = item;
    }

    private static void setCompass() {
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§bTo Current Page");

        item.setItemMeta(meta);
        compass = item;
    }
}
