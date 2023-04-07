package net.lexicodes.kesandra.events.inventory

import net.lexicodes.kesandra.helpers.ActionSound
import net.lexicodes.kesandra.helpers.Utilities
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryClick : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        Utilities.playSound(ActionSound.CLICK, event.whoClicked as Player)
    }
}