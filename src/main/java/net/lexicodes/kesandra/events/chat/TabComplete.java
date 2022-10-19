package net.lexicodes.kesandra.events.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TabComplete implements TabCompleter {

    private CommandSender sender;

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.sender = sender;

        // verify sender is a player
        if (!(sender instanceof Player)) return null;
        Player player = (Player) sender;

        ArrayList<String> arguments = new ArrayList<>();

        // tab completion for /exchange command
        if (command.getName().equals("template")) {

            // no arguments
            if (args.length == 1){
                if (player.hasPermission("template.user")) { arguments.addAll(Arrays.asList("help", "info", "tutorial")); }
                if (player.hasPermission("template.admin")) { arguments.addAll(Collections.singletonList("reload")); }

                Iterator<String> iter = arguments.iterator(); while (iter.hasNext()) { String str = iter.next().toLowerCase(); if (!str.contains(args[0].toLowerCase())) iter.remove(); }
            }
        }

        return arguments;
    }
}
