package com.cecchi_linux.sshcommandexecuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;


//added

import com.cecchi_linux.sshcommandexecuter.model.Command;
import com.cecchi_linux.sshcommandexecuter.model.MyConnection;
import com.cecchi_linux.sshcommandexecuter.utils.ConnectionAdapter;
import com.cecchi_linux.sshcommandexecuter.utils.DataSingleton;


import java.util.ArrayList;
import java.util.List;




public class MainActivity extends AppCompatActivity {

    static final int ADD_CONNECTION = 999;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddConnectionActivity.class);
                startActivityForResult(i, ADD_CONNECTION);
            }
        });

//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {

//        });

        ListView listView = (ListView)findViewById(R.id.listViewConnections);
        List connectionsList = DataSingleton.getInstance().getConnections();

        //DEBUG
        ArrayList<Command> commandsDebug = new ArrayList<>();
        //commandsDebug.add(new Command("spegni","sudo shutdown -h now"));
        commandsDebug.add(new Command("spegni","echo \"ciao\""));
        commandsDebug.add(new Command("errore","fefe"));
        connectionsList.add(new MyConnection("conn1","192.168.0.110",22,"pi","raspberry",commandsDebug));
        ConnectionAdapter adapter = new ConnectionAdapter(this, R.layout.connection_row_adapter, connectionsList);
        //DEBUG END

        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {
                MyConnection connection = (MyConnection) adapter.getItemAtPosition(position);
                Intent intentCommands = new Intent(MainActivity.this, CommandsActivity.class);
                intentCommands.putExtra("connectionName", connection.getConnectionName());
                startActivity(intentCommands);
            }
        };
        listView.setOnItemClickListener(clickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CONNECTION) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    String name = data.getStringExtra("name");
                    String address = data.getStringExtra("address");
                    String port = data.getStringExtra("port");
                    String username = data.getStringExtra("username");
                    String password = data.getStringExtra("password");
                    DataSingleton.getInstance().addConnection(new MyConnection(name, address, Integer.parseInt(port), username, password, null));
                }
            }
        }
    }


}
