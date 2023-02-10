package net.ddns.twicusstumble.twicusseconomy.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Set;

public class GUIClose implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Set<String> UserTags = event.getPlayer().getScoreboardTags();
        if (UserTags.contains("GUIWithdraw")) {
            event.getPlayer().removeScoreboardTag("GUIWithdraw");
        } else if (UserTags.contains("GUIDeposit")) {
            event.getPlayer().removeScoreboardTag("GUIDeposit");
        }
    }

    public static void close(Player player) {
        Set<String> UserTags = player.getScoreboardTags();
        if (UserTags.contains("GUIWithdraw")) {
            player.getPlayer().removeScoreboardTag("GUIWithdraw");
        } else if (UserTags.contains("GUIDeposit")) {
            player.getPlayer().removeScoreboardTag("GUIDeposit");
        }
        player.closeInventory();
    }
}
