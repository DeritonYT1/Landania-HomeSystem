package de.deriton.home_system_spigot.Commands;

import de.deriton.home_system_api.HomeData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class HomeCommand implements CommandExecutor {


    private HomeData data = null;

    public HomeCommand(HomeData data) {
        super();
        this.data = data;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            Player p = (Player) sender;
            try {
                data.getHome(p.getUniqueId().toString(),args[0], p);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
