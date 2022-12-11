package de.deriton.home_system_spigot.Commands.Listener;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    public boolean onMove(PlayerMoveEvent event) {

        event.getPlayer().getUniqueId().toString();
        return true;
    }
}
