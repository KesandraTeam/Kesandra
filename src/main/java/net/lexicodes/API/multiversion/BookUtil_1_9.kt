package net.lexicodes.API.multiversion

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta
import java.lang.reflect.Method

/**
 * Create a "Virtual" book gui that doesn't require the user to have a book in their hand.
 * Requires ReflectionUtil class.
 * Built for Minecraft 1.9
 * @author Jed
 */
object BookUtil_1_9 {
    var isInitialised = false
        private set
    private var getHandle: Method? = null
    private var openBook: Method? = null

    init {
        try {
            getHandle =
                ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle")
            openBook = ReflectionUtils.getMethod(
                "EntityPlayer",
                ReflectionUtils.PackageType.MINECRAFT_SERVER,
                "a",
                ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ItemStack"),
                ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumHand")
            )
            isInitialised = true
        } catch (e: ReflectiveOperationException) {
            e.printStackTrace()
            Bukkit.getServer().logger.warning("Cannot force open book!")
            isInitialised = false
        }
    }

    /**
     * Open a "Virtual" Book ItemStack.
     * @param i Book ItemStack.
     * @param p Player that will open the book.
     * @return
     */
    fun openBook(i: ItemStack?, p: Player): Boolean {
        if (!isInitialised) return false
        val held = p.inventory.itemInMainHand
        try {
            p.inventory.setItemInMainHand(i)
            sendPacket(i, p)
        } catch (e: ReflectiveOperationException) {
            e.printStackTrace()
            isInitialised = false
        }
        p.inventory.setItemInMainHand(held)
        return isInitialised
    }

    @Throws(ReflectiveOperationException::class)
    private fun sendPacket(i: ItemStack?, p: Player) {
        val entityplayer = getHandle!!.invoke(p)
        val enumHand = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumHand")
        val enumArray: Array<Any> = enumHand!!.getEnumConstants()
        openBook!!.invoke(entityplayer, getItemStack(i), enumArray[0])
    }

    fun getItemStack(item: ItemStack?): Any? {
        try {
            val asNMSCopy = ReflectionUtils.getMethod(
                ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"),
                "asNMSCopy",
                ItemStack::class.java
            )
            return asNMSCopy!!.invoke(
                ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"),
                item
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * Set the pages of the book in JSON format.
     * @param metadata BookMeta of the Book ItemStack.
     * @param pages Each page to be added to the book.
     */
    fun setPages(metadata: BookMeta?, pages: List<String?>) {
        val p: MutableList<Any?>
        var page: Any?
        try {
            p = ReflectionUtils.getField(
                ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaBook"),
                true,
                "pages"
            )[metadata] as MutableList<Any?>
            for (text in pages) {
                page = ReflectionUtils.invokeMethod(
                    ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent\$ChatSerializer")
                        .newInstance(), "a", text
                )
                p.add(page)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}