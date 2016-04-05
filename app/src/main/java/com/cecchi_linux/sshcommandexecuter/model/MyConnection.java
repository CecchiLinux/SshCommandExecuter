package com.cecchi_linux.sshcommandexecuter.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enri on 31/03/2016.
 */
public class MyConnection implements Serializable {

    private static final String JSON_CONNECTION_NAME = "connectionName";
    private static final String JSON_ADDRESS = "address";
    private static final String JSON_PORT = "port";
    private static final String JSON_USER_NAME = "userName";
    private static final String JSON_USER_PASSWORD = "userPassword";
    private static final String JSON_COMMANDS_LIST = "commandsList";

    private String name;
    private String address;
    private int port;
    private String userName;
    private String userPassword;
    private List<Command> commands = new ArrayList<>();;

    public MyConnection(String name, String address, int port, String userName, String userPassword, List<Command> commands) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.userName = userName;
        this.userPassword = userPassword;
        if(commands != null){
            this.commands = commands;
        }
    }

    public MyConnection(JSONObject jsonConnection) throws NullPointerException, JSONException {
        if (jsonConnection == null)
            throw new NullPointerException("JSONObject can't be null");
        setConnectionName(jsonConnection.getString(JSON_CONNECTION_NAME));
        setAddress(jsonConnection.getString(JSON_ADDRESS));
        setPort(jsonConnection.getInt(JSON_PORT));
        setUserName(jsonConnection.getString(JSON_USER_NAME));
        setUserPassword(jsonConnection.getString(JSON_USER_PASSWORD));

        JSONArray jsonArray = jsonConnection.getJSONArray(JSON_COMMANDS_LIST);
        for (int i=0; i<jsonArray.length(); i++){
            JSONObject jsonCommand = jsonArray.getJSONObject(i);
            if (jsonCommand != null) {
                try {
                    Command command = new Command(jsonCommand);
                    this.addCommand(command);

                } catch (Exception e) {
                    Log.e("loadConnection", "error to parse command", e);
                }
            }
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

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_CONNECTION_NAME, this.name);
        json.put(JSON_ADDRESS, this.address);
        json.put(JSON_PORT, this.port);
        json.put(JSON_USER_NAME, this.address);
        json.put(JSON_USER_PASSWORD, this.address);

        JSONArray jsonArray = new JSONArray();
        for (int i=0; i<this.commands.size(); i++){
            JSONObject objCommand = this.commands.get(i).toJSONObject();
            jsonArray.put(objCommand);
        }
        json.put(JSON_COMMANDS_LIST, jsonArray);
        return json;
    }
}
