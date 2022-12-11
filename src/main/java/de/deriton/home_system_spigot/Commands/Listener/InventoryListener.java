package de.deriton.home_system_spigot.Commands.Listener;

import de.deriton.home_system_api.HomeData;
import de.deriton.home_system_api.InventoryData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;

public class InventoryListener implements Listener {

    private HomeData data = null;

    public InventoryListener(HomeData data) {
        super();
        this.data = data;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) throws SQLException {
        if(event.getInventory() == null) return;
        if(event.getView().getTitle().equalsIgnoreCase("§aDeine Homes")) {
            if(event.getCurrentItem() == null) return;
            Player p = (Player) event.getWhoClicked();
            int s = event.getSlot();
            ItemMeta tmp_meta;
            ItemStack tmp_itemstack;

            if(event.getCurrentItem().getType() == Material.LEGACY_SKULL_ITEM && s == 49 ) {
                data.deleteallhomes(p.getUniqueId().toString());
                p.sendMessage("Du hast erfolgreich alle Homes gelöscht!");
            }
        }
        event.setCancelled(true);
    }
}
