package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.system.Account;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DeleteAccount {
    public static String ERROR_MESSAGE = ChatColor.RED + "usage: /te account delete <account name> <password>" + ChatColor.RESET;

    public static boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
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

        sender.sendMessage(ERROR_MESSAGE);
        return false;
    }
}
