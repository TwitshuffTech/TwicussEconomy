package net.ddns.twicusstumble.twicusseconomy.data;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import net.ddns.twicusstumble.twicusseconomy.TwicussEconomy;

import java.util.LinkedHashMap;

public class Config {
    private static Config instance;
    private final TwicussEconomy plugin;
    private FileConfiguration config;

    private LinkedHashMap<Integer, String> moneyList;

    private Config(TwicussEconomy plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
        moneyList = getMoneyList();
    }

    public static Config getInstance(TwicussEconomy plugin) {
        if (instance == null) {
            instance = new Config(plugin);
        }
        return instance;
    }

    public LinkedHashMap<Integer, String> getMoneyList() {
        if (moneyList == null) {
            LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
            ConfigurationSection money = config.getConfigurationSection("money");
            money.getKeys(false).forEach(key -> map.put(Integer.parseInt(key), money.getString(key)));

            return map;
        }
        return moneyList;
    }
}
