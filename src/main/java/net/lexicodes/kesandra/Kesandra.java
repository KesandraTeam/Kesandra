package net.lexicodes.kesandra;

import net.lexicodes.kesandra.commands.Commandrepair;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kesandra extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("repair").setExecutor(new Commandrepair());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
