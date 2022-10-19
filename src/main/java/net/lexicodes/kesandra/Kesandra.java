package net.lexicodes.kesandra;

import net.lexicodes.kesandra.commands.Commandbroadcast;
import net.lexicodes.kesandra.commands.Commandrepair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import net.lexicodes.kesandra.events.chat.commands.MainPluginCommand;
import net.lexicodes.kesandra.events.block.BlockClick;
import net.lexicodes.kesandra.events.chat.TabComplete;
import net.lexicodes.kesandra.events.inventory.InventoryClick;
import net.lexicodes.kesandra.helpers.Utilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Kesandra extends JavaPlugin {

    public static Kesandra plugin;

    private static Kesandra instance;

    // chat messages
    private static Map<String, String> phrases = new HashMap<>();

    // core settings
    public static String prefix = "&c&l[&5&lKesandra&c&l] &8&l"; // generally unchanged unless otherwise stated in config
    public static String consolePrefix = "[Kesandra] ";

    // customizable settings
    public static boolean customSetting = false;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // load config.yml (generate one if not there)
        loadConfiguration();

        // load language.yml (generate one if not there)
        loadLangFile();

        // register commands and events
        registerCommands();
        registerEvents();

        // posts confirmation in chat
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been enabled");

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

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been disabled");
    }
    private void registerCommands() {
        getCommand("template").setExecutor(new MainPluginCommand());
        getCommand("repair").setExecutor(new Commandrepair());
        getCommand("broadcast").setExecutor(new Commandbroadcast());

        // set up tab completion
        getCommand("template").setTabCompleter(new TabComplete());
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new BlockClick(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
    }

    // load the config file and apply settings
    public void loadConfiguration() {
        // prepare config.yml (generate one if not there)
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()){
            Utilities.loadResource(this, "config.yml");
        }
        FileConfiguration config = this.getConfig();

        customSetting = config.getBoolean("custom-setting");
        // put more settings here

        Bukkit.getLogger().info(consolePrefix + "Settings Reloaded from config");
    }

    // load the language file and apply settings
    public void loadLangFile() {

        // load language.yml (generate one if not there)
        // console and IO, instance
        File langFile = new File(getDataFolder(), "language.yml");
        FileConfiguration langFileConfig = new YamlConfiguration();
        if (!langFile.exists()){ Utilities.loadResource(this, "language.yml"); }

        try { langFileConfig.load(langFile); }
        catch (Exception e3) { e3.printStackTrace(); }

        for(String priceString : langFileConfig.getKeys(false)) {
            phrases.put(priceString, langFileConfig.getString(priceString));
        }
    }

    // reload all plugin assets
    public static void reload() {
        getInstance().reloadConfig();
        getInstance().loadConfiguration();
        getInstance().loadLangFile();
        Bukkit.getLogger().info("configuration, values, and language settings reloaded");
    }

    // getters
    public static String getPhrase(String key) {
        return phrases.get(key);
    }
    public static Kesandra getInstance() { return instance; }
    public String getVersion() {
        return getDescription().getVersion();
    }

}
