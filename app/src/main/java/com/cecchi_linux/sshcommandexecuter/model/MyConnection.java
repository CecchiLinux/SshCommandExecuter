package com.cecchi_linux.sshcommandexecuter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enri on 31/03/2016.
 */
public class MyConnection {
    private String name;
    private String address;
    private int port;
    private String userName;
    private String userPassword;
    private List<Command> commands;

    public MyConnection(String name, String address, int port, String userName, String userPassword, List<Command> commands) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.userName = userName;
        this.userPassword = userPassword;
        if(commands == null){
            this.commands = new ArrayList<>();
        } else {
            this.commands = commands;
        }
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public void addCommand(Command command){
        this.commands.add(command);
    }

    public void removeCommand(Command command){
        //TODO remove command
    }

    public String getConnectionName() {
        return name;
    }

    public void setConnectionName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
