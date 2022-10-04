package net.lexicodes.kesandra.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Commandrepair implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        ItemStack item = player.getItemInHand();

        if(item.getType().isBlock()) {
            player.sendMessage("You Cannot Repair A Block!");
        } else if(Objects.requireNonNull(item.getItemMeta()).isUnbreakable()) {
            player.sendMessage("This Item Is Unbreakable Or Not Repairable!");
        } else if(item.getDurability() == 0 ) {
            player.sendMessage("This Item Is Already At Max Durability!");
        }else {
                item.setDurability((short) -3276);
        }
        return false;
    }
}
