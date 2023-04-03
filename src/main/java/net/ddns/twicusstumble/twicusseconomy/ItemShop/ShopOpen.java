package net.ddns.twicusstumble.twicusseconomy.ItemShop;

import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;
import net.ddns.twicusstumble.twicusseconomy.util.UUIDList;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ShopOpen implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && ItemShop.isShopChest(event.getClickedBlock())) {
            ItemShop itemShop = new ItemShop((Chest) event.getClickedBlock().getState());
            Player player = event.getPlayer();
            ItemStack itemStack = player.getInventory().getItemInMainHand();

            if (Bankbook.isBankbook(itemStack)) {
                Bankbook bankbook = new Bankbook(itemStack);
                if (bankbook.getAccount().getName().equals(itemShop.getOwnerName())) {
                    UUIDList.OPEN_SHOP_AS_OWNER.add(player.getUniqueId());
                    return;
                }
            }

            UUIDList.OPEN_SHOP_AS_CUSTOMER.add(player.getUniqueId());
            return;
        }
    }
}
