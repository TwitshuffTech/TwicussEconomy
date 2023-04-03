package net.ddns.twicusstumble.twicusseconomy.ItemShop;

import net.ddns.twicusstumble.twicusseconomy.TwicussEconomy;
import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;
import net.ddns.twicusstumble.twicusseconomy.system.InventoryCash;
import net.ddns.twicusstumble.twicusseconomy.system.Money;
import net.ddns.twicusstumble.twicusseconomy.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ShopClick implements Listener {
    private TwicussEconomy plugin;

    public ShopClick(TwicussEconomy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (!ShopSign.isShopSign(block)) {
            if (ItemShop.isShopChest(block)) {
                ItemShop itemShop = new ItemShop((Chest) block.getState());
                if (itemShop.getShopSign() != null && Utils.getItemStacksInChest(itemShop.getChest()).size() == 1) {
                    itemShop.update();
                }
            }
            return;
        }

        Sign sign = (Sign) block.getState();
        ItemShop itemShop = new ItemShop((Chest) Utils.getBlockSignIsPlaced(sign).getState());

        Player player = event.getPlayer();

        if (Utils.getItemStacksInChest(itemShop.getChest()).size() != 1) {
            player.sendMessage("このショップは現在利用不可能です");
            return;
        }

        ItemStack item = Utils.getItemStacksInChest(itemShop.getChest()).get(0);
        int numUnit = itemShop.getNumUnit();
        if (Utils.getNumItemsInChest(itemShop.getChest(), item) < numUnit) {
            player.sendMessage("在庫が不足しています");
            return;
        }

        InventoryCash playerCash = new InventoryCash(plugin, player);
        int totalCash = playerCash.getTotalCash();
        int price = itemShop.getPrice();
        if (totalCash < price) {
            player.sendMessage("現金が不足しています");
            player.sendMessage("所持金は " + Money.formatToInteger(totalCash, ChatColor.GOLD) + " です");
            return;
        }

        itemShop.buyItem(player, playerCash);
    }
}
