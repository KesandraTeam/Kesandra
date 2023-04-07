package net.lexicodes.kesandra.events.chat

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.*

class TabComplete : TabCompleter {
    private var sender: CommandSender? = null
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): List<String>? {
        this.sender = sender

        // verify sender is a player
        if (sender !is Player) return null
        val player = sender
        val arguments = ArrayList<String>()

        // tab completion for /exchange command
        if (command.name == "template") {

            // no arguments
            if (args.size == 1) {
                if (player.hasPermission("template.user")) {
                    arguments.addAll(mutableListOf("help", "info", "tutorial"))
                }
                if (player.hasPermission("template.admin")) {
                    arguments.addAll(listOf("reload"))
                }
                val iter = arguments.iterator()
                while (iter.hasNext()) {
                    val str = iter.next().lowercase(Locale.getDefault())
                    if (!str.contains(args[0].lowercase(Locale.getDefault()))) iter.remove()
                }
            }
        }
        return arguments
    }
}