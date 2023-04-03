package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.Money;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.ddns.twicusstumble.twicusseconomy.data.Database;

import java.text.DecimalFormat;

public class ShowAllAccounts implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Account.getAllAccounts().forEach(s -> sender.sendMessage(s + ": " + Money.formatToDecimal(Account.getMoney(s))));
            return true;
        }
        return false;
    }
}

