package net.lexicodes.kesandra;

import net.lexicodes.kesandra.commands.Commandrepair;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Kesandra extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(this.getCommand("repair")).setExecutor(new Commandrepair());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
