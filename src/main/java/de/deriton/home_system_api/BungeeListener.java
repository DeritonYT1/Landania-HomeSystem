package de.deriton.home_system_api;

import de.deriton.home_system_common.HomeSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class BungeeListener {

    private Plugin plugin = HomeSystem.getPlugin(HomeSystem.class);

    public void changeServer(Player p, String Server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(Server);
        } catch (IOException e) {
            e.printStackTrace();
        }

        p.sendPluginMessage(plugin(), "BungeeCord", b.toByteArray());
    }

    private Plugin plugin() {
        return plugin;
    }


}
