package net.ddns.twicusstumble.twicusseconomy.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    private Utils() {

    }

    public static Block getBlockSignIsPlaced(Sign sign) {
        org.bukkit.material.Sign signData = (org.bukkit.material.Sign) sign.getData();
        Block block = sign.getBlock().getRelative(signData.getAttachedFace());

        return block;
    }

    public static boolean ifSignIsBlank(Sign sign) {
        String[] lines = sign.getLines();
        for (String line : lines) {
            if (!line.equals("")) {
                return false;
            }
        }
        return true;
    }

    public static List<ItemStack> getItemStacksInChest(Chest chest) {
        Inventory inventory = chest.getInventory();
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i) == null ? null : inventory.getItem(i).clone();
            if (itemStack != null) {
                itemStack.setAmount(1);
                if (!itemStacks.contains(itemStack)) {
                    itemStacks.add(itemStack);
                }
            }
        }
        return itemStacks;
    }

    public static int getNumItemsInChest(Chest chest, ItemStack item) {
        if (item == null) {
            return 0;
        }
        Inventory inventory = chest.getInventory();
        int count = 0;
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) != null && inventory.getItem(i).isSimilar(item)) {
                count += inventory.getItem(i).getAmount();
            }
        }
        return count;
    }

    public static boolean isPositiveNumeric(String str) {
        return str.matches("\\d+(?:\\.\\d+)?");
    }

    public static boolean isPositiveInteger(String str) {
        return str.matches("[0-9]+");
    }
}
