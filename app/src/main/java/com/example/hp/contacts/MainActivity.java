package com.example.hp.contacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends ActionBarActivity implements MyAdapter.Callback{

    private ListView listView;
    Context context;
    MyAdapter myAdapter;
    final static int CONTEXTMENU1=1;
    int positionTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;

        DBUtils dbUtils=new DBUtils(context);
        dbUtils.initDB();
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        listView=(ListView)findViewById(R.id.list);
        myAdapter=new MyAdapter(context,this);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PeopleBean peopleBean = myAdapter.getList().get(position);
                positionTemp=position;
                Toast.makeText(MainActivity.this, "点击查看详情",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PeopleDetailsActivity.class);
                intent.putExtra("head", peopleBean.getImageId());
                intent.putExtra("name", peopleBean.getPeople_name());
                intent.putExtra("tel", peopleBean.getPeople_tel());
                intent.putExtra("email", peopleBean.getPeople_email());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                registerForContextMenu(listView);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        // 设置Title
        menu.setHeaderTitle("快捷菜单");
        menu.add(0, CONTEXTMENU1, 0, "删除");
        menu.add(0, CONTEXTMENU1+1, 1, "编辑");
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
        int pos=menuInfo.position;
        if (item.getItemId()==CONTEXTMENU1){
            new AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage("确认删除吗？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
                            int pos=menuInfo.position;
                            PeopleDBAdapter peopleDBAdapter = new PeopleDBAdapter(context);
                            peopleDBAdapter.openDB();
                            peopleDBAdapter.delete_byName(myAdapter.getList().get(pos).getPeople_name());

                            myAdapter=new MyAdapter(context,MainActivity.this);
                            listView.setAdapter(myAdapter);
//                            myAdapter.getList().remove(pos);
//                            myAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "该联系人已删除！", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        }
        if (item.getItemId()==CONTEXTMENU1+1){
            PeopleBean peopleBean =  myAdapter.getList().get(pos);
            String[] info = new String[3];
            info[0]=peopleBean.getPeople_name();
            info[1]=peopleBean.getPeople_tel();
            info[2]=peopleBean.getPeople_email();

            Intent intent = new Intent(context,EditActivity.class);
            intent.putExtra("info", info);
            startActivityForResult(intent, 1);

//            intent.putExtra("name", peopleBean.getPeople_name());
//            intent.putExtra("tel", peopleBean.getPeople_tel());
//            intent.putExtra("email", peopleBean.getPeople_email());

        }
        return super.onContextItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){//说明twoactivity返回的
            if(resultCode==RESULT_OK){
                data.getFlags();
//                String rlt=data.getStringExtra("name");
//                String rlt2=data.getStringExtra("tel");
//                String rlt3=data.getStringExtra("email");
//                myAdapter.getList().get(i).setPeople_name(rlt);
//                myAdapter.getList().get(i).setPeople_tel(rlt2);
//                myAdapter.getList().get(i).setPeople_email(rlt3);
//                myAdapter.notifyDataSetChanged();
            }
        }else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                data.getFlags();
            }
        }
        myAdapter=new MyAdapter(context,this);
        listView.setAdapter(myAdapter);
    }

    /*接口方法，响应ListView按钮点击事件*/
    @Override
    public void click(View v) {
        //利用v对象获取在第一步中设置的Tag标记
        int pos = (Integer) v.getTag();

        switch (v.getId()){
        case R.id.ibtnTel:
        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + MyAdapter.peopleList.get(pos).getPeople_tel().toString()));
        startActivity(intent);
        break;
        case R.id.ibtnSms:
            intent = new Intent(Intent.ACTION_SENDTO,
                    Uri.parse("smsto:" + MyAdapter.peopleList.get(pos).getPeople_tel().toString()));
            intent.putExtra("sms_body", "Hello,");
            startActivity(intent);
        break;
    }
}}
