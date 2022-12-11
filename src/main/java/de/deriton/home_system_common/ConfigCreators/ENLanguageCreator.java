package de.deriton.home_system_common.ConfigCreators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ENLanguageCreator {

    private String gui_title;
    private String gui_deleteall;
    private String cmd_sethome;
    private String cmd_delhome;
    private String cmd_home_notexist;
    private String no_permission;
}
