package com.example.hp.contacts;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

/**
 * Created by hp on 2015/7/7.
 */
public class MyAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    public static List<PeopleBean> peopleList;
    private Callback mCallback;
    public MyAdapter(Context context, Callback callback){
        this.context=context;
       this. mCallback=callback;
        setList();
    }

    //==============================================
    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     */
    public interface Callback {
        public void click(View v);
    }

    //==============================================

    List<PeopleBean> getList(){
        return peopleList;
    }

    private void setList(){//对数据列表初始化
        PeopleDBAdapter peopleDBAdapter = new PeopleDBAdapter(context);
        peopleDBAdapter.openDB();
        peopleList=peopleDBAdapter.queryAll();
        peopleDBAdapter.closeDB();

//        peopleBeanList=new ArrayList<PeopleBean>();
//        for (int i=0;i<20;i++){
//            PeopleBean people=new PeopleBean();
////            people.setImageId(R.drawable.buddy_4_mb5ucom);
//            people.setName("zhang" + i);
//            people.setTel("12345670" + i);
//            people.setEmail("zhang" + i+"@163.com");
//            peopleBeanList.add(people);
//        }
    }
    @Override
    public int getCount() {
        if (peopleList == null){
            return 0;
        }else {
            return peopleList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (peopleList == null){
            return null;
        }else {
            return peopleList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView==null){
            holder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.listview_item,null);
            holder.imageView=(ImageView)convertView.findViewById(R.id.list_img);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.tel=(TextView)convertView.findViewById(R.id.tel);

            holder.ibtnTel=(ImageButton)convertView.findViewById(R.id.ibtnTel);
            holder.ibtnSms=(ImageButton)convertView.findViewById(R.id.ibtnSms);
            holder.ibtnSms.setOnClickListener(this);
            holder.ibtnTel.setOnClickListener(this);
            /*在定义控件的时候，设置一个Tag标记，
            *将getView(int position, View convertView, ViewGroup parent)方法的position记录下来；
            *然后在按钮的onClick(View v)方法中，利用v对象获取在第一步中设置的Tag标记，
            *也就是保存的位置，通过位置，可以从列表集合里获取到相应的值
            */
            holder.ibtnTel.setTag(position);
            holder.ibtnSms.setTag(position);


            convertView.setTag(holder);

        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        PeopleBean peopleBean = peopleList.get(position);

//        holder.imageView.setImageResource(peopleBean.getImageId());
        holder.name.setText(peopleBean.getPeople_name());
        holder.tel.setText(peopleBean.getPeople_tel());
        holder.imageView.setImageResource(R.drawable.buddy_10_mb5ucom);

        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView name,tel;
        ImageButton ibtnTel,ibtnSms;
    }

    //响应按钮点击事件,调用子定义接口，并传入View
    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }

}
