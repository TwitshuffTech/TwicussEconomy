package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.util.Utils;
import net.ddns.twicusstumble.twicusseconomy.system.InventoryCash;
import net.ddns.twicusstumble.twicusseconomy.system.Money;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.ddns.twicusstumble.twicusseconomy.TwicussEconomy;

public class GiveCash {
    public static String ERROR_MESSAGE = ChatColor.RED + "usage: /te give cash <player> <amount>" + ChatColor.RESET;

    public static boolean runCommand(TwicussEconomy plugin, CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2 && Utils.isPositiveNumeric(args[1])) {
            int amount = Money.normalize(Integer.parseInt(args[1]));
            Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);

            if (targetPlayer == null) {
                sender.sendMessage("存在しないプレイヤー名です");
                return true;
            }

            InventoryCash inventoryCash = new InventoryCash(plugin, targetPlayer);
            inventoryCash.giveCash(amount);
            sender.sendMessage(args[0] + " に " + Money.formatToInteger(amount, ChatColor.GOLD) + " 加えました");

            return true;
        }

        sender.sendMessage(ERROR_MESSAGE);
        return false;
    }
}
