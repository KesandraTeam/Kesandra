package net.lexicodes.kesandra.events.block;

import net.lexicodes.kesandra.helpers.ActionSound;
import net.lexicodes.kesandra.helpers.Utilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockClick implements Listener {

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        Utilities.playSound(ActionSound.ERROR, event.getPlayer());

    }

}