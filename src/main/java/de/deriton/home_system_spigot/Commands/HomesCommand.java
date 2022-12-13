package de.deriton.home_system_spigot.Commands;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import de.deriton.home_system_api.HomeData;
import de.deriton.home_system_common.ConfigCreators.MessageConfigCreator;
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
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import de.deriton.home_system_api.InventoryData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


public class HomesCommand implements CommandExecutor {

    private HomeData data = null;
    private InventoryData invdata = null;
    private MessageConfigCreator msgdata = null;


    public HomesCommand(InventoryData invdata, HomeData data, MessageConfigCreator msgdata) {
        super();
        this.data = data;
        this.invdata = invdata;
        this.msgdata = msgdata;
    }

    private void createInventory(Player p) throws SQLException {

        //Navigation Item Creation
        Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)null, 54, "§a" + msgdata.getGui_title());
        ItemStack deleteall_homes = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        SkullMeta deleteall_homesMetaa = (SkullMeta) deleteall_homes.getItemMeta();
        deleteall_homesMetaa.setPlayerProfile(Bukkit.createProfile(UUID.randomUUID(), null));
        PlayerProfile playerProfile = deleteall_homesMetaa.getPlayerProfile();
        playerProfile.setProperties(Collections.singleton(new ProfileProperty("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==")));
        deleteall_homesMetaa.setPlayerProfile(playerProfile);
        deleteall_homesMetaa.setDisplayName("§c" + msgdata.getGui_deleteall());
        deleteall_homes.setItemMeta(deleteall_homesMetaa);

        // Get Homepoints from Player and set Homepoint Items
        int rowCount = data.getHomes(p.getUniqueId().toString()).size();
        ArrayList<String> Name = data.getHomes(p.getUniqueId().toString());
        for(int i = 0; i < rowCount; i++) {
            invdata.setInvItem(i,inv,Name.get(i), Material.PAPER, true);
        }
        // Set Navigation Items

        invdata.setInvItem(45, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        invdata.setInvItem(46, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        invdata.setInvItem(47, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        invdata.setInvItem(48, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        inv.setItem(49, deleteall_homes);
        invdata.setInvItem(50, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        invdata.setInvItem(51, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        invdata.setInvItem(52, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        invdata.setInvItem(53, inv, "", Material.GRAY_STAINED_GLASS_PANE, false);
        //open Inventory
        p.openInventory(inv);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        //Check if Player isn't a console
        if(!(sender instanceof Player)) {
            sender.sendMessage(msgdata.getNonPlayer_usage());
            return false;
        }
        Player p = (Player) sender;
        //Check Permission
        if(p.hasPermission("landania.homesystem.homes") || p.isOp()) {
            try {
                createInventory((Player) sender);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            p.sendMessage(msgdata.getNo_permission());
            return true;
        }
    }
}
