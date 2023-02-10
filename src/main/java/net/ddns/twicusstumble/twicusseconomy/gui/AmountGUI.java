package net.ddns.twicusstumble.twicusseconomy.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.text.DecimalFormat;

public class AmountGUI {
    private final Inventory inventory;

    public AmountGUI(String amount) {
        if (amount.equals("")) {
            amount = "0";
        }
        inventory = Bukkit.createInventory(null, 36, "Amount: " + new DecimalFormat("###,##0").format(Integer.parseInt(amount)));
        initializeItems();
    }

    public void initializeItems() {
        inventory.setItem(3, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 1, "1", (short) 7));
        inventory.setItem(4, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 2, "2", (short) 7));
        inventory.setItem(5, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 3, "3", (short) 7));
        inventory.setItem(12, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 4, "4", (short) 7));
        inventory.setItem(13, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 5, "5", (short) 7));
        inventory.setItem(14, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 6, "6", (short) 7));
        inventory.setItem(21, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 7, "7", (short) 7));
        inventory.setItem(22, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 8, "8", (short) 7));
        inventory.setItem(23, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 9, "9", (short) 7));
        inventory.setItem(31, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 10, "0", (short) 7));

        inventory.setItem(30, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 1, "Delete", (short) 14));
        inventory.setItem(32, new GUIItems().createGlass(Material.STAINED_GLASS_PANE, 1, "Enter", (short) 3));
    }

    public Inventory getGUI() {
        return inventory;
    }
}
