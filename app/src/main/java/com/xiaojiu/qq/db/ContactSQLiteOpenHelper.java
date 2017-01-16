package com.xiaojiu.qq.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：${xiaojiukeji} on 17/1/14 11:03
 * 作用:
 */

public class ContactSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String CONTACT = "contact";
    public static final String DB_NAME = "contacts.db";
    public static final int VERSION = 1;
    public static final String T_CONTACT = "t_contact";
    public static final String USERNAME = "username";

    private ContactSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public ContactSQLiteOpenHelper(Context context){
        super(context,DB_NAME,null,VERSION);

    }
    //初始化表结构
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + T_CONTACT + "(_id integer primary key," + USERNAME + " varchar(20)," + CONTACT + " varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
