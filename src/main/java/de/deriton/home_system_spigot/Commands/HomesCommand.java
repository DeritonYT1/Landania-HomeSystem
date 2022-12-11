package de.deriton.home_system_spigot.Commands;

import de.deriton.home_system_api.HomeData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import de.deriton.home_system_api.InventoryData;

import java.sql.SQLException;
import java.util.ArrayList;


public class HomesCommand implements CommandExecutor {

    private HomeData data = null;
    private InventoryData invdata = null;

    public HomesCommand(InventoryData invdata, HomeData data) {
        super();
        this.data = data;
        this.invdata = invdata;
    }

    private void createInventory(Player p) throws SQLException {

        //Navigation Item Creation
        Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)null, 54, "§aDeine Homes");
        ItemStack deleteall_homes = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        SkullMeta deleteall_homesMeta = (SkullMeta) deleteall_homes.getItemMeta();
        deleteall_homesMeta.setDisplayName("§cAlle Homes löschen!");
        deleteall_homesMeta.setOwner("I;1590668272,1381189552,-1538418164,-1841266247");
        deleteall_homes.setItemMeta(deleteall_homesMeta);

        // Get Homepoints from Player and set Homepoint Items
        int rowCount = data.getHomes(p.getUniqueId().toString()).size();
        ArrayList<String> Name = data.getHomes(p.getUniqueId().toString());
        for(int i = 0; i < rowCount; i++) {
            invdata.setInvItem(i,inv,Name.get(i), Material.PAPER, true);
        }
        // Set Navigation Items

        inv.setItem(45, deleteall_homes);
        invdata.setInvItem(46, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        invdata.setInvItem(47, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        invdata.setInvItem(48, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        inv.setItem(49, deleteall_homes);
        invdata.setInvItem(50, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        invdata.setInvItem(51, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        invdata.setInvItem(52, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        inv.setItem(53, deleteall_homes);
        //open Inventory
        p.openInventory(inv);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        try {
            createInventory((Player) sender);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
