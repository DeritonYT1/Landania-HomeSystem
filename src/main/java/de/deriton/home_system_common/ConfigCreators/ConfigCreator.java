package de.deriton.home_system_common.ConfigCreators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfigCreator {
    //var creation of config inputs
    private String host;
    private int port;
    private String user;
    private String password;
    private String database;
    private String tablename;
    private boolean defaultconfig;
}

