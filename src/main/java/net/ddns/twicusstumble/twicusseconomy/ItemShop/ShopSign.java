package net.ddns.twicusstumble.twicusseconomy.ItemShop;

import net.ddns.twicusstumble.twicusseconomy.system.Money;
import net.ddns.twicusstumble.twicusseconomy.util.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

public class ShopSign {
    private final Sign sign;

    public ShopSign(Sign sign) {
        this.sign = sign;
    }

    public Sign getSign() {
        return sign;
    }

    public String getItemName() {
        return sign.getLine(0);
    }

    public int getPrice() {
        String price = sign.getLine(1).substring(1, sign.getLine(1).indexOf(" per "));
        return Integer.parseInt(price.replace(",", ""));
    }

    public int getNumUnit() {
        return Integer.parseInt(sign.getLine(1).substring(sign.getLine(1).indexOf(" per ") + 5));
    }

    public String getOwnerName() {
        return sign.getLine(3);
    }

    public static boolean isShopSign(Block block) {
        if (block != null && block.getState() instanceof Sign) {
            Sign sign = (Sign) block.getState();
            String[] lines = sign.getLines();
            if (!(lines[1].contains("￥") && lines[1].contains("per"))) {
                return false;
            }
            if (!(lines[2].startsWith("Amount: "))) {
                return false;
            }
            return true;
        }
        return false;
    }
}
