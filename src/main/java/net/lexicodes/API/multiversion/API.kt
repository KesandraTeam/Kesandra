package net.lexicodes.API.multiversion

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class API {
    // get the item in the player's main hand
    fun getItemInHand(player: Player): ItemStack {
        return if (Version.Companion.getVersion()
                .isBiggerThan(Version.v1_8)
        ) {
            player.inventory.itemInMainHand
        } else {
            player.inventory.itemInHand
        }
    }

    companion object {
        // get an entity given the UUID
        fun getEntity(id: UUID): Entity? {

            // 1.11 and above method
            //if (Version.getVersion().isBiggerThan(Version.v1_10)) return Bukkit.getEntity(id);

            // 1.10 and below method
            for (w in Bukkit.getWorlds()) {
                for (entity in w.entities) {
                    if (entity.uniqueId === id) return entity
                }
            }
            return null
        }

        // determine whether a block is a slab
        fun isSlab(material: Material): Boolean {
            return (material.name.lowercase(Locale.getDefault())
                .contains("slab") || material.name.lowercase(Locale.getDefault())
                .contains("step")) && !material.name.lowercase(Locale.getDefault()).contains("double")
        }

        // determine whether a slab is the bottom variety
        //    public static boolean isBottomSlab(Block block) {
        //        if(block == null || !isSlab(block.getType())) return false;
        //
        //        //location.getBlock().getBlockData().getAsString().contains("type=bottom")
        //        if(Version.getVersion().isBiggerThan(Version.v1_12)) {
        //            return block.getBlockData().getAsString().contains("type=bottom");
        //        } else {
        //            return block.getData() < 8; // works in 1.12
        //        }
        //    }
        //
        //    // determine whether a slab is the top variety
        //    public static boolean isTopSlab(Block block) {
        //        if(block == null || !isSlab(block.getType())) return false;
        //
        //        //location.getBlock().getBlockData().getAsString().contains("type=top")
        //        if(Version.getVersion().isBiggerThan(Version.v1_12)) {
        //            return block.getBlockData().getAsString().contains("type=top");
        //        } else {
        //            return block.getData() > 7;
        //        }
        //    }
        // force open a book
        fun openBook(book: ItemStack?, player: Player) {

            // test if version greater than 1.14.3 then use 1.8 book
            if (Version.Companion.getVersion().isBiggerThan(Version.v1_14)) BookUtil_1_14_4.openBook(
                book,
                player
            ) else if (Version.Companion.getVersion().isBiggerThan(
                    Version.v1_8
                )
            ) BookUtil_1_9.openBook(book, player) else BookUtil_1_8.openBook(book, player)
        }
    }
}