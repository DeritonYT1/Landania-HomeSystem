package de.deriton.home_system_api;

import de.deriton.home_system_common.ConfigCreators.ConfigCreator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnector {


    private Connection con = null;

    public DBConnector() {
    }

    public boolean connect(ConfigCreator cfg) {
        try {
            //Creates Database if not exists & Opens Connection to the database
            con = DriverManager.getConnection("jdbc:mysql://" + cfg.getHost() + ":" + cfg.getPort() + "/"  + "?autoReconnect=true&allowMultiQueries=true", cfg.getUser(), cfg.getPassword());
            String sql = "CREATE DATABASE IF NOT EXISTS `" + cfg.getDatabase() + "`;";
            sql += "CREATE TABLE IF NOT EXISTS `" + cfg.getDatabase() + "`.`" + cfg.getTablename() + "`(`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                    "`uuid` VARCHAR(36) NOT NULL COLLATE 'utf8mb4_general_ci',\n" +
                    "`HomeName` VARCHAR(32) NOT NULL COLLATE 'utf8mb4_general_ci',\n" +
                    "`HomeWorld` VARCHAR(32) NOT NULL COLLATE 'utf8mb4_general_ci', \n" +
               //   "`HomeServer` VARCHAR(32) NOT NULL COLLATE 'utf8mb4_general_ci',\n" +
                    "`CoordinateX` DECIMAL(12,6) NOT NULL DEFAULT '0.000000',\n" +
                    "`CoordinateY` DECIMAL(12,6) NOT NULL DEFAULT '0.000000',\n" +
                    "`CoordinateZ` DECIMAL(12,6) NOT NULL DEFAULT '0.000000',\n" +
                    "`yaw` FLOAT NOT NULL DEFAULT '0',\n" +
                    "`pitch` FLOAT NOT NULL DEFAULT '0',\n" +
                    "`createdAt` TIMESTAMP NOT NULL DEFAULT current_timestamp(),\n" +
                    "PRIMARY KEY (`id`) USING BTREE,\n" +
                    "UNIQUE INDEX `uuidHome` (`uuid`, `HomeName`) USING BTREE \n" +
                    ")\n" +
                    "COLLATE='utf8mb4_general_ci'\n" +
                    "ENGINE=InnoDB;";
            sql += "USE `" + cfg.getDatabase() + "`";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Datenbankverbindung verloren!");
            return false;
        }
    }

    public void disconnect() {
        //Disconnect from DB
        try {
            this.con.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement prepare(String stmt) {
        //prepare Statement
        try {
            return this.con.prepareStatement(stmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return this.con;
    }


}
