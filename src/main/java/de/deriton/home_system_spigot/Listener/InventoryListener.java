package de.deriton.home_system_spigot.Listener;

import de.deriton.home_system_api.HomeData;
import de.deriton.home_system_api.InventoryData;
import de.deriton.home_system_common.ConfigCreators.MessageConfigCreator;
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
    private MessageConfigCreator msgdata = null;

    public InventoryListener(HomeData data, MessageConfigCreator msgdata) {
        super();
        this.data = data;
        this.msgdata = msgdata;
    }

    //Listener to assign functions to GUI Items
    @EventHandler
    public void onInvClick(InventoryClickEvent event) throws SQLException {
        //Check if he is in a Inventory
        if(event.getInventory() == null) return;
        //Check if he is in the Home GUI
        if(event.getView().getTitle().equalsIgnoreCase("§a" + msgdata.getGui_title())) {
            if(event.getCurrentItem() == null) return;
            Player p = (Player) event.getWhoClicked();
            int s = event.getSlot();
            ItemMeta tmp_meta;
            ItemStack tmp_itemstack;

            //Delete All Homes Slot
            if(s == 49 ) {
                data.deleteallhomes(p.getUniqueId().toString());
                p.sendMessage(msgdata.getMsg_deleteall());
                p.closeInventory();
            //Homepoint "Slots"
            } else if(event.getCurrentItem().getType() == Material.PAPER) {
                String HomeName = event.getCurrentItem().getItemMeta().getDisplayName();
                data.getHome(p.getUniqueId().toString(),HomeName.substring(2), p);
            }
            event.setCancelled(true);
        }
    }
}
