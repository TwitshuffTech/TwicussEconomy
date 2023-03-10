package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.system.Account;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeleteAccount implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("deleteaccount")) {
            if (args.length == 2) {
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

                Account.delete(account.getName());
                sender.sendMessage("アカウント " + account.getName() + " を削除しました。");
                return true;
            }
        }
        return false;
    }
}
