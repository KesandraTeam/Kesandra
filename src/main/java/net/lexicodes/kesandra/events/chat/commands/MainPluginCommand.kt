package net.lexicodes.kesandra.events.chat.commands;

import net.lexicodes.kesandra.Kesandra;
import net.lexicodes.kesandra.helpers.Utilities;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainPluginCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // verify that the user has proper permissions
        if (!sender.hasPermission("template.user")) {
            Utilities.warnPlayer(sender, Kesandra.getPhrase("no-permissions-message"));
            return true;
        }

        try {

            switch (args[0].toLowerCase()) {
                case "help":
                    help(sender);
                    break;
                case "info":
                    info(sender);
                    break;

                // put plugin specific commands here

                case "reload":
                    if (sender.hasPermission("template.admin")) reload(sender);
                    else Utilities.warnPlayer(sender, Kesandra.getPhrase("no-permissions-message"));
                    break;
                default:
                    Utilities.warnPlayer(sender, Kesandra.getPhrase("not-a-command-message"));
                    help(sender);
                    break;
            }

        } catch(Exception e) {
            Utilities.warnPlayer(sender, Kesandra.getPhrase("formatting-error-message"));
        }

        return true;
    }

    private void info(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "------------------------------");
        sender.sendMessage(Kesandra.prefix + ChatColor.GRAY + "Plugin Info");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GREEN + "Version " + Kesandra.getInstance().getVersion() + " - By LexiCodes And Almondz");
        sender.sendMessage(ChatColor.DARK_PURPLE + "------------------------------");
    }

    private void tutorial(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "------------------------------");
        sender.sendMessage(Kesandra.prefix + ChatColor.GRAY + "Plugin Info");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GREEN + "Version " + Kesandra.getInstance().getVersion() + " - By LexiCodes And Almondz");
        sender.sendMessage(ChatColor.DARK_PURPLE + "------------------------------");
    }

    private void help(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "------------------------------");
        sender.sendMessage(Kesandra.prefix + ChatColor.GRAY + "Commands");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/template help");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/template info");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/template tutorial");
        sender.sendMessage(ChatColor.DARK_PURPLE + "- " + ChatColor.GRAY + "/template reload");
        sender.sendMessage(ChatColor.DARK_PURPLE + "------------------------------");
    }

    private void reload(CommandSender sender) {
        Kesandra.getInstance().reloadConfig();
        Kesandra.getInstance().loadConfiguration();
        Kesandra.getInstance().loadLangFile();

        Utilities.informPlayer(sender, "configuration, values, and language settings reloaded");
    }

}
