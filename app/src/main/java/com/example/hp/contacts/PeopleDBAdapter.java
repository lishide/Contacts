package com.example.hp.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PeopleDBAdapter {

    final static String TABLE="people";
    final static String PEOPLE_NAME="people_name";
    final static String PEOPLE_TEL="people_tel";
    final static String PEOPLE_EMAIL="people_email";

    Context context;

    SQLiteDatabase database;
    DBUtils dbUtils;

    public PeopleDBAdapter(Context context){
        this.context=context;
    }

    public void openDB(){
        dbUtils=new DBUtils(context);
        database=dbUtils.getDB();
    }

    public void closeDB(){
        dbUtils.closeDB();
    }

    public List<PeopleBean> queryAll(){
        Cursor cursor=database.query(TABLE,null,null,null,null,null,null);
        return convertToPeople(cursor);
    }

    public List<PeopleBean> query(String selection,String selectionArgs){
        Cursor cursor=database.query(TABLE,null,selection+"=?",new String[]{selectionArgs},null,null,null);
        return convertToPeople(cursor);
    }

//    public long insert(PeopleBean peopleBean){
//        ContentValues values=new ContentValues();
//        values.put(PEOPLE_NAME, peopleBean.getPeople_name());
//        values.put(PEOPLE_TEl, peopleBean.getPeople_tel());
//        values.put(PEOPLE_EMAIL, peopleBean.getPeople_email());
//        return database.insert(TABLE, null, values);
//    }
    public long insert(String people_name,String people_tel,String people_email){
        ContentValues values=new ContentValues();
        values.put(PEOPLE_NAME, people_name);
        values.put(PEOPLE_TEL, people_tel);
        values.put(PEOPLE_EMAIL, people_email);
        return database.insert(TABLE, null, values);
    }

    public int update(/*int img ,*/String name,String people_name,String people_tel,String people_email){
        ContentValues values = new ContentValues();
        values.put(PEOPLE_NAME,  people_name);
        values.put(PEOPLE_TEL, people_tel);
        values.put(PEOPLE_EMAIL, people_email);
        return database.update(TABLE, values,PEOPLE_NAME+"=?", new String[]{people_name});
    }

//    public int update(PeopleBean peopleBean,String people_name){
//        ContentValues values=new ContentValues();
//        values.put(PEOPLE_NAME, peopleBean.getPeople_name());
//        values.put(PEOPLE_TEL, peopleBean.getPeople_tel());
//        values.put(PEOPLE_EMAIL, peopleBean.getPeople_email());
//        return database.update(TABLE, values, PEOPLE_NAME + "=?", new String[]{people_name});
//    }

    public int delete_byName(String people_name) {
        return database.delete(TABLE, PEOPLE_NAME + "=?" ,new String[]{people_name});
    }

    public int delete_All(){
        return database.delete(TABLE, null, null);
    }

    private List<PeopleBean> convertToPeople(Cursor cursor){
        List<PeopleBean> peoples;
        if (cursor.getCount()==0){
            return null;
        }
        peoples=new ArrayList<PeopleBean>();
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++){
            PeopleBean peopleBean=new PeopleBean();
            peopleBean.setPeople_name(cursor.getString(cursor.getColumnIndex(PEOPLE_NAME)));
            peopleBean.setPeople_tel(cursor.getString(cursor.getColumnIndex(PEOPLE_TEL)));
            peopleBean.setPeople_email(cursor.getString(cursor.getColumnIndex(PEOPLE_EMAIL)));
            peoples.add(peopleBean);
            cursor.moveToNext();
        }
        return peoples;
    }

}
