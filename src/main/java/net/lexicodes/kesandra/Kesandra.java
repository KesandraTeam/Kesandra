package net.lexicodes.kesandra;

import net.lexicodes.kesandra.commands.Commandbroadcast;
import net.lexicodes.kesandra.commands.Commandrepair;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Kesandra extends JavaPlugin {

    public static Kesandra plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(this.getCommand("repair")).setExecutor(new Commandrepair());
        Objects.requireNonNull(this.getCommand("broadcast")).setExecutor(new Commandbroadcast());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
