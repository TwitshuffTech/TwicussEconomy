package net.ddns.twicusstumble.twicusseconomy.system;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import net.ddns.twicusstumble.twicusseconomy.data.Database;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bankbook {
    private static final Database database = Database.getInstance();
    private final ItemStack bankbook;

    public Bankbook(ItemStack item) {
        this.bankbook = item;
    }

    public static Bankbook create(Account account) {
        ItemStack bankbook = new ItemStack(Material.WRITTEN_BOOK, 1);

        BookMeta bookMeta = (BookMeta)bankbook.getItemMeta();
        bookMeta.setTitle(account.getName());
        bookMeta.setAuthor("System");
        bookMeta.setDisplayName(ChatColor.GOLD + "BANK BOOK - " + account.getName());
        String text = ChatColor.BOLD + "Account: " + account.getName() + "\nBalance: 0.0" + ChatColor.RESET + "\n ";
        bookMeta.setPages(text);
        bankbook.setItemMeta(bookMeta);

        return new Bankbook(bankbook);
    }

    public static boolean isBankbook(ItemStack itemStack) {
        if (!itemStack.getType().equals(Material.WRITTEN_BOOK)) {
            return false;
        } else {
            BookMeta bookMeta = (BookMeta)itemStack.getItemMeta();
            if (bookMeta.hasAuthor() && bookMeta.getAuthor().equals("System") && bookMeta.hasGeneration() && bookMeta.getGeneration().equals(BookMeta.Generation.ORIGINAL)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public ItemStack getItemStack() {
        return bankbook;
    }

    public Account getAccount() {
        BookMeta bookMeta = (BookMeta)bankbook.getItemMeta();
        return new Account(bookMeta.getTitle());
    }

    public void updateText() {
        BookMeta bookMeta = (BookMeta)bankbook.getItemMeta();
        Account account = getAccount();

        String oldText = bookMeta.getPage(1);
        List<String> pre_lines = Arrays.asList(oldText.split("\n"));
        List<String> lines = new ArrayList<>(pre_lines);
        lines.set(1, ChatColor.BOLD + "Balance: " + new DecimalFormat("###,###.0").format(account.getMoney()) + ChatColor.RESET);

        String text = String.join("\n", lines.subList(0, 3)) + "\n" + String.join("\n", database.getLogs(account.getName(), 8));

        bookMeta.setPages(text);
        bankbook.setItemMeta(bookMeta);
    }
}
