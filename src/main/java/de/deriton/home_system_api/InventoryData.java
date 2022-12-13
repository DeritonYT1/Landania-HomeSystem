package de.deriton.home_system_api;

import javax.annotation.Nullable;

import de.deriton.home_system_common.ConfigCreators.MessageConfigCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryData {

    private DBConnector cmddb = null;
    private HomeData data = null;
    private MessageConfigCreator msgdata = null;

    public InventoryData(DBConnector db, MessageConfigCreator msgdata) {
        this.cmddb = db;
        this.msgdata = msgdata;
    }

    public @Nullable ItemMeta getItemMeta() {
        return null;
    }

    public void setInvItem(int aPos, Inventory aInv, String Name, Material Mat, boolean Homepoints) {
        //Set Homepoint Items
        ItemStack tmp_itemstack;
        String tmp_displayname;
        tmp_itemstack = new ItemStack(Mat);
        tmp_displayname = Name;
        ItemMeta tmp_meta = tmp_itemstack.getItemMeta();
        tmp_meta.setDisplayName("§e" + tmp_displayname);
        if(Homepoints == true) {
            ArrayList<String> lorelist = new ArrayList<String>();
            lorelist.add("§7" + msgdata.getGui_HomeItemDescriptionLine1());
            lorelist.add("§7" + msgdata.getGui_HomeItemDescriptionLine2());
            lorelist.add("§7" + msgdata.getGui_HomeItemDescriptionLine3());
            tmp_meta.setLore(lorelist);
        }
        tmp_itemstack.setItemMeta(tmp_meta);
        aInv.setItem(aPos, tmp_itemstack);
    }

    private int setItemMeta(String string) {
        return 0;
    }

    private int setDisplayName(String string) {
        return 0;
    }


}
