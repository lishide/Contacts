package com.example.hp.contacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PeopleDetailsActivity extends ActionBarActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_details);
        context = PeopleDetailsActivity.this;

        ImageView imageView = (ImageView) findViewById(R.id.ivHead);
        TextView tvname = (TextView) findViewById(R.id.tvName);
        final TextView tvtel = (TextView) findViewById(R.id.tvTel);
        TextView tvemail = (TextView) findViewById(R.id.tvEmail);

        Intent intent = getIntent();
//        finil String img=intent.getStringExtra("head");
        tvname.setText(intent.getStringExtra("name"));
        tvtel.setText(intent.getStringExtra("tel"));
        tvemail.setText(intent.getStringExtra("email"));

        findViewById(R.id.btnTel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvtel.getText().toString()));
                startActivity(intent);
            }
        });

        findViewById(R.id.btnSMS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + tvtel.getText().toString()));
//                intent.setAction(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("smsto:5554"));
                intent.putExtra("sms_body", "Hello,");//把"welcome to..."定义到变量sms_body,这里sms_body不能改为其他的变量名
                startActivity(intent);

            }
        });
    }

}

