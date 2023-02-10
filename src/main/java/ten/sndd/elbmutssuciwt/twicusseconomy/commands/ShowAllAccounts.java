package ten.sndd.elbmutssuciwt.twicusseconomy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ten.sndd.elbmutssuciwt.twicusseconomy.data.Database;

import java.text.DecimalFormat;

public class ShowAllAccounts implements CommandExecutor {
    private final Database database = Database.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("showallaccounts")) {
            if (args.length == 0) {
                database.getAllAccounts().forEach(s -> sender.sendMessage(s + ": ￥" + new DecimalFormat("###,##0.0").format(database.getMoney(s))));
                return true;
            }
            return false;
        }
        return false;
    }
}

