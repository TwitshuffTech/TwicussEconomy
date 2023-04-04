package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.system.Money;
import net.ddns.twicusstumble.twicusseconomy.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.ddns.twicusstumble.twicusseconomy.ATM.ATM;
import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;

public class Transfer {
    public static String ERROR_MESSAGE = ChatColor.RED + "usage: /te transfer <destination> <amount> <password>" + ChatColor.RESET;

    public static boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && args.length == 3 && Utils.isPositiveNumeric(args[1])) {
            String targetAccountName = args[0];
            double amount = Double.parseDouble(args[1]);
            String password = args[2];

            if (!ATM.canOperate((Player) sender, ((Player) sender).getInventory().getItemInMainHand(), password)) {
                return true;
            }

            Bankbook bankbook = new Bankbook(((Player) sender).getInventory().getItemInMainHand());

            Account account = bankbook.getAccount();
            Account targetAccount = new Account(targetAccountName);

            if (amount > account.getMoney()) {
                sender.sendMessage("残高が不足しています");
                sender.sendMessage("残高は " + Money.formatToDecimal(account.getMoney(), ChatColor.GOLD) + " です");
                return true;
            }

            if (targetAccount.exists()) {
                account.addMoney(-amount);
                targetAccount.addMoney(amount);

                bankbook.updateText();

                sender.sendMessage("アカウント " + targetAccount.getName() + " に " + Money.formatToDecimal(amount, ChatColor.GOLD) + " 送金しました");
                sender.sendMessage("残高は " + Money.formatToDecimal(account.getMoney(), ChatColor.GOLD) + " です");
            } else {
                sender.sendMessage("存在しないアカウント名です");
            }

            return true;
        }

        sender.sendMessage(ERROR_MESSAGE);
        return false;
    }
}
