package de.deriton.home_system_common.ConfigCreators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageConfigCreator {
    //var creation of config inputs
    private String gui_title;
    private String gui_HomeItemDescriptionLine1;
    private String gui_HomeItemDescriptionLine2;
    private String gui_HomeItemDescriptionLine3;
    private String gui_deleteall;
    private String msg_deleteall;
    private String msg_MaximumHomes;
    private String cmd_sethome;
    private String cmd_delhome;
    private String cmd_home_notexist;
    private String no_permission;
    private String wrong_usage;
    private String NonPlayer_usage;

}
