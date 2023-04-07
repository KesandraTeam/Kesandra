package net.lexicodes.kesandra.helpers

import com.google.common.io.ByteStreams
import net.lexicodes.API.multiversion.*
import net.lexicodes.API.multiversion.Sound
import net.lexicodes.kesandra.Kesandra
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.FileOutputStream
import java.util.*

object Utilities {
    // list of transparent blocks to be ignored when a player looks at a block
    private val TRANSPARENT: Set<Material?> = EnumSet.of(
        XMaterial.AIR.parseMaterial(),
        XMaterial.BLACK_CARPET.parseMaterial(),
        XMaterial.BLUE_CARPET.parseMaterial(),
        XMaterial.BROWN_CARPET.parseMaterial(),
        XMaterial.CYAN_CARPET.parseMaterial(),
        XMaterial.GRAY_CARPET.parseMaterial(),
        XMaterial.GREEN_CARPET.parseMaterial(),
        XMaterial.LIGHT_BLUE_CARPET.parseMaterial(),
        XMaterial.LIME_CARPET.parseMaterial(),
        XMaterial.MAGENTA_CARPET.parseMaterial(),
        XMaterial.ORANGE_CARPET.parseMaterial(),
        XMaterial.PINK_CARPET.parseMaterial(),
        XMaterial.PURPLE_CARPET.parseMaterial(),
        XMaterial.RED_CARPET.parseMaterial(),
        XMaterial.WHITE_CARPET.parseMaterial(),
        XMaterial.YELLOW_CARPET.parseMaterial()
    )

    // list of all supported inventory blocks in the plugin
    val INVENTORY_BLOCKS = Arrays.asList(
        XMaterial.CHEST.parseMaterial(),
        XMaterial.TRAPPED_CHEST.parseMaterial(),
        XMaterial.ENDER_CHEST.parseMaterial(),
        XMaterial.SHULKER_BOX.parseMaterial(),
        XMaterial.BLACK_SHULKER_BOX.parseMaterial(),
        XMaterial.BLUE_SHULKER_BOX.parseMaterial(),
        XMaterial.BROWN_SHULKER_BOX.parseMaterial(),
        XMaterial.CYAN_SHULKER_BOX.parseMaterial(),
        XMaterial.GRAY_SHULKER_BOX.parseMaterial(),
        XMaterial.GREEN_SHULKER_BOX.parseMaterial(),
        XMaterial.LIGHT_BLUE_SHULKER_BOX.parseMaterial(),
        XMaterial.LIGHT_GRAY_SHULKER_BOX.parseMaterial(),
        XMaterial.LIME_SHULKER_BOX.parseMaterial(),
        XMaterial.MAGENTA_SHULKER_BOX.parseMaterial(),
        XMaterial.ORANGE_SHULKER_BOX.parseMaterial(),
        XMaterial.PINK_SHULKER_BOX.parseMaterial(),
        XMaterial.PURPLE_SHULKER_BOX.parseMaterial(),
        XMaterial.RED_SHULKER_BOX.parseMaterial(),
        XMaterial.WHITE_SHULKER_BOX.parseMaterial(),
        XMaterial.YELLOW_SHULKER_BOX.parseMaterial()
    )
    private val mostRecentSelect: Map<Player, Long> = HashMap()

    // load file from JAR with comments
    fun loadResource(plugin: Plugin, resource: String?): File {
        val folder = plugin.dataFolder
        if (!folder.exists()) folder.mkdir()
        val resourceFile = File(folder, resource)
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile()
                plugin.getResource(resource!!)
                    .use { `in` -> FileOutputStream(resourceFile).use { out -> ByteStreams.copy(`in`, out) } }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return resourceFile
    }

    // convert a location to formatted string (world,x,y,z)
    fun toLocString(location: Location?): String {
        return if (location == null) "" else location.world.name + "," + location.x.toInt() + "," + location.y.toInt() + "," + location.z.toInt()
    }

    // renames item
    fun nameItem(item: ItemStack, name: String?): ItemStack {
        val meta = item.itemMeta
        meta.setDisplayName(name)
        item.itemMeta = meta
        return item
    }

    // creates item that is renamed given material and name
    fun nameItem(item: Material?, name: String?): ItemStack {
        return nameItem(ItemStack(item!!), name)
    }

    // set the lore of an item
    fun loreItem(item: ItemStack, lore: List<String?>?): ItemStack {
        val meta = item.itemMeta
        meta.lore = lore
        item.itemMeta = meta
        return item
    }

    // makes visible string invisible to player
    fun convertToInvisibleString(s: String): String {
        if (Version.Companion.getVersion()
                .isBiggerThan(Version.v1_15)
        ) return s // HOTFIX to prevent invisible text being garbled by 1.16 changes
        val hidden = StringBuilder()
        for (c in s.toCharArray()) hidden.append(ChatColor.COLOR_CHAR.toString() + "").append(c)
        return hidden.toString()
    }

    // make invisible string visible to player
    fun convertToVisibleString(s: String): String {
        if (Version.Companion.getVersion()
                .isBiggerThan(Version.v1_15)
        ) return s // HOTFIX to prevent invisible text being garbled by 1.16 changes
        var c = ""
        c = c + ChatColor.COLOR_CHAR
        return s.replace(c.toRegex(), "")
    }

    // warns player of something in plugin
    fun warnPlayer(sender: CommandSender, messages: List<String>) {
        if (sender is Player) {
            playSound(ActionSound.ERROR, sender)
        }
        for (message in messages) sender.sendMessage(Kesandra.Companion.prefix + ChatColor.RESET + ChatColor.RED + message)
    }

    fun warnPlayer(sender: CommandSender?, message: String?) {
        warnPlayer(sender, listOf(message))
    }

    // informs player of something in plugin
    fun informPlayer(sender: CommandSender, messages: List<String>) {
        for (message in messages) sender.sendMessage(Kesandra.Companion.prefix + ChatColor.RESET + ChatColor.GRAY + message)
    }

    fun informPlayer(sender: CommandSender?, message: String) {
        informPlayer(sender!!, listOf(message))
    }

    // return the block the player is looking at, ignoring transparent blocks
    fun getBlockLookingAt(player: Player): Block {
        return player.getTargetBlock(TRANSPARENT, 120)
    }

    // play sound at player (version independent)
    fun playSound(sound: ActionSound?, player: Player) {
        when (sound) {
            ActionSound.OPEN -> Sound.CHEST_OPEN.playSound(player)
            ActionSound.MODIFY -> Sound.ANVIL_USE.playSound(player)
            ActionSound.SELECT -> Sound.LEVEL_UP.playSound(player)
            ActionSound.CLICK -> Sound.CLICK.playSound(player)
            ActionSound.POP -> Sound.CHICKEN_EGG_POP.playSound(player)
            ActionSound.BREAK -> Sound.ANVIL_LAND.playSound(player)
            ActionSound.ERROR -> Sound.BAT_DEATH.playSound(player)
        }
    }
}