package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.system.InventoryCash;
import net.ddns.twicusstumble.twicusseconomy.system.Money;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.ddns.twicusstumble.twicusseconomy.TwicussEconomy;

import java.text.DecimalFormat;

public class GiveCash implements CommandExecutor {
    private final TwicussEconomy plugin;

    public GiveCash(TwicussEconomy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("givecash")) {
            if (args.length == 2 && Money.isPositiveNumeric(args[1])) {
                int amount = Money.normalize(Integer.parseInt(args[1]));
                Player targetPlayer = Bukkit.getServer().getPlayer(args[0]);

                if (targetPlayer == null) {
                    sender.sendMessage("存在しないプレイヤー名です");
                    return true;
                }

                InventoryCash inventoryCash = new InventoryCash(plugin, targetPlayer);
                inventoryCash.giveCash(amount);
                sender.sendMessage(args[0] + " に " + ChatColor.GOLD + "￥" + new DecimalFormat("###,##0").format(amount) + ChatColor.RESET + " 加えました");

                return true;
            }
        }
        return false;
    }
}
