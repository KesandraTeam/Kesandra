package net.lexicodes.kesandra.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class Commandrepair : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val player = sender as Player
        val item = player.itemInHand
        if (item.type.isBlock) {
            player.sendMessage("You Cannot Repair A Block!")
        } else if (Objects.requireNonNull(item.itemMeta).isUnbreakable) {
            player.sendMessage("This Item Is Unbreakable Or Not Repairable!")
        } else if (item.durability.toInt() == 0) {
            player.sendMessage("This Item Is Already At Max Durability!")
        } else {
            item.durability = (-3276.toShort()).toShort()
        }
        return false
    }
}