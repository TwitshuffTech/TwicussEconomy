package net.ddns.twicusstumble.twicusseconomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.ddns.twicusstumble.twicusseconomy.TwicussEconomy;
import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.ATM;
import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;
import net.ddns.twicusstumble.twicusseconomy.system.InventoryCash;
import net.ddns.twicusstumble.twicusseconomy.system.Money;

import java.text.DecimalFormat;

public class Deposit implements CommandExecutor {
    private final TwicussEconomy plugin;

    public Deposit(TwicussEconomy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("deposit")) {
            if (sender instanceof Player && args.length == 2 && Money.isPositiveNumeric(args[0])) {
                int amount = Money.normalize(Integer.parseInt(args[0]));
                String password = args[1];

                InventoryCash inventoryCash = new InventoryCash(plugin, (Player) sender);
                int money = inventoryCash.getTotalCash();

                Bankbook bankbook = new Bankbook(((Player) sender).getInventory().getItemInMainHand());

                if (!ATM.canOperateATM((Player) sender, bankbook, password)) {
                    return true;
                }
                if (amount > money) {
                    sender.sendMessage("現金が不足しています");
                    sender.sendMessage("所持金は " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0").format(money) + ChatColor.RESET + " です");
                    return true;
                }

                inventoryCash.removeAllCash();
                inventoryCash.giveCash(money - amount);

                Account account = bankbook.getAccount();
                account.addMoney(amount);

                bankbook.updateText();

                sender.sendMessage(ChatColor.GOLD + "￥" + new DecimalFormat("###,##0").format(amount) + ChatColor.RESET + " 預け入れました");
                sender.sendMessage("残高は " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0.0").format(account.getMoney()) + ChatColor.RESET + " です");
                return true;
            }
        }
        return false;
    }
}
