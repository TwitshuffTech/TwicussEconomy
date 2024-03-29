package net.ddns.twicusstumble.twicusseconomy.ATM;

import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ATM {
    public static final String MASTER_KEY = "MASTER_KEY";

    public static boolean canOperate(Player player, ItemStack itemStack, String password) {
        if (!isPlayerLookingAtATM(player)) {
            player.sendMessage("近くにATMが存在しません");
            return false;
        }
        if (!Bankbook.isBankbook(itemStack)) {
            player.sendMessage("通帳を手に持ってください");
            return false;
        }

        Bankbook bankbook = new Bankbook(itemStack);

        if (password.equals(MASTER_KEY)) {
            return true;
        }

        if (!Account.checkPasswordStyle(password)) {
            player.sendMessage("パスワードは4桁の数字にしてください");
            return false;
        }
        Account account = bankbook.getAccount();
        if (!account.exists()) {
            player.sendMessage("アカウントが存在しません");
            return false;
        }
        if (!password.equals(bankbook.getAccount().getPassword())) {
            player.sendMessage("パスワードが異なります");
            return false;
        }
        return true;
    }

    public static boolean isPlayerLookingAtATM(Player player) {
        Block block = player.getTargetBlock(null, 1);

        if (block != null && block.getState() instanceof Sign) {
            String[] lines = ((Sign) block.getState()).getLines();
            if (lines[0].equals("") && lines[1].equals("ATM") && lines[2].equals("") && lines[3].equals("")) {
                return true;
            }
        }
        return false;
    }
}
