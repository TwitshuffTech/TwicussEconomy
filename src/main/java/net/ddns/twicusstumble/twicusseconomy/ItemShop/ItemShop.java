package net.ddns.twicusstumble.twicusseconomy.ItemShop;

import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.InventoryCash;
import net.ddns.twicusstumble.twicusseconomy.util.Utils;
import net.ddns.twicusstumble.twicusseconomy.system.Money;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemShop {
    public static final String SHOP_NAME = "SHOP";

    private final Chest chest;
    private final ShopSign shopSign;

    public ItemShop(Chest chest) {
        this.chest = chest;
        shopSign = getShopSign();
    }

    public static boolean canCreate(Sign sign, Chest chest) {
        if (Utils.ifSignIsBlank(sign) && Utils.getItemStacksInChest(chest).size() == 1 && !isShopChest(chest.getBlock())) {
            return true;
        }
        return false;
    }

    public static void create(Account ownerAccount, Sign sign, Chest chest, int price, int numUnit) {
        ItemStack itemStack = Utils.getItemStacksInChest(chest).get(0);
        Material material = itemStack.getType();

        sign.setLine(0, material.name());
        sign.setLine(1, Money.formatToInteger(price) + " per " + numUnit);
        sign.setLine(2, "Amount: " + Utils.getNumItemsInChest(chest, itemStack));
        sign.setLine(3, ownerAccount.getName());
        sign.update();

        chest.setCustomName(SHOP_NAME);
        chest.update();
    }

    public Chest getChest() {
        return chest;
    }

    public ShopSign getShopSign() {
        if (shopSign == null) {
            for (BlockFace blockFace : BlockFace.values()) {
                if (ShopSign.isShopSign(chest.getBlock().getRelative(blockFace))) {
                    Sign sign = (Sign) chest.getBlock().getRelative(blockFace).getState();
                    org.bukkit.material.Sign signData = (org.bukkit.material.Sign) sign.getData();
                    if (sign.getBlock().getRelative(signData.getAttachedFace()).equals(chest.getBlock())) {
                        return new ShopSign(sign);
                    }
                }
            }
            return null;
        } else {
            return shopSign;
        }
    }

    public int getPrice() {
        if (shopSign == null) {
            return 0;
        } else {
            return shopSign.getPrice();
        }
    }

    public int getNumUnit() {
        if (shopSign == null) {
            return 0;
        } else {
            return shopSign.getNumUnit();
        }
    }

    public String getOwnerName() {
        if (shopSign == null) {
            return null;
        } else {
            return shopSign.getOwnerName();
        }
    }

    public void buyItem(Player buyer, InventoryCash buyerCash) {
        Account ownerAccount = new Account(getOwnerName());
        int price = getPrice();
        int totalCash = buyerCash.getTotalCash();

        ownerAccount.addMoney(price);
        buyerCash.removeAllCash();
        buyerCash.giveCash(totalCash - price);

        ItemStack item = Utils.getItemStacksInChest(chest).get(0).clone();
        item.setAmount(getNumUnit());

        Map<Integer, ItemStack> map = buyer.getInventory().addItem(item);
        for (ItemStack itemStack : map.values()) {
            buyer.getWorld().dropItemNaturally(buyer.getLocation(), itemStack);
        }

        chest.getInventory().removeItem(item);
        update();
    }

    public void update() {
        ItemStack itemStack = Utils.getItemStacksInChest(chest).size() == 0 ? null : Utils.getItemStacksInChest(chest).get(0);
        Sign sign = shopSign.getSign();

        sign.setLine(2, "Amount: " + Utils.getNumItemsInChest(chest, itemStack));
        sign.update();
    }

    public static boolean isShopChest(Block block) {
        if (block != null && block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            return chest.getCustomName() != null && chest.getCustomName().equals(SHOP_NAME);
        }
        return false;
    }
}
