package de.deriton.home_system_common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.deriton.home_system_common.ConfigCreators.ConfigCreator;
import de.deriton.home_system_common.ConfigCreators.DELanguageCreator;
import de.deriton.home_system_common.ConfigCreators.ENLanguageCreator;
import lombok.Getter;

import java.io.*;

@Getter
public class ConfigReader {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final File ConfigFile = new File("plugins//HomeSystem//config.json");
    private final File DE_Language_File = new File("plugins//HomeSystem//locales//de.json");
    private final File EN_Language_File = new File("plugins//HomeSystem//locales//en.json");

    public void saveConfig() {

        ConfigCreator config = new ConfigCreator("localhost", 3306, "root", "", "HomeSystem", "Homes", true);
        DELanguageCreator deconfig = new DELanguageCreator("Deine Homes!", "Alle Homes löschen!", "Dein Home wurde erfolgreich gesetzt!", "Dein Home wurde erfolgreich gelöscht!", "Der amgegebene Home existiert nicht!", "Du hast nicht genügend Rechte um diesen Befehl auszuführen");
        ENLanguageCreator enconfig = new ENLanguageCreator("Your Homes!", "Delete all Homes!", "Your Home got set successfully!", "Your home got deleted successfully!", "The specified home does not exist!", "You do not have enough permissions to run this command!");


        ConfigFile.getParentFile().mkdirs();
        try (FileWriter fileWriter = new FileWriter(ConfigFile)) {
            if(!ConfigFile.exists()) {
                ConfigFile.createNewFile();
            }
            fileWriter.write(gson.toJson(config));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DE_Language_File.getParentFile().mkdirs();
        try (FileWriter fileWriter = new FileWriter(DE_Language_File)) {
            if(!DE_Language_File.exists()) {
                DE_Language_File.createNewFile();
            }
            fileWriter.write(gson.toJson(deconfig));
        } catch (IOException e) {
            e.printStackTrace();
        }

        EN_Language_File.getParentFile().mkdirs();
        try (FileWriter fileWriter = new FileWriter(EN_Language_File)) {
            if(!EN_Language_File.exists()) {
                EN_Language_File.createNewFile();
            }
            fileWriter.write(gson.toJson(enconfig));
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

    public DELanguageCreator readDEConfig() {

        if(!DE_Language_File.exists()) {
            saveConfig();
        }

        try {
            DELanguageCreator deconfig = gson.fromJson(new FileReader(DE_Language_File), DELanguageCreator.class);
            return deconfig;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public ENLanguageCreator readENConfig() {

        if(!EN_Language_File.exists()) {
            saveConfig();
        }

        try {
            ENLanguageCreator enconfig = gson.fromJson(new FileReader(EN_Language_File), ENLanguageCreator.class);
            return enconfig;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
