package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.ATM.ATM;
import net.ddns.twicusstumble.twicusseconomy.util.Utils;
import net.ddns.twicusstumble.twicusseconomy.system.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.ddns.twicusstumble.twicusseconomy.TwicussEconomy;

public class Withdraw {
    public static boolean runCommand(TwicussEconomy plugin, CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && args.length == 2 && Utils.isPositiveNumeric(args[0])) {
            int amount = Money.normalize(Integer.parseInt(args[0]));
            String password = args[1];

            if (!ATM.canOperate((Player) sender, ((Player) sender).getInventory().getItemInMainHand(), password)) {
                return true;
            }

            Bankbook bankbook = new Bankbook(((Player) sender).getInventory().getItemInMainHand());

            Account account = bankbook.getAccount();
            InventoryCash inventoryCash = new InventoryCash(plugin, (Player) sender);
            if (amount > account.getMoney()) {
                sender.sendMessage("残高が不足しています");
                sender.sendMessage("残高は " + Money.formatToDecimal(account.getMoney(), ChatColor.GOLD) + " です");
                return true;
            }

            inventoryCash.giveCash(amount);
            account.addMoney(-amount);

            bankbook.updateText();

            sender.sendMessage(Money.formatToInteger(amount, ChatColor.GOLD) + " 引き下ろしました");
            sender.sendMessage("残高は " + Money.formatToDecimal(account.getMoney(), ChatColor.GOLD) + " です");
            return true;
        }
        return false;
    }
}
