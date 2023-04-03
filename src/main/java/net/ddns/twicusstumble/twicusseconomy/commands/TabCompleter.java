package net.ddns.twicusstumble.twicusseconomy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    private static final String[] COMMANDS = { "account", "password", "deposit", "withdraw", "transfer", "give", "shop" };
    private static final String[] ACCOUNT_PARAMS = { "create", "delete", "reissue", "showall" };
    private static final String[] PASSWORD_PARAMS = { "change" };
    private static final String[] GIVE_PARAMS = { "cash", "money" };
    private static final String[] SHOP_PARAMS = { "create" };

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList(COMMANDS));
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("account")) {
                completions.addAll(Arrays.asList(ACCOUNT_PARAMS));
            } else if (args[0].equalsIgnoreCase("password")) {
                completions.addAll(Arrays.asList(PASSWORD_PARAMS));
            } else if (args[0].equalsIgnoreCase("give")) {
                completions.addAll(Arrays.asList(GIVE_PARAMS));
            } else if (args[0].equalsIgnoreCase("shop")) {
                completions.addAll(Arrays.asList(SHOP_PARAMS));
            }
        }

        return completions;
    }
}
