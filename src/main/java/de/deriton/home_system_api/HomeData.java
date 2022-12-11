package de.deriton.home_system_api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeData {

    private DBConnector cmddb = null;
    private BungeeListener bungee = null;

    public HomeData(DBConnector db, BungeeListener bungee) {
        this.cmddb = db;
        this.bungee = bungee;
    }


    public void setHome(String uuid, String HomeName, String HomeWorld, String HomeServer, double CoordinateX, double CoordinateY, double CoordinateZ) throws SQLException {
        PreparedStatement stmt = cmddb.prepare("REPLACE INTO homes (UUID, HomeName, HomeWorld, HomeServer, CoordinateX, CoordinateY, CoordinateZ, pinned) VALUES (?, ?,?,?,?,?,?,?)");
        stmt.setString(1, uuid);
        stmt.setString(2, HomeName);
        stmt.setString(3, HomeWorld);
        stmt.setString(4, HomeServer);
        stmt.setDouble(5, CoordinateX);
        stmt.setDouble(6, CoordinateY);
        stmt.setDouble(7, CoordinateZ);
        stmt.setInt(8, 0);
        boolean rs = stmt.execute();
    }

    public void deleteHome(String uuid, String HomeName) throws SQLException {
        PreparedStatement stmt = cmddb.prepare("DELETE FROM homes WHERE uuid = ? AND HomeName = ?");
        stmt.setString(1, uuid);
        stmt.setString(2, HomeName);
        boolean rs = stmt.execute();
    }



    public ArrayList<String> getHomes(String uuid) throws SQLException {
        PreparedStatement stmt = cmddb.prepare("SELECT * FROM homes WHERE uuid = ?");
        stmt.setString(1, uuid);
        ResultSet rs = stmt.executeQuery();
        String Homes = new String();
        int rowCount = 0;
        ArrayList<String> Name = new ArrayList<String>();
        while (rs.next()) {
            Name.add(rowCount, rs.getNString("HomeName"));
            rowCount++;
        }
        return Name;
    }

    public void getHome(String uuid, String HomeName, Player p) throws SQLException {
        PreparedStatement stmt = cmddb.prepare("SELECT * FROM homes WHERE uuid = ? AND HomeName = ?");
        stmt.setString(1, uuid);
        stmt.setString(2, HomeName);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        Double CoordinateX = rs.getDouble("CoordinateX");
        Double CoordinateY = rs.getDouble("CoordinateY");
        Double CoordinateZ = rs.getDouble("CoordinateZ");
        String targetServer = rs.getString("HomeServer");
        World targetWorld = Bukkit.getWorld(rs.getNString("HomeWorld"));
        Location loc = new Location(targetWorld, CoordinateX, CoordinateY, CoordinateZ);
        System.out.println("Location: " + loc);
        System.out.println("Server: " + targetServer);
        bungee.changeServer(p, targetServer);
        p.teleport(loc);
    }

    public void deleteallhomes(String uuid) throws SQLException {
        PreparedStatement stmt = cmddb.prepare("DELETE FROM homes WHERE uuid = ?");
        stmt.setString(1, uuid);
        boolean rs = stmt.execute();
    }
}
