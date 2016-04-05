package com.cecchi_linux.sshcommandexecuter.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Enri on 31/03/2016.
 */
public class Command implements Serializable{

    private static final String JSON_COMMAND_NAME = "commandName";
    private static final String JSON_COMMAND_STR = "commandStr";

    private String name;
    private String strCommand;

    public Command(String name, String strCommand) {
        this.name = name;
        this.strCommand = strCommand;
    }

    public Command(JSONObject jsonCommand) throws NullPointerException, JSONException {
        if (jsonCommand == null)
            throw new NullPointerException("JSONObject can't be null");
        setCommandName(jsonCommand.getString(JSON_COMMAND_NAME));
        setStrCommand(jsonCommand.getString(JSON_COMMAND_STR));
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

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_COMMAND_NAME, this.name);
        json.put(JSON_COMMAND_STR, this.strCommand);

        return json;
    }
}
