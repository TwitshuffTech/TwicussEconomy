package net.ddns.twicusstumble.twicusseconomy.system;

import net.ddns.twicusstumble.twicusseconomy.data.Config;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.ddns.twicusstumble.twicusseconomy.TwicussEconomy;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Money {
    private Money() {}

    public static ItemStack getMoneyItemStack(TwicussEconomy plugin, int value, int amount) {
        String type = Config.getInstance(plugin).getMoneyList().get(value);
        ItemStack money = null;
        if (type.equalsIgnoreCase("bill")) {
            money = new ItemStack(Material.PAPER, amount);
            ItemMeta meta = money.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "" + new DecimalFormat("###,##0").format(value) + "円札");
            money.setItemMeta(meta);
        } else if (type.equalsIgnoreCase("coin")) {
            money = new ItemStack(Material.GOLD_NUGGET, amount);
            ItemMeta meta = money.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "" + new DecimalFormat("###,##0").format(value) + "円玉");
            money.setItemMeta(meta);
        } else {
            return null;
        }
        money.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 0);

        return money;
    }

    public static Map<Integer, Integer> calculateBills(TwicussEconomy plugin, int amount) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int value : Config.getInstance(plugin).getMoneyList().keySet()) {
            map.put(value, amount / value);
            amount = amount % value;
        }
        return map;
    }

    public static int normalize(double amount) {
        return ((int)amount / 100) * 100;
    }

    public static String formatToInteger(double value) {
        return "￥" + new DecimalFormat("###,##0").format(value);
    }

    public static String formatToInteger(double value, ChatColor color) {
        return color + "￥" + new DecimalFormat("###,##0").format(value) + ChatColor.RESET;
    }

    public static String formatToDecimal(double value) {
        return "￥" + new DecimalFormat("###,##0.0").format(value);
    }

    public static String formatToDecimal(double value, ChatColor color) {
        return color + "￥" + new DecimalFormat("###,##0.0").format(value) + ChatColor.RESET;
    }
}
