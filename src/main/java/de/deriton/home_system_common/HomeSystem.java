package de.deriton.home_system_common;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import de.deriton.home_system_api.BungeeListener;
import de.deriton.home_system_api.DBConnector;
import de.deriton.home_system_api.HomeData;
import de.deriton.home_system_api.InventoryData;
import de.deriton.home_system_common.ConfigCreators.ConfigCreator;
import de.deriton.home_system_common.ConfigCreators.DELanguageCreator;
import de.deriton.home_system_common.ConfigCreators.ENLanguageCreator;
import de.deriton.home_system_spigot.Commands.HomeCommand;
import de.deriton.home_system_spigot.Commands.HomesCommand;
import de.deriton.home_system_spigot.Commands.Listener.InventoryListener;
import de.deriton.home_system_spigot.Commands.delHomeCommand;
import de.deriton.home_system_spigot.Commands.setHomeCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class HomeSystem extends JavaPlugin {

    private ConfigCreator configfile = null;
    private DELanguageCreator deconfig = null;
    private ENLanguageCreator enconfig = null;
    private DBConnector db = null;

    private HomeData data = null;
    private InventoryData invdata = null;
    private BungeeListener bungee = null;

    @Override
    public void onEnable() {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel( this, "BungeeCord");
        // Load Configfiles
        this.configfile = new ConfigReader().readConfig();
        this.deconfig = new ConfigReader().readDEConfig();
        this.enconfig = new ConfigReader().readENConfig();
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
        this.bungee = new BungeeListener();
        this.data = new HomeData(db, bungee);
        this.invdata = new InventoryData(db);

        // Plugin startup logic
        this.getCommand("sethome").setExecutor(new setHomeCommand(data));
        this.getCommand("homes").setExecutor(new HomesCommand(invdata, data));
        this.getCommand("delhome").setExecutor(new delHomeCommand(data));
        this.getCommand("home").setExecutor(new HomeCommand(data));
        this.getServer().getPluginManager().registerEvents(new InventoryListener(data), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
