package de.deriton.home_system_spigot.Commands;

import de.deriton.home_system_api.HomeData;
import de.deriton.home_system_common.ConfigCreators.MessageConfigCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class delHomeCommand implements CommandExecutor {

    private HomeData data = null;
    private MessageConfigCreator msgdata = null;


    public delHomeCommand(HomeData data, MessageConfigCreator msgdata) {
        super();
        this.data = data;
        this.msgdata = msgdata;

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //Check if Player isn't a console
        if(!(sender instanceof Player)) {
            sender.sendMessage(msgdata.getNonPlayer_usage());
            return false;
        }
        Player p = (Player) sender;
        //Check Permission
        if(p.hasPermission("landania.homesystem.delhome") || p.isOp()) {
            try {
                //Check if the Home exists
                if(data.deleteHome(p.getUniqueId().toString(), args[0], p)) {
                    p.sendMessage(msgdata.getCmd_delhome().replace("[HomeName]", args[0]));
                }
            } catch (SQLException e) {
                p.sendMessage(msgdata.getCmd_home_notexist());
            }
            return true;
        } else {
            p.sendMessage(msgdata.getNo_permission());
            return true;
        }
    }
}
