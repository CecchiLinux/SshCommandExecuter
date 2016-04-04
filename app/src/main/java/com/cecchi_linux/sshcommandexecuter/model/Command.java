package com.cecchi_linux.sshcommandexecuter.model;

/**
 * Created by Enri on 31/03/2016.
 */
public class Command {
    private String name;
    private String strCommand;

    public Command(String name, String strCommand) {
        this.name = name;
        this.strCommand = strCommand;
    }

    public String getCommandName() {
        return name;
    }

    public void setCommandName(String name) {
        this.name = name;
    }

    public String getStrCommand() {
        return strCommand;
    }

    public void setStrCommand(String strCommand) {
        this.strCommand = strCommand;
    }
}
