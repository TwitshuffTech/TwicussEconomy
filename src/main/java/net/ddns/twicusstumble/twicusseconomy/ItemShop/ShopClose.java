package net.ddns.twicusstumble.twicusseconomy.ItemShop;

import net.ddns.twicusstumble.twicusseconomy.util.UUIDList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

public class ShopClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        UUIDList.OPEN_SHOP_AS_OWNER.remove(uuid);
        UUIDList.OPEN_SHOP_AS_CUSTOMER.remove(uuid);
    }
}
