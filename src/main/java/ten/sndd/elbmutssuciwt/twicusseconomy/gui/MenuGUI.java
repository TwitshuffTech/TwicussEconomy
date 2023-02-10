package ten.sndd.elbmutssuciwt.twicusseconomy.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class MenuGUI implements Listener {
    private final Inventory inventory;

    public MenuGUI() {
        inventory = Bukkit.createInventory(null, 9, "Menu");
        initializeItems();
    }

    public void initializeItems() {
        inventory.setItem(3, new GUIItems().create(Material.GOLD_INGOT, 1, "出金", "withdraw money"));
        inventory.setItem(5, new GUIItems().create(Material.PAPER, 1, "入金", "deposit money"));
    }

    public Inventory getGUI() {
        return inventory;
    }
}
