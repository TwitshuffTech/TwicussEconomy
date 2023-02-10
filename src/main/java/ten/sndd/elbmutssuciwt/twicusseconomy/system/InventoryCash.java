package ten.sndd.elbmutssuciwt.twicusseconomy.system;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ten.sndd.elbmutssuciwt.twicusseconomy.TwicussEconomy;
import ten.sndd.elbmutssuciwt.twicusseconomy.data.Config;

import java.util.Map;

public class InventoryCash {
    private final TwicussEconomy plugin;
    private final Config config;
    private final Player player;

    public InventoryCash(TwicussEconomy plugin, Player player) {
        this.plugin = plugin;
        this.config = Config.getInstance(plugin);
        this.player = player;
    }

    public int getTotalCash() {
        int cash = 0;
        ItemStack[] items = player.getInventory().getContents();

        for (ItemStack item : items) {
            for (int value : config.getMoneyList().keySet()) {
                if (item != null && item.isSimilar(Money.getMoneyItemStack(plugin, value, 1))) {
                    cash += value * item.getAmount();
                }
            }
        }
        return cash;
    }

    public void giveCash(int amount) {
        Map<Integer, Integer> moneyMap = Money.calculateBills(plugin, amount);

        for (Map.Entry<Integer, Integer> entry : moneyMap.entrySet()) {
            if (entry.getValue() > 0) {
                Map<Integer, ItemStack> map = player.getInventory().addItem(Money.getMoneyItemStack(plugin, entry.getKey(), entry.getValue()));
                for (ItemStack item : map.values()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), item);
                }
            }
        }
    }

    public void removeAllCash() {
        ItemStack[] items = player.getInventory().getContents();

        for (ItemStack item : items) {
            for (int value : config.getMoneyList().keySet()) {
                if (item != null && item.isSimilar(Money.getMoneyItemStack(plugin, value, 1))) {
                    player.getInventory().removeItem(item);
                }
            }
        }
    }
}
