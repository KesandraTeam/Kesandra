package net.lexicodes.kesandra.helpers

import net.lexicodes.API.multiversion.API
import net.lexicodes.API.multiversion.XMaterial
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta

object MenuUtils {
    fun tutorialMenu(player: Player) {
        val book = ItemStack(XMaterial.WRITTEN_BOOK.parseMaterial()!!)
        val meta = book.itemMeta as BookMeta
        meta.author = "ThirtyVirus"
        meta.title = "Welcome to TemplatePlugin!"
        val pages: MutableList<String> = ArrayList()

        // exmaple main menu
        pages.add(
            ChatColor.translateAlternateColorCodes(
                '&',
                """      &7&lWelcome to:
   &c&lTemplate&5&lPlugin&r
This guide book will show you everything you need to know about template! Happy reading!

 - ThirtyVirus

&7&lGo to the next page for info on a second page!"""
            )
        )

        // example secondary page
        pages.add(
            ChatColor.translateAlternateColorCodes(
                '&',
                """
                &c&lIn-Inventory&r
                
                Middle Click any slot in any container-block's open inventory, and it will instantly be sorted!
                
                Use &c'/template inv'&r to sort your personal inventory.
                
                &7&lNext: External Sorting
                
                """.trimIndent()
            )
        )
        meta.pages = pages
        book.setItemMeta(meta)
        Utilities.playSound(ActionSound.CLICK, player)
        API.Companion.openBook(book, player)
    }
}