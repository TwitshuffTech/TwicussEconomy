package net.ddns.twicusstumble.twicusseconomy;

import net.ddns.twicusstumble.twicusseconomy.commands.*;
import org.bukkit.plugin.java.JavaPlugin;
import net.ddns.twicusstumble.twicusseconomy.gui.GUIClick;
import net.ddns.twicusstumble.twicusseconomy.gui.GUIClose;
import net.ddns.twicusstumble.twicusseconomy.gui.GUIOpen;

public final class TwicussEconomy extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("createaccount").setExecutor(new CreateAccount());
        getCommand("changepassword").setExecutor(new ChangePassword());
        getCommand("deleteaccount").setExecutor(new DeleteAccount());
        getCommand("reissue").setExecutor(new Reissue());
        getCommand("deposit").setExecutor(new Deposit(this));
        getCommand("withdraw").setExecutor(new Withdraw(this));
        getCommand("transfer").setExecutor(new Transfer());
        getCommand("givecash").setExecutor(new GiveCash(this));
        getCommand("givemoney").setExecutor(new GiveMoney());
        getCommand("showallaccounts").setExecutor(new ShowAllAccounts());

        getServer().getPluginManager().registerEvents(new GUIOpen(), this);
        getServer().getPluginManager().registerEvents(new GUIClick(), this);
        getServer().getPluginManager().registerEvents(new GUIClose(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
