package de.deriton.home_system_common;

import de.deriton.home_system_spigot.setHomeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomeSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("sethome").setExecutor(new setHomeCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
