package com.cecchi_linux.sshcommandexecuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class AddConnectionActivity extends AppCompatActivity {

    private EditText editText_name;
    private EditText editText_address;
    private EditText editText_port;
    private EditText editText_username;
    private EditText editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_connection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.editText_name = (EditText)findViewById(R.id.input_name);
        this.editText_address = (EditText)findViewById(R.id.input_address);
        this.editText_port = (EditText)findViewById(R.id.input_port);
        this.editText_username = (EditText)findViewById(R.id.input_username);
        this.editText_password = (EditText)findViewById(R.id.input_password);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            Intent data = new Intent();
            data.putExtra("name", editText_name.getText().toString());
            data.putExtra("address", editText_address.getText().toString());
            data.putExtra("port",editText_port.getText().toString());
            data.putExtra("username",editText_username.getText().toString());
            data.putExtra("password",editText_password.getText().toString());
            setResult(RESULT_OK,data);
            AddConnectionActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
