package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.Money;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ShowAllAccounts {
    public static boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Account.getAllAccounts().forEach(s -> sender.sendMessage(s + ": " + Money.formatToDecimal(Account.getMoney(s))));
            return true;
        }
        return false;
    }
}

