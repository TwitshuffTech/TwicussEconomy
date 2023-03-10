package net.ddns.twicusstumble.twicusseconomy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;

public class Reissue implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("reissue")) {
            if (sender instanceof Player && args.length == 2) {
                String accountName = args[0];
                String password = args[1];
                Account account = new Account(accountName);

                if (!account.exists()) {
                    sender.sendMessage("存在しないアカウント名です");
                    return true;
                }
                if (!password.equals(account.getPassword())) {
                    sender.sendMessage("パスワードが異なります");
                    return true;
                }

                Bankbook bankbook = Bankbook.create(account);
                bankbook.updateText();

                ((Player) sender).getInventory().addItem(bankbook.getItemStack());
                sender.sendMessage("アカウント " + accountName + " の通帳を再発行しました");
                return true;
            }
        }
        return false;
    }
}
