package ten.sndd.elbmutssuciwt.twicusseconomy.system;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ten.sndd.elbmutssuciwt.twicusseconomy.TwicussEconomy;
import ten.sndd.elbmutssuciwt.twicusseconomy.data.Config;

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

    public static boolean isPositiveNumeric(String str) {
        return str.matches("\\d+(?:\\.\\d+)?");
    }

    public static int normalize(double amount) {
        return ((int)amount / 100) * 100;
    }
}
