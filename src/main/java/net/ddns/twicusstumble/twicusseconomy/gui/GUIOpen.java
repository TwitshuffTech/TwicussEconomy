package net.ddns.twicusstumble.twicusseconomy.gui;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import net.ddns.twicusstumble.twicusseconomy.system.ATM;
import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;

public class GUIOpen implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign && ATM.isPlayerLookingAtATM(event.getPlayer())) {
            Player player = event.getPlayer();
            if (ATM.canOperateATM(player, new Bankbook(player.getInventory().getItemInMainHand()), "MASTERKEY")) {
                player.openInventory(new MenuGUI().getGUI());
            }
        }
    }
}
