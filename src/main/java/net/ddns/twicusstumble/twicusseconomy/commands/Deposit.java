package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.ddns.twicusstumble.twicusseconomy.TwicussEconomy;
import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.ATM.ATM;
import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;
import net.ddns.twicusstumble.twicusseconomy.system.InventoryCash;
import net.ddns.twicusstumble.twicusseconomy.system.Money;

public class Deposit {
    public static String ERROR_MESSAGE = ChatColor.RED + "usage: /te deposit <amount> <password>" + ChatColor.RESET;

    public static boolean runCommand(TwicussEconomy plugin, CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && args.length == 2 && Utils.isPositiveNumeric(args[0])) {
            int amount = Money.normalize(Integer.parseInt(args[0]));
            String password = args[1];

            InventoryCash inventoryCash = new InventoryCash(plugin, (Player) sender);
            int money = inventoryCash.getTotalCash();

            if (!ATM.canOperate((Player) sender, ((Player) sender).getInventory().getItemInMainHand(), password)) {
                return true;
            }
            if (amount > money) {
                sender.sendMessage("現金が不足しています");
                sender.sendMessage("所持金は " + Money.formatToInteger(money, ChatColor.GOLD) + " です");
                return true;
            }

            Bankbook bankbook = new Bankbook(((Player) sender).getInventory().getItemInMainHand());

            inventoryCash.removeAllCash();
            inventoryCash.giveCash(money - amount);

            Account account = bankbook.getAccount();
            account.addMoney(amount);

            bankbook.updateText();

            sender.sendMessage(Money.formatToInteger(money, ChatColor.GOLD) + " 預け入れました");
            sender.sendMessage("残高は " + Money.formatToInteger(account.getMoney(), ChatColor.GOLD) + " です");
            return true;
        }

        sender.sendMessage(ERROR_MESSAGE);
        return false;
    }
}
