package com.xiaojiu.qq.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：${xiaojiukeji} on 17/1/14 11:11
 * 作用:
 */

public class DBUtils {
    private static Context mContext;
    private static boolean isInit;

    public static void initDB(Context context) {
        mContext = context.getApplicationContext();
        isInit = true;
    }

    public static List<String> getContacts(String usename) {
        if (!isInit) {
            throw new RuntimeException("使用DBUtils之前,请在Application中初始化");
        } else {
            ContactSQLiteOpenHelper contactSQLiteOpenHelper = new ContactSQLiteOpenHelper(mContext);
            SQLiteDatabase readableDatabase = contactSQLiteOpenHelper.getReadableDatabase();
            Cursor query = readableDatabase.query(ContactSQLiteOpenHelper.T_CONTACT, new String[]{ContactSQLiteOpenHelper.CONTACT},
                    ContactSQLiteOpenHelper.USERNAME + "= ?", new String[]{usename}, null, null, ContactSQLiteOpenHelper.CONTACT
            );
            List<String> contactsList = new ArrayList<>();
            while (query.moveToNext()){
                String string = query.getString(0);
                contactsList.add(string);
            }
            query.close();
            readableDatabase.close();
            return contactsList;
        }
    }

    /**
     *
     * @param usename
     * @param contactList
     * 1.先删除username的所有联系人
     * 2.再添加contactList添加进去
     */
    public static void updateContacts(String usename,List<String> contactList){
        ContactSQLiteOpenHelper openHelper = new ContactSQLiteOpenHelper(mContext);
        SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
        writableDatabase.beginTransaction();

        writableDatabase.delete(ContactSQLiteOpenHelper.T_CONTACT,ContactSQLiteOpenHelper.USERNAME + "= ?",new String[]{usename});
        ContentValues values = new ContentValues();
        for (int i = 0; i < contactList.size(); i++) {
            String contact = contactList.get(i);
            values.put(ContactSQLiteOpenHelper.CONTACT,contact);
            writableDatabase.insert(ContactSQLiteOpenHelper.T_CONTACT,null,values);
        }

        writableDatabase.setTransactionSuccessful();
        
        writableDatabase.endTransaction();
        writableDatabase.close();
    }
}
