package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.Money;

public class GiveMoney {
    public static boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2 && Utils.isPositiveNumeric(args[1])) {
            String accountName = args[0];
            double amount = Double.parseDouble(args[1]);
            Account account = new Account(accountName);

            if (accountName.equalsIgnoreCase("@all")) {
                for (String name : Account.getAllAccounts()) {
                    Account eachAccount = new Account(name);
                    eachAccount.addMoney(amount);
                    sender.sendMessage(name + " のアカウントに " + Money.formatToDecimal(amount, ChatColor.GOLD) + " 加えました");
                }
            } else if (account.exists()) {
                account.addMoney(amount);
                sender.sendMessage("アカウント " + accountName + " に " + Money.formatToDecimal(amount, ChatColor.GOLD) + " 加えました");
            } else {
                sender.sendMessage("存在しないアカウント名です");
            }

            return true;
        }
        return false;
    }
}
