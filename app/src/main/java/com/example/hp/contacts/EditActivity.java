package com.example.hp.contacts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends ActionBarActivity {

    Context context = EditActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final EditText etEditName=(EditText)findViewById(R.id.etEditName);
        final EditText etEditTel=(EditText)findViewById(R.id.etEditTel);
        final EditText etEditEmail=(EditText)findViewById(R.id.etEditEmail);

        Intent intent = getIntent();//获取上一个intent
        final String info[] =intent.getStringArrayExtra("info");//取得传过来的参数
        etEditName.setText(info[0]);
        etEditTel.setText(info[1]);
        etEditEmail.setText(info[2]);

        findViewById(R.id.btnEditSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flg = editPeople(info[1], etEditName.getText().toString(),
                        etEditTel.getText().toString(), etEditEmail.getText().toString());
                Intent intent = new Intent();
                intent.setFlags(flg);
                setResult(RESULT_OK , intent);
                finish();

            }
        });

        findViewById(R.id.btnEditCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public int editPeople(String name,String people_name,String people_tel,String people_email){
        PeopleDBAdapter personDBAdapter = new PeopleDBAdapter(context);
        personDBAdapter.openDB();
        return personDBAdapter.update(name,people_name,people_tel,people_email);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
