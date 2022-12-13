package de.deriton.home_system_common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.deriton.home_system_common.ConfigCreators.ConfigCreator;
import de.deriton.home_system_common.ConfigCreators.MessageConfigCreator;
import lombok.Getter;

import java.io.*;

@Getter
public class ConfigReader {
    //Reads the messages and db Config
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final File ConfigFile = new File("plugins//HomeSystem//config.json");
    private final File MessageConfig = new File("plugins//HomeSystem//messages.json");

    public void saveConfig() {

        ConfigCreator config = new ConfigCreator("localhost", 3306, "root", "", "HomeSystem", "Homes", true);
        MessageConfigCreator msgconfig = new MessageConfigCreator(
                "Deine Homes!",
                "Klicke, um dich zum Home zu teleportieren",
                "",
                "Server: ",
                "Alle Homes löschen!",
                "Du hast erfolgreich alle Homes gelöscht!",
                "Du hast die maximale Anzahl an Homes erreicht! Lösche erst einen um einen neuen zu erstellen!",
                "Dein Home [HomeName] wurde erfolgreich gesetzt!",
                "Dein Home [HomeName] wurde erfolgreich gelöscht!",
                "Der angegebene Home existiert nicht!",
                "Du hast nicht genügend Rechte um diesen Befehl auszuführen",
                "Falsche Benutzung!",
                "Nur Spieler können diesen Command benutzen!");


        ConfigFile.getParentFile().mkdirs();
        try (FileWriter fileWriter = new FileWriter(ConfigFile)) {
            if(!ConfigFile.exists()) {
                ConfigFile.createNewFile();
            }
            fileWriter.write(gson.toJson(config));
        } catch (IOException e) {
            e.printStackTrace();
        }

        MessageConfig.getParentFile().mkdirs();
        try (FileWriter fileWriter = new FileWriter(MessageConfig)) {
            if(!MessageConfig.exists()) {
                MessageConfig.createNewFile();
            }
            fileWriter.write(gson.toJson(msgconfig));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ConfigCreator readConfig () {

        if(!ConfigFile.exists()) {
            saveConfig();
        }

        try {
            ConfigCreator configfile = gson.fromJson(new FileReader(ConfigFile), ConfigCreator.class);
            return configfile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public MessageConfigCreator readMessageConfig() {

        if(!MessageConfig.exists()) {
            saveConfig();
        }

        try {
            MessageConfigCreator msgconfig = gson.fromJson(new FileReader(MessageConfig), MessageConfigCreator.class);
            return msgconfig;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

}
