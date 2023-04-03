package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.system.Account;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChangePassword implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 3) {
            String accountName = args[0];
            String oldPassword = args[1];
            String newPassword = args[2];
            Account account = new Account(accountName);

            if (!account.exists()) {
                sender.sendMessage("存在しないアカウント名です");
                return true;
            }
            if (!oldPassword.equals(account.getPassword())) {
                sender.sendMessage("パスワードが異なります");
                return true;
            }
            if (!Account.checkPasswordStyle(newPassword)) {
                sender.sendMessage("新しいパスワードは4桁の数字で入力してください");
                return true;
            }

            account.setPassword(newPassword);
            sender.sendMessage("新しいパスワードを設定しました");

            return true;
        }
        return false;
    }

    public static boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 3) {
            String accountName = args[0];
            String oldPassword = args[1];
            String newPassword = args[2];
            Account account = new Account(accountName);

            if (!account.exists()) {
                sender.sendMessage("存在しないアカウント名です");
                return true;
            }
            if (!oldPassword.equals(account.getPassword())) {
                sender.sendMessage("パスワードが異なります");
                return true;
            }
            if (!Account.checkPasswordStyle(newPassword)) {
                sender.sendMessage("新しいパスワードは4桁の数字で入力してください");
                return true;
            }

            account.setPassword(newPassword);
            sender.sendMessage("新しいパスワードを設定しました");

            return true;
        }
        return false;
    }
}