package net.ddns.twicusstumble.twicusseconomy.ItemShop;

import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;
import net.ddns.twicusstumble.twicusseconomy.util.UUIDList;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ProtectShop implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (ItemShop.isShopChest(block)) {
            ItemShop itemShop = new ItemShop((Chest) block.getState());
            ShopSign shopSign = itemShop.getShopSign();
            ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();

            if (shopSign == null) {
                return;
            } else if (Bankbook.isBankbook(itemStack)) {
                Bankbook bankbook = new Bankbook(itemStack);
                if (bankbook.getAccount().getName().equals(itemShop.getOwnerName())) {
                    return;
                }
            }

            event.setCancelled(true);
            event.getPlayer().sendMessage("保護されたチェストです");

        } else if (ShopSign.isShopSign(block)) {
            ShopSign shopSign = new ShopSign((Sign) block.getState());
            ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();

            if (Bankbook.isBankbook(itemStack)) {
                Bankbook bankbook = new Bankbook(itemStack);
                if (bankbook.getAccount().getName().equals(shopSign.getOwnerName())) {
                    return;
                }
            }

            event.setCancelled(true);
            event.getPlayer().sendMessage("保護された看板です");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        UUID uuid = event.getWhoClicked().getUniqueId();
        if (UUIDList.OPEN_SHOP_AS_CUSTOMER.contains(uuid)) {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().sendMessage("登録した通帳を手にもってください");
        } else if (UUIDList.OPEN_SHOP_AS_OWNER.contains(uuid)) {

        }
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        if (event.getSource().getTitle().equals(ItemShop.SHOP_NAME)) {
            event.setCancelled(true);
        }
    }
}
