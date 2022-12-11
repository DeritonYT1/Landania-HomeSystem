package de.deriton.home_system_api;

import javax.annotation.Nullable;
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

    public InventoryData(DBConnector db) {
        this.cmddb = db;
    }

    public Inventory setInventoryItem(Player p) throws SQLException {

        Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)null, 54, "§aDeine Homes");
        ItemStack deleteall_homes = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        SkullMeta deleteall_homesMeta = (SkullMeta) deleteall_homes.getItemMeta();
        deleteall_homesMeta.setDisplayName("§cAlle Homes löschen!");
        deleteall_homesMeta.setOwner("MHF_ArrowRight");
        deleteall_homes.setItemMeta(deleteall_homesMeta);

        ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta placeholder_Meta = (ItemMeta) placeholder.getItemMeta();
        placeholder_Meta.setDisplayName(" ");
        placeholder.setItemMeta(placeholder_Meta);

        try {
            System.out.println(data.getHomes(p.getUniqueId().toString()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        inv.setItem(45, deleteall_homes);
        inv.setItem(46, placeholder);
        inv.setItem(47, placeholder);
        inv.setItem(48, placeholder);
        inv.setItem(49, deleteall_homes);
        inv.setItem(50, placeholder);
        inv.setItem(51, placeholder);
        inv.setItem(52, placeholder);
        inv.setItem(53, deleteall_homes);
        return inv;
    }

    public @Nullable ItemMeta getItemMeta() {
        return null;
    }

    public void setInvItem(int aPos, Inventory aInv, String Name, Material Mat, boolean Homepoints) {
        ItemStack tmp_itemstack;
        String tmp_displayname;
        tmp_itemstack = new ItemStack(Mat);
        tmp_displayname = Name;
        ItemMeta tmp_meta = tmp_itemstack.getItemMeta();
        tmp_meta.setDisplayName("§e" + tmp_displayname);
        if(Homepoints == true) {
            ArrayList<String> lorelist = new ArrayList<String>();
            lorelist.add("§7Klicke, um dich zum Home zu teleportieren");
            lorelist.add("");
            lorelist.add("§7Server: ");
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
