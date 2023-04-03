package net.ddns.twicusstumble.twicusseconomy.commands;

import net.ddns.twicusstumble.twicusseconomy.system.Bankbook;
import net.ddns.twicusstumble.twicusseconomy.system.Money;
import net.ddns.twicusstumble.twicusseconomy.util.Utils;
import net.ddns.twicusstumble.twicusseconomy.ItemShop.ItemShop;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Shop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            if (!Utils.isPositiveInteger(args[0]) || !Utils.isPositiveInteger(args[1])) {
                sender.sendMessage("引数は自然数で入力してください");
                return false;
            }

            int price = Integer.parseInt(args[0]);
            price = price / 100 * 100;
            int numUnit = Integer.parseInt(args[1]);

            if (price < 100) {
                sender.sendMessage("値段は" + Money.formatToInteger(100) + " 以上で指定してください");
            }

            ItemStack itemStack = ((Player) sender).getInventory().getItemInMainHand();
            if (!Bankbook.isBankbook(itemStack)) {
                sender.sendMessage("通帳を手に持ってください");
                return false;
            }

            Bankbook bankbook = new Bankbook(itemStack);

            Block block = ((Player) sender).getTargetBlock(null, 6);
            if (block == null || !(block.getState() instanceof Sign)) {
                sender.sendMessage("ショップを作成できません");
                return false;
            }

            Sign sign = (Sign) block.getState();
            Block baseBlock = Utils.getBlockSignIsPlaced(sign);

            if (baseBlock == null || !(baseBlock.getState() instanceof Chest)) {
                sender.sendMessage("ショップを作成できません");
                return false;
            }

            Chest chest = (Chest) baseBlock.getState();
            if (ItemShop.canCreate(sign, chest)) {
                ItemShop.create(bankbook.getAccount(), sign, chest, price, numUnit);
                return true;
            } else {
                sender.sendMessage("ショップを作成できません");
                return false;
            }
        }
        return false;
    }
}
