package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateAccount {
    public static boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            String accountName = args[0];
            String password = args[1];

            if (!accountName.matches("^[0-9a-zA-Z]+$") || accountName.length() > 20) {
                sender.sendMessage("アカウント名は20文字以下の英数字で入力してください");
                return true;
            }

            if (!Account.checkPasswordStyle(password)) {
                sender.sendMessage("パスワードは4桁の数字で入力してください");
                return true;
            }

            Account account = Account.create(accountName, password);
            if (account == null) {
                sender.sendMessage("既に存在しているアカウント名です");
                return true;
            }

            Bankbook bankBook = Bankbook.create(account);
            ((Player) sender).getInventory().addItem(bankBook.getItemStack());

            sender.sendMessage("新しいアカウントを作成しました");
            return true;
        }
        return false;
    }
}
