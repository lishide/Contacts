package com.example.hp.contacts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends ActionBarActivity {

    Context context = AddActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText etAddName=(EditText)findViewById(R.id.etAddName);
        final EditText etAddTel=(EditText)findViewById(R.id.etAddTel);
        final EditText etAddEmail=(EditText)findViewById(R.id.etAddEmail);

        findViewById(R.id.ibtnAddHead).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.btnAddSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long flg = insert(etAddName.getText().toString(),
                        etAddTel.getText().toString(),
                        etAddEmail.getText().toString());
                Intent intent = new Intent();
                intent.setFlags((int)flg);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        findViewById(R.id.btnAddCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public long insert(/*int img , */String people_name,String people_tel,String people_email){
        PeopleDBAdapter peopleDBAdapter = new PeopleDBAdapter(context);
        peopleDBAdapter.openDB();
        return peopleDBAdapter.insert(people_name, people_tel, people_email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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
}
