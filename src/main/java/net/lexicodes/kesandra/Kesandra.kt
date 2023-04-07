package net.lexicodes.kesandra

import net.lexicodes.kesandra.commands.Commandbroadcast
import net.lexicodes.kesandra.commands.Commandrepair
import net.lexicodes.kesandra.events.block.BlockClick
import net.lexicodes.kesandra.events.chat.TabComplete
import net.lexicodes.kesandra.events.chat.commands.MainPluginCommand
import net.lexicodes.kesandra.events.inventory.InventoryClick
import net.lexicodes.kesandra.helpers.Utilities
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Kesandra : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        instance = this

        // load config.yml (generate one if not there)
        loadConfiguration()

        // load language.yml (generate one if not there)
        loadLangFile()

        // register commands and events
        registerCommands()
        registerEvents()

        // posts confirmation in chat
        getLogger().info(description.name + " V: " + description.version + " has been enabled")

        // example scheduled task
        //if (autoPurge){
        //    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
        //        public void run() {
        //            if (debug) Bukkit.getLogger().info("Automatically Purged " + Utilities.purge(shops, consolePrefix, debug, purgeAge) + " empty shops that haven't been active in the past " + purgeAge + " hour(s).");
        //            if (!debug) Utilities.purge(shops, consolePrefix, debug, purgeAge);
        //        }
        //    }, 20 * 60 * 60, 20 * 60 * 60);
        //}
    }

    override fun onDisable() {
        // Plugin shutdown logic
        getLogger().info(description.name + " V: " + description.version + " has been disabled")
    }

    private fun registerCommands() {
        getCommand("template")!!.setExecutor(MainPluginCommand())
        getCommand("repair")!!.setExecutor(Commandrepair())
        getCommand("broadcast")!!.setExecutor(Commandbroadcast())

        // set up tab completion
        getCommand("template")!!.tabCompleter = TabComplete()
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(BlockClick(), this)
        server.pluginManager.registerEvents(InventoryClick(), this)
    }

    // load the config file and apply settings
    fun loadConfiguration() {
        // prepare config.yml (generate one if not there)
        val configFile = File(dataFolder, "config.yml")
        if (!configFile.exists()) {
            Utilities.loadResource(this, "config.yml")
        }
        val config = this.config

        // general settings
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("plugin-prefix")!!)
        customSetting = config.getBoolean("custom-setting")
        // put more settings here
        Bukkit.getLogger().info(consolePrefix + "Settings Reloaded from config")
    }

    // load the language file and apply settings
    fun loadLangFile() {

        // load language.yml (generate one if not there)
        // console and IO, instance
        val langFile = File(dataFolder, "language.yml")
        val langFileConfig: FileConfiguration = YamlConfiguration()
        if (!langFile.exists()) {
            Utilities.loadResource(this, "language.yml")
        }
        try {
            langFileConfig.load(langFile)
        } catch (e3: Exception) {
            e3.printStackTrace()
        }
        for (priceString in langFileConfig.getKeys(false)) {
            phrases[priceString] = langFileConfig.getString(priceString)!!
        }
    }

    val version: String
        get() = description.version

    companion object {
        var plugin: Kesandra? = null
        var instance: Kesandra? = null
            private set

        // chat messages
        private val phrases: MutableMap<String, String> = HashMap()

        // core settings
        var prefix = "&c&l[&5&lKesandra&c&l] &8&l" // generally unchanged unless otherwise stated in config
        var consolePrefix = "[Kesandra] "

        // customizable settings
        var customSetting = false

        // reload all plugin assets
        fun reload() {
            instance!!.reloadConfig()
            instance!!.loadConfiguration()
            instance!!.loadLangFile()
            Bukkit.getLogger().info("configuration, values, and language settings reloaded")
        }

        // getters
        fun getPhrase(key: String): String? {
            return phrases[key]
        }
    }
}