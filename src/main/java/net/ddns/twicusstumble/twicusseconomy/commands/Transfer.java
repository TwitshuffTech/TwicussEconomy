package net.ddns.twicusstumble.twicusseconomy.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.ddns.twicusstumble.twicusseconomy.system.ATM;
import net.ddns.twicusstumble.twicusseconomy.system.Account;
import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;
import net.ddns.twicusstumble.twicusseconomy.system.Money;

import java.text.DecimalFormat;

public class Transfer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("transfer")) {
            if (sender instanceof Player && args.length == 3 && Money.isPositiveNumeric(args[1])) {
                String targetAccountName = args[0];
                double amount = Double.parseDouble(args[1]);
                String password = args[2];
                Bankbook bankbook = new Bankbook(((Player) sender).getInventory().getItemInMainHand());

                if (!ATM.canOperateATM((Player) sender,bankbook, password)) {
                    return true;
                }

                Account account = bankbook.getAccount();
                Account targetAccount = new Account(targetAccountName);

                if (amount > account.getMoney()) {
                    sender.sendMessage("残高が不足しています");
                    sender.sendMessage("残高は " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0.0").format(account.getMoney()) + ChatColor.RESET + " です");
                    return true;
                }

                if (targetAccount.exists()) {
                    account.addMoney(-amount);
                    targetAccount.addMoney(amount);

                    bankbook.updateText();

                    sender.sendMessage(targetAccount.getName() + " に " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0.0").format(amount) + ChatColor.RESET + " 送金しました");
                    sender.sendMessage("残高は " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0.0").format(account.getMoney()) + ChatColor.RESET + " です");

                    Player targetPlayer = Bukkit.getServer().getPlayer(targetAccount.getName());
                    if (targetPlayer != null) {
                        targetPlayer.sendMessage(account.getName() + " から " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0.0").format(amount) + ChatColor.RESET + " 送金されました");
                        targetPlayer.sendMessage("残高は " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0.0").format(targetAccount.getMoney()) + ChatColor.RESET + " です");
                    }
                } else {
                    sender.sendMessage("存在しないプレイヤー名です");
                }

                return true;
            }
        }
        return false;
    }
}
