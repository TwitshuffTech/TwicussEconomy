package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.system.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.ddns.twicusstumble.twicusseconomy.TwicussEconomy;
import net.ddns.twicusstumble.twicusseconomy.data.Database;

import java.text.DecimalFormat;

public class Withdraw implements CommandExecutor {
    private final TwicussEconomy plugin;

    public Withdraw(TwicussEconomy plugin) {
        this.plugin = plugin;
    }

    private Database database = Database.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("withdraw")) {
            if (sender instanceof Player && args.length == 2 && Money.isPositiveNumeric(args[0])) {
                int amount = Money.normalize(Integer.parseInt(args[0]));
                String password = args[1];
                Bankbook bankbook = new Bankbook(((Player) sender).getInventory().getItemInMainHand());

                if (!ATM.canOperateATM((Player) sender, bankbook, password)) {
                    return true;
                }

                Account account = bankbook.getAccount();
                InventoryCash inventoryCash = new InventoryCash(plugin, (Player) sender);
                if (amount > account.getMoney()) {
                    sender.sendMessage("残高が不足しています");
                    sender.sendMessage("残高は " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0.0").format(account.getMoney()) + ChatColor.RESET + " です");
                    return true;
                }

                inventoryCash.giveCash(amount);
                account.addMoney(-amount);

                bankbook.updateText();

                sender.sendMessage(ChatColor.GOLD + "￥" + new DecimalFormat("###,##0").format(amount) + ChatColor.RESET + " 引き下ろしました");
                sender.sendMessage("残高は " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0.0").format(account.getMoney()) + ChatColor.RESET + " です");
                return true;
            }
        }
        return false;
    }
}
