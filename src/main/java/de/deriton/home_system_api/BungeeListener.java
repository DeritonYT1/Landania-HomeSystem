package de.deriton.home_system_api;

import de.deriton.home_system_common.HomeSystem;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

    /*@Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if(!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String SubServer = in.readUTF();
    }
     */
}
