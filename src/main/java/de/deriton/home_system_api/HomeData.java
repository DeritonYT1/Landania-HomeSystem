package de.deriton.home_system_api;

import de.deriton.home_system_common.ConfigCreators.MessageConfigCreator;
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
    private MessageConfigCreator msgdata = null;

    public HomeData(DBConnector db, MessageConfigCreator msgdata) {
        this.cmddb = db;
        this.msgdata = msgdata;
    }


    public boolean setHome(String uuid, String HomeName, String HomeWorld, double CoordinateX, double CoordinateY, double CoordinateZ, float yaw, float pitch, Player p) throws SQLException {
        PreparedStatement HomeCountstmt = cmddb.prepare("SELECT COUNT(*) AS HomeCount FROM homes WHERE uuid = ?");
        HomeCountstmt.setString(1, uuid);
        ResultSet HomeCountrs = HomeCountstmt.executeQuery();
        HomeCountrs.next();
        //Check if he hasn't reached maximum value
        if(HomeCountrs.getInt("HomeCount") > 44) {
            p.sendMessage(msgdata.getMsg_MaximumHomes());
            return false;
        } else {
            //Replace/Overwrite Home if existing otherwise he's creating one.
            PreparedStatement stmt = cmddb.prepare("REPLACE INTO homes (UUID, HomeName, HomeWorld, CoordinateX, CoordinateY, CoordinateZ, yaw, pitch) VALUES (?,?,?,?,?,?,?,?)");
            stmt.setString(1, uuid);
            stmt.setString(2, HomeName);
            stmt.setString(3, HomeWorld);
            stmt.setDouble(4, CoordinateX);
            stmt.setDouble(5, CoordinateY);
            stmt.setDouble(6, CoordinateZ);
            stmt.setFloat(7, yaw);
            stmt.setFloat(8, pitch);
            boolean rs = stmt.execute();
        }
        return true;
    }

    //Function for deleting one home at a time
    public boolean deleteHome(String uuid, String HomeName, Player p) throws SQLException {
        PreparedStatement checkstmt = cmddb.prepare("SELECT COUNT(*) AS home FROM homes WHERE uuid = ? AND HomeName = ?");
        checkstmt.setString(1, uuid);
        checkstmt.setString(2, HomeName);
        ResultSet checkrs = checkstmt.executeQuery();
        checkrs.next();
        if(checkrs.getInt("home") == 0) {
            p.sendMessage(msgdata.getCmd_home_notexist());
            return false;
        } else {
            PreparedStatement stmt = cmddb.prepare("DELETE FROM homes WHERE uuid = ? AND HomeName = ?");
            stmt.setString(1, uuid);
            stmt.setString(2, HomeName);
            boolean rs = stmt.execute();
        }
        return true;
    }


    //get all Homes of User (e.g. for the Homes Command)
    public ArrayList<String> getHomes(String uuid) throws SQLException {
        PreparedStatement stmt = cmddb.prepare("SELECT * FROM homes WHERE uuid = ? ORDER BY id");
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
        //Gets the Home of the Player
        PreparedStatement stmt = cmddb.prepare("SELECT * FROM homes WHERE uuid = ? AND HomeName = ?");
        stmt.setString(1, uuid);
        stmt.setString(2, HomeName);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        //Get set home values from database
        Double CoordinateX = rs.getDouble("CoordinateX");
        Double CoordinateY = rs.getDouble("CoordinateY");
        Double CoordinateZ = rs.getDouble("CoordinateZ");
        Float yaw = rs.getFloat("yaw");
        Float pitch = rs.getFloat("pitch");
        //Teleports player with location
        World targetWorld = Bukkit.getWorld(rs.getNString("HomeWorld"));
        Location loc = new Location(targetWorld, CoordinateX, CoordinateY, CoordinateZ, yaw, pitch);
        p.teleport(loc);
    }

    //Function for deleting all Homes
    public void deleteallhomes(String uuid) throws SQLException {
        PreparedStatement stmt = cmddb.prepare("DELETE FROM homes WHERE uuid = ?");
        stmt.setString(1, uuid);
        boolean rs = stmt.execute();
    }
}
