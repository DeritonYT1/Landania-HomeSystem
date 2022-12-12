package de.deriton.home_system_api;

import org.bukkit.entity.Player;

import java.util.Locale;

public class LanguageData {

    //Get Config Values to set the Plugin Messages easier!
    public String getLanguageString(Player p, String Languagetype) {
        if(p.locale() == Locale.ENGLISH) {
            return "en_us";
        } else if(p.locale() == Locale.GERMAN) {
            return "de_de";
        }
        return "en_us";
    }
}
