package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.TwicussEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class CommandHandler implements CommandExecutor {
    private static CommandHandler instance;
    private final TwicussEconomy plugin;

    private CommandHandler(TwicussEconomy plugin) {
        this.plugin = plugin;
    }

    public static CommandHandler getInstance(TwicussEconomy plugin) {
        if (instance == null) {
            instance = new CommandHandler(plugin);
        }
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        if (args[0].equalsIgnoreCase("account")) {
            if (args.length < 2) {
                return false;
            }

            if (args[1].equalsIgnoreCase("create")) {
                return CreateAccount.runCommand(sender, command, label, Arrays.copyOfRange(args, 2, args.length));
            } else if (args[1].equalsIgnoreCase("delete")) {
                return DeleteAccount.runCommand(sender, command, label, Arrays.copyOfRange(args, 2, args.length));
            } else if (args[1].equalsIgnoreCase("reissue")) {
                return Reissue.runCommand(sender, command, label, Arrays.copyOfRange(args, 2, args.length));
            } else if (args[1].equalsIgnoreCase("showall")) {
                return ShowAllAccounts.runCommand(sender, command, label, Arrays.copyOfRange(args, 2, args.length));
            }
        } else if (args[0].equalsIgnoreCase("password")) {
            if (args.length < 2) {
                return false;
            }

            if (args[1].equalsIgnoreCase("change")) {
                return ChangePassword.runCommand(sender, command, label, Arrays.copyOfRange(args, 2, args.length));
            }
        } else if (args[0].equalsIgnoreCase("deposit")) {
            return Deposit.runCommand(plugin, sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else if (args[0].equalsIgnoreCase("withdraw")) {
            return Withdraw.runCommand(plugin, sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else if (args[0].equalsIgnoreCase("transfer")) {
            return Transfer.runCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        } else if (args[0].equalsIgnoreCase("give")) {
            if (args.length < 2) {
                return false;
            }

            if (args[1].equalsIgnoreCase("cash")) {
                return GiveCash.runCommand(plugin, sender, command, label, Arrays.copyOfRange(args, 2, args.length));
            } else if (args[1].equalsIgnoreCase("money")) {
                return GiveMoney.runCommand(sender, command, label, Arrays.copyOfRange(args, 2, args.length));
            }
        } else if (args[0].equalsIgnoreCase("shop")) {
            if (args.length < 2) {
                return false;
            }

            if (args[1].equalsIgnoreCase("create")) {
                return CreateShop.runCommand(sender, command, label, Arrays.copyOfRange(args, 2, args.length));
            }
        }

        return false;
    }
}
