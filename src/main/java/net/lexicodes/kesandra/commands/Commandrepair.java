package net.lexicodes.kesandra.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Commandrepair implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        ItemStack item = player.getItemInHand();
        if(item == null || item.getType().isBlock() || item.getDurability() == 0) {
            player.sendMessage("That Is Not A Repairable Item");
        } else {
            item.setDurability((short) 5000);
        }
        return false;
    }
}
