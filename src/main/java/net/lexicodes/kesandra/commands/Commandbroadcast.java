package net.lexicodes.kesandra.commands;

import net.lexicodes.kesandra.Kesandra;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Commandbroadcast implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        if (p.hasPermission("kesandra.broadcast")){
            if (args.length == 0){
                p.sendMessage(ChatColor.RED + "Correct usage: /broadcast <msg>");
                return false;
            }
            if (args.length >= 1){
                String message = "";
                for (int i = 0; i < args.length; i++) {
                    message = message + args[i] + " ";
                }
                if (message.length() == 0){
                    p.sendMessage(ChatColor.RED + "Correct usage: /broadcast <msg>");
                    return false;
                }
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Kesandra.plugin.getConfig().getString("Broadcast message").replace("%message%", message)));
                return true;
            }
        }
        return false;
    }
}
