package net.lexicodes.kesandra.events.chat.commands

import net.lexicodes.kesandra.Kesandra
import net.lexicodes.kesandra.helpers.Utilities
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.*

class MainPluginCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        // verify that the user has proper permissions
        if (!sender.hasPermission("template.user")) {
            Utilities.warnPlayer(sender, Kesandra.Companion.getPhrase("no-permissions-message"))
            return true
        }
        try {
            when (args[0].lowercase(Locale.getDefault())) {
                "help" -> help(sender)
                "info" -> info(sender)
                "reload" -> if (sender.hasPermission("template.admin")) reload(sender) else Utilities.warnPlayer(
                    sender,
                    Kesandra.Companion.getPhrase("no-permissions-message")
                )

                else -> {
                    Utilities.warnPlayer(sender, Kesandra.Companion.getPhrase("not-a-command-message"))
                    help(sender)
                }
            }
        } catch (e: Exception) {
            Utilities.warnPlayer(sender, Kesandra.Companion.getPhrase("formatting-error-message"))
        }
        return true
    }

    private fun info(sender: CommandSender) {
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + "------------------------------")
        sender.sendMessage(Kesandra.Companion.prefix + ChatColor.GRAY + "Plugin Info")
        sender.sendMessage(
            ChatColor.DARK_PURPLE.toString() + "- " + ChatColor.GREEN + "Version " + Kesandra.Companion.getInstance()
                .getVersion() + " - By LexiCodes And Almondz"
        )
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + "------------------------------")
    }

    private fun tutorial(sender: CommandSender) {
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + "------------------------------")
        sender.sendMessage(Kesandra.Companion.prefix + ChatColor.GRAY + "Plugin Info")
        sender.sendMessage(
            ChatColor.DARK_PURPLE.toString() + "- " + ChatColor.GREEN + "Version " + Kesandra.Companion.getInstance()
                .getVersion() + " - By LexiCodes And Almondz"
        )
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + "------------------------------")
    }

    private fun help(sender: CommandSender) {
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + "------------------------------")
        sender.sendMessage(Kesandra.Companion.prefix + ChatColor.GRAY + "Commands")
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + "- " + ChatColor.GRAY + "/template help")
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + "- " + ChatColor.GRAY + "/template info")
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + "- " + ChatColor.GRAY + "/template tutorial")
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + "- " + ChatColor.GRAY + "/template reload")
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + "------------------------------")
    }

    private fun reload(sender: CommandSender) {
        Kesandra.Companion.getInstance().reloadConfig()
        Kesandra.Companion.getInstance().loadConfiguration()
        Kesandra.Companion.getInstance().loadLangFile()
        Utilities.informPlayer(sender, "configuration, values, and language settings reloaded")
    }
}