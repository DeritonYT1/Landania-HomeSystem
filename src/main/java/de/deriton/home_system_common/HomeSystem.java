package de.deriton.home_system_common;

import de.deriton.home_system_api.*;
import de.deriton.home_system_common.ConfigCreators.ConfigCreator;
import de.deriton.home_system_common.ConfigCreators.MessageConfigCreator;
import de.deriton.home_system_spigot.Commands.HomeCommand;
import de.deriton.home_system_spigot.Commands.HomesCommand;
import de.deriton.home_system_spigot.Listener.InventoryListener;
import de.deriton.home_system_spigot.Commands.delHomeCommand;
import de.deriton.home_system_spigot.Commands.setHomeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class HomeSystem extends JavaPlugin {

    private ConfigCreator configfile = null;
    private MessageConfigCreator msgconfig = null;
    private DBConnector db = null;
    private HomeData data = null;
    private InventoryData invdata = null;

    @Override
    public void onEnable() {
        // Load Configfiles
        this.configfile = new ConfigReader().readConfig();
        this.msgconfig = new ConfigReader().readMessageConfig();
        if(configfile.isDefaultconfig()) {
            System.out.println("<! IMPORTANT !>");
            System.out.println("Default Config created! Change DB Credientals and set DefaultConfig to false");
            System.out.println("<! IMPORTANT !>");
            /*
            Outdated & Unsupported Line (just a idea):
            getServer().getPluginManager().disablePlugin(this);
            */
        }
        // Create Database Connection
        this.db = new DBConnector();
        this.db.connect(configfile);
        this.data = new HomeData(db, msgconfig);
        this.invdata = new InventoryData(db, msgconfig);

        // Plugin startup logic
        this.getCommand("sethome").setExecutor(new setHomeCommand(data, msgconfig));
        this.getCommand("homes").setExecutor(new HomesCommand(invdata, data, msgconfig));
        this.getCommand("delhome").setExecutor(new delHomeCommand(data, msgconfig));
        this.getCommand("home").setExecutor(new HomeCommand(data, msgconfig));
        this.getServer().getPluginManager().registerEvents(new InventoryListener(data, msgconfig), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
