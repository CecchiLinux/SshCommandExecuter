package com.cecchi_linux.sshcommandexecuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cecchi_linux.sshcommandexecuter.utils.DataSingleton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CommandResultActivity extends AppCompatActivity {

    private String connectionName;
    private String commandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent myIntent = getIntent(); // gets the previously created intent
        this.connectionName = myIntent.getStringExtra("connectionName");
        this.commandName = myIntent.getStringExtra("commandName");
        exec();
    }

    public void exec(){
        try {
            ArrayList<String> result = DataSingleton.getInstance().execCommand(this.connectionName,this.commandName);
            for(String s : result){
                System.out.println(s);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
