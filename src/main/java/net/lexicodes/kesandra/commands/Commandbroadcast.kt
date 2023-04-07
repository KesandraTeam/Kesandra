package net.lexicodes.kesandra.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Commandbroadcast : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val p = sender as Player
        if (p.hasPermission("kesandra.broadcast")) {
            if (args.size == 0) {
                p.sendMessage(ChatColor.RED.toString() + "Correct usage: /broadcast <msg>")
                return false
            }
            if (args.size >= 1) {
                var message = ""
                for (i in args.indices) {
                    message = message + args[i] + " "
                }
                if (message.length == 0) {
                    p.sendMessage(ChatColor.RED.toString() + "Correct usage: /broadcast <msg>")
                    return false
                }
                Bukkit.broadcastMessage(ChatColor.GOLD.toString() + "[Broadcast] " + ChatColor.DARK_RED + message)
                return true
            }
        }
        return false
    }
}