package de.deriton.home_system_spigot.Commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.deriton.home_system_api.DBConnector;
import de.deriton.home_system_api.HomeData;
import de.deriton.home_system_api.LanguageData;
import de.deriton.home_system_common.ConfigCreators.ConfigCreator;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.net.ProxySelector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class setHomeCommand implements CommandExecutor {

    private HomeData data = null;
    private LanguageData langdata = null;

    public setHomeCommand(HomeData data, LanguageData langdata) {
        super();
        this.data = data;
        this.langdata = langdata;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        if(args.length == 1) {
            // Check if sender is a Player
            if(!(sender instanceof Player)) {
                sender.sendMessage("Nur Spieler können diesen Command ausführen!");
                return false;
            }
            // Get Player, his Location and save Home Data in Database
            Location loc = p.getLocation();
            try {
                //Sets or Replaces Home of User
                data.setHome(p.getUniqueId().toString(), args[0], p.getWorld().getName(), "Test", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                p.sendMessage("Dein Home '" + args[0] + "' wurde erfolgreich gesetzt!");
                System.out.println(langdata.getLanguageString(p, "Test"));
                try {
                    System.out.println(data.getHomes(p.getUniqueId().toString()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            p.sendMessage("Falscher Syntax! Bitte benutze /sethome [Name]");
        }
        return true;
    }
}
