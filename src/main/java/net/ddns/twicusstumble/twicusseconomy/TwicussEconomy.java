package net.ddns.twicusstumble.twicusseconomy;

import net.ddns.twicusstumble.twicusseconomy.ItemShop.ProtectShop;
import net.ddns.twicusstumble.twicusseconomy.ItemShop.ShopClick;
import net.ddns.twicusstumble.twicusseconomy.ItemShop.ShopClose;
import net.ddns.twicusstumble.twicusseconomy.ItemShop.ShopOpen;
import net.ddns.twicusstumble.twicusseconomy.commands.*;
import org.bukkit.plugin.java.JavaPlugin;
import net.ddns.twicusstumble.twicusseconomy.gui.GUIClick;
import net.ddns.twicusstumble.twicusseconomy.gui.GUIClose;
import net.ddns.twicusstumble.twicusseconomy.gui.GUIOpen;

public final class TwicussEconomy extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("te").setExecutor(CommandHandler.getInstance(this));
        getCommand("te").setTabCompleter(new TabCompleter());

        getServer().getPluginManager().registerEvents(new GUIOpen(), this);
        getServer().getPluginManager().registerEvents(new GUIClick(), this);
        getServer().getPluginManager().registerEvents(new GUIClose(), this);

        getServer().getPluginManager().registerEvents(new ShopOpen(), this);
        getServer().getPluginManager().registerEvents(new ShopClick(this), this);
        getServer().getPluginManager().registerEvents(new ShopClose(), this);
        getServer().getPluginManager().registerEvents(new ProtectShop(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
