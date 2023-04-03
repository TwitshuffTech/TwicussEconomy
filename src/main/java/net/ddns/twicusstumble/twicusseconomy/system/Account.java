package net.ddns.twicusstumble.twicusseconomy.system;

import net.ddns.twicusstumble.twicusseconomy.data.Database;

import java.util.List;

public class Account {
    private static final Database database = Database.getInstance();
    private final String name;

    public Account(String name) {
        this.name = name;
    }

    public static Account create(String name, String password) {
        if (!database.checkAccountExistence(name)) {
            database.addAccount(name, password);
            return new Account(name);
        } else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return database.getPassword(name);
    }

    public double getMoney() {
        return database.getMoney(name);
    }

    public static double getMoney(String name) {
        return database.getMoney(name);
    }

    public boolean exists() {
        return database.checkAccountExistence(name);
    }

    public void addMoney(double amount) {
        database.addMoney(name, amount);
        addLog(amount);
    }

    public void setPassword(String password) {
        database.changePassword(name, password);
    }

    public void addLog(double amount) {
        database.addLog(name, amount, getMoney());
    }

    public static List<String> getAllAccounts() {
        return database.getAllAccounts();
    }

    public static boolean checkPasswordStyle(String str) {
        try {
            int n = Integer.parseInt(str);
            return str.length() == 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean delete(String name) {
        if (database.checkAccountExistence(name)) {
            database.deleteAccount(name);
            return true;
        } else {
            return false;
        }
    }
}
