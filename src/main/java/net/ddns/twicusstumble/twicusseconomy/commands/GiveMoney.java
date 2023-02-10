package net.ddns.twicusstumble.twicusseconomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.Money;

import java.text.DecimalFormat;

public class GiveMoney implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("givemoney")) {
            if (args.length == 2 && Money.isPositiveNumeric(args[1])) {
                String accountName = args[0];
                double amount = Double.parseDouble(args[1]);
                Account account = new Account(accountName);

                if (accountName.equalsIgnoreCase("@all")) {
                    for (String name : Account.getAllAccounts()) {
                        Account eachAccount = new Account(name);
                        eachAccount.addMoney(amount);
                        sender.sendMessage(name + " のアカウントに " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0.0").format(amount) + ChatColor.RESET + " 加えました");
                    }
                } else if (account.exists()) {
                    account.addMoney(amount);
                    sender.sendMessage("アカウント " + accountName + " に " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0.0").format(amount) + ChatColor.RESET + " 加えました");
                } else {
                    sender.sendMessage("存在しないアカウント名です");
                }

                return true;
            }
        }
        return false;
    }
}
