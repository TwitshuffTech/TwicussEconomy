package net.ddns.twicusstumble.twicusseconomy.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import net.ddns.twicusstumble.twicusseconomy.system.ATM;
import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;

import java.util.Set;

public class GUIClick implements Listener {
    public static final String WITHDRAW_TAG = "GUIWithdraw";
    public static final String DEPOSIT_TAG = "GUIDeposit";

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        if (title == null || (!title.startsWith("Menu") && !title.startsWith("Password: ") && !title.startsWith("Amount: "))) {
            return;
        }

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        String buttonName = clickedItem.getItemMeta().getDisplayName();
        if (buttonName == null) {
            return;
        }

        Set<String> userTags = player.getScoreboardTags();
        String flag = null;
        if (userTags.contains(WITHDRAW_TAG)) {
            flag = WITHDRAW_TAG;
        } else if (userTags.contains(DEPOSIT_TAG)) {
            flag = DEPOSIT_TAG;
        }

        if (title.equals("Menu")) {
            switch (buttonName) {
                case "出金":
                    player.openInventory(new PasswordGUI("").getGUI());
                    player.addScoreboardTag(WITHDRAW_TAG);
                    break;
                case "入金":
                    player.openInventory(new PasswordGUI("").getGUI());
                    player.addScoreboardTag(DEPOSIT_TAG);
                    break;
            }
        } else if (title.startsWith("Password: ")) {
            String password = title.substring(10);
            if (buttonName.equals("Delete")) {
                if (password.length() < 1) {
                    password += "a";
                }
                player.openInventory(new PasswordGUI(password.substring(0, password.length() - 1)).getGUI());
            } else if (password.length() == 3) {
                if (ATM.canOperateATM(player, new Bankbook(player.getInventory().getItemInMainHand()), password + buttonName)) {
                    player.openInventory(new AmountGUI("").getGUI());
                } else {
                    GUIClose.close(player);
                }
            } else {
                player.openInventory(new PasswordGUI(password + buttonName).getGUI());
            }

            if (flag.equals(WITHDRAW_TAG)) {
                player.addScoreboardTag(WITHDRAW_TAG);
            } else if (flag.equals(DEPOSIT_TAG)) {
                player.addScoreboardTag(DEPOSIT_TAG);
            }
        } else if (title.startsWith("Amount: ")) {
            String amount = String.join("", title.substring(8).split(","));
            if (buttonName.equals("Delete")) {
                if (amount.length() < 1) {
                    amount += "0";
                }
                player.openInventory(new AmountGUI(amount.substring(0, amount.length() - 1)).getGUI());
            } else if (buttonName.equals("Enter")) {
                if (userTags.contains(WITHDRAW_TAG)) {
                    player.performCommand("withdraw " + amount + " MASTERKEY");
                    GUIClose.close(player);
                    return;
                } else if (userTags.contains(DEPOSIT_TAG)) {
                    player.performCommand("deposit " + amount + " MASTERKEY");
                    GUIClose.close(player);
                    return;
                }
            } else {
                player.openInventory(new AmountGUI(amount + buttonName).getGUI());
            }

            if (flag.equals(WITHDRAW_TAG)) {
                player.addScoreboardTag(WITHDRAW_TAG);
            } else if (flag.equals(DEPOSIT_TAG)) {
                player.addScoreboardTag(DEPOSIT_TAG);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        Set<String> UserTags = event.getWhoClicked().getScoreboardTags();
        if (UserTags.contains("GUIOpen")) {
            event.setCancelled(true);
        }
    }
}
