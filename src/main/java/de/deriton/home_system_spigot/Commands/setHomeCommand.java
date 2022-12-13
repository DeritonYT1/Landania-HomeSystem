package de.deriton.home_system_spigot.Commands;

import de.deriton.home_system_api.HomeData;
import de.deriton.home_system_common.ConfigCreators.MessageConfigCreator;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class setHomeCommand implements CommandExecutor {

    private HomeData data = null;
    private MessageConfigCreator msgdata = null;

    public setHomeCommand(HomeData data, MessageConfigCreator msgdata) {
        super();
        this.data = data;
        this.msgdata = msgdata;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            // Check if sender is a Player
            if (!(sender instanceof Player)) {
                sender.sendMessage(msgdata.getNonPlayer_usage());
                return false;
            }
            Player p = (Player) sender;
            //Check Permission
            if(p.hasPermission("landania.homesystem.sethome") || p.isOp()) {
                if (args.length == 1) {

                    // Get Player, his Location and save Home Data in Database
                    Location loc = p.getLocation();
                    //Sets or Replaces Home of User
                    try {
                        //sethome Function w/ overwrite
                        if (data.setHome(p.getUniqueId().toString(), args[0], p.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), loc.getPitch(), p)) {
                            p.sendMessage(msgdata.getCmd_sethome().replace("[HomeName]", args[0]));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                } else {
                    p.sendMessage(msgdata.getWrong_usage());
                }
                return true;
            } else {
                p.sendMessage(msgdata.getNo_permission());
                return true;
            }
    }
}
