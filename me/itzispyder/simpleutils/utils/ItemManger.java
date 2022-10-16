package me.itzispyder.simpleutils.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManger {

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
}
