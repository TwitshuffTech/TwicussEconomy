package ten.sndd.elbmutssuciwt.twicusseconomy.data;

import org.bukkit.ChatColor;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class Database {

    private static Database instance;
    private String url = "jdbc:sqlite:plugins/TwicussEconomy/database.db";
    private Connection connection = null;
    private Statement statement = null;

    private Database() {
        createTables();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void createTables() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS balances (account_name TEXT, password TEXT, money REAL DEFAULT 0, PRIMARY KEY (account_name))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS logs (timestamp TIMESTAMP, account_name TEXT, line TEXT, PRIMARY KEY (timestamp))");
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
    }

    public boolean checkAccountExistence(String accountName) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            String sql = "SELECT COUNT(*) as count FROM balances WHERE account_name = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, accountName);

            ResultSet resultSet = pStatement.executeQuery();
            int n = resultSet.getInt("count");
            if (n >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
        return false;
    }

    public void addAccount(String accountName, String password) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            String sql = "INSERT INTO balances VALUES (?, ?, ?)";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, accountName);
            pStatement.setString(2, password);
            pStatement.setDouble(3, 0);

            pStatement.executeUpdate();
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
    }

    public void deleteAccount(String accountName) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            String sql = "DELETE FROM balances WHERE account_name = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, accountName);

            pStatement.executeUpdate();
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
    }

    public void addLog(String accountName, double amount, double money) {
        String newLine = "";
        if (amount == 0) {
            return;
        }
        if (amount > 0) {
            newLine = "         ";
            String amount_s = new DecimalFormat("###,###").format(amount);
            for (int i = 0; i < 9 - amount_s.length(); i++) {
                newLine += " ";
            }
            newLine += ChatColor.DARK_GREEN + amount_s;

            String money_s = new DecimalFormat("###,##0").format(money);
            for (int i = 0; i < 9 - money_s.length(); i++) {
                newLine += " ";
            }
            newLine += ChatColor.BLACK + money_s;
        } else if (amount < 0) {
            newLine = "";
            String amount_s = new DecimalFormat("###,##0").format(-amount);
            for (int i = 0; i < 9 - amount_s.length(); i++) {
                newLine += " ";
            }
            newLine += ChatColor.DARK_RED + amount_s + "         ";

            String money_s = new DecimalFormat("###,##0").format(money);
            for (int i = 0; i < 9 - money_s.length(); i++) {
                newLine += " ";
            }
            newLine += ChatColor.BLACK + money_s;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            String sql = "INSERT INTO logs VALUES (?, ?, ?)";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            pStatement.setString(2, accountName);
            pStatement.setString(3, newLine);

            pStatement.executeUpdate();
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
    }

    public void deleteLog(Timestamp timestamp) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            String sql = "DELETE FROM logs WHERE timestamp = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setTimestamp(1, timestamp);

            pStatement.executeUpdate();
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
    }

    public List<String> getAllAccounts() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM balances");

            List<String> accounts = new ArrayList<>();
            while (resultSet.next()) {
                accounts.add(resultSet.getString("account_name"));
            }
            return accounts;
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
        return null;
    }
    public String getPassword(String accountName) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            String sql = "SELECT * FROM balances WHERE account_name = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, accountName);

            ResultSet resultSet = pStatement.executeQuery();
            return resultSet.getString("password");
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
        return null;
    }

    public double getMoney(String accountName) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            String sql = "SELECT * FROM balances WHERE account_name = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, accountName);

            ResultSet resultSet = pStatement.executeQuery();
            return resultSet.getDouble("money");
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
        return 0;
    }

    public List<String> getLogs(String accountName, int number) {
        List<String> lines = new ArrayList<>();
        List<Timestamp> removingLogs = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            String sql = "SELECT * FROM logs WHERE account_name = ? ORDER BY timestamp DESC";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, accountName);

            ResultSet resultSet = pStatement.executeQuery();

            int count = 0;
            while (resultSet.next()) {
                if (count < number) {
                    lines.add(resultSet.getString("line"));
                } else {
                    removingLogs.add(resultSet.getTimestamp("timestamp"));
                }
                count++;
            }
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
        Collections.reverse(lines);
        removingLogs.forEach(timestamp -> deleteLog(timestamp));

        return lines;
    }

    public void changePassword(String accountName, String newPassword) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            String sql = "UPDATE balances SET password = ? WHERE account_name = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, newPassword);
            pStatement.setString(2, accountName);

            pStatement.executeUpdate();
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
    }

    public void addMoney(String accountName, double amount) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

            String sql = "UPDATE balances SET money = ? WHERE account_name = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setDouble(1, getMoney(accountName) + amount);
            pStatement.setString(2, accountName);

            pStatement.executeUpdate();
        } catch (Exception e) {
            getLogger().info(e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getLogger().info(e.toString());
                }
            }
        }
    }
}
