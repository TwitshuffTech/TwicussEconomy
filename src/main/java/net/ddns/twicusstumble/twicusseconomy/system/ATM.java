package net.ddns.twicusstumble.twicusseconomy.system;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class ATM {
    public static boolean canOperateATM(Player player, Bankbook bankbook, String password) {
        if (!isPlayerLookingAtATM(player)) {
            player.sendMessage("近くにATMが存在しません");
            return false;
        }
        if (!bankbook.isBankbook()) {
            player.sendMessage("通帳を手に持ってください");
            return false;
        }

        if (password.equals("MASTERKEY")) {
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
