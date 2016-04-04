package com.cecchi_linux.sshcommandexecuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cecchi_linux.sshcommandexecuter.model.Command;
import com.cecchi_linux.sshcommandexecuter.model.MyConnection;
import com.cecchi_linux.sshcommandexecuter.utils.CommandAdapter;
import com.cecchi_linux.sshcommandexecuter.utils.ConnectionAdapter;
import com.cecchi_linux.sshcommandexecuter.utils.DataSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CommandsActivity extends AppCompatActivity {

    static final int ADD_COMMAND = 998;  // The request code
    private String connectionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CommandsActivity.this, AddCommandActivity.class);
                startActivityForResult(i, ADD_COMMAND);
            }
        });

        Intent myIntent = getIntent(); // gets the previously created intent
        this.connectionName = myIntent.getStringExtra("connectionName");

        ListView listView = (ListView)findViewById(R.id.listViewCommands);
        List commandsList = DataSingleton.getInstance().getCommands(connectionName);
        
        CommandAdapter adapter = new CommandAdapter(this, R.layout.command_row_adapter, commandsList);
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {
                Command command = (Command) adapter.getItemAtPosition(position);
                Intent intentCommands = new Intent(CommandsActivity.this, CommandResultActivity.class);
                intentCommands.putExtra("connectionName", connectionName);
                intentCommands.putExtra("commandName", command.getCommandName());
                startActivity(intentCommands);
            }
        };
        listView.setOnItemClickListener(clickListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_COMMAND) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    String name = data.getStringExtra("name");
                    String command = data.getStringExtra("command");
                    DataSingleton.getInstance().addCommand(new Command(name, command), connectionName);
                }
            }
        }
    }

}
