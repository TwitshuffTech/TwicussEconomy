package net.ddns.twicusstumble.twicusseconomy.gui;

import net.ddns.twicusstumble.twicusseconomy.util.UUIDList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Set;
import java.util.UUID;

public class GUIClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        UUIDList.GUI_WITHDRAW.remove(uuid);
        UUIDList.GUI_DEPOSIT.remove(uuid);
    }

    public static void close(Player player) {
        UUID uuid = player.getUniqueId();
        UUIDList.GUI_WITHDRAW.remove(uuid);
        UUIDList.GUI_DEPOSIT.remove(uuid);
        player.closeInventory();
    }
}
