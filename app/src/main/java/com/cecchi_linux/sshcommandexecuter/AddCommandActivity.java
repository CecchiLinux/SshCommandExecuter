package com.cecchi_linux.sshcommandexecuter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddCommandActivity extends AppCompatActivity {

    private EditText editText_name;
    private EditText editText_command;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_command);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.editText_name = (EditText)findViewById(R.id.input_name);
        this.editText_command = (EditText)findViewById(R.id.input_command);
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
            data.putExtra("command", editText_command.getText().toString());
            setResult(RESULT_OK,data);
            AddCommandActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
