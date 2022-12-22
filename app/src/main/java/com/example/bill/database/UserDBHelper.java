package com.example.bill.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bill.entity.UserInfo;

//用户
public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "user.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_USERS_INFO = "user_info";

    private static UserDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    public UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建账单信息表
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS_INFO +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "username VARCHAR(15) UNIQUE NOT NULL," +
                "password INTEGER(15) NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static UserDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new UserDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public void openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
    }

    // 打开数据库的写连接
    public void openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }

        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    // 添加用户
    public long insert(UserInfo user) {
        ContentValues cv = new ContentValues();
        cv.put("username", user.getUsername());
        cv.put("password", user.getPassword());
        return mWDB.insert(TABLE_USERS_INFO, null, cv);
    }

    // 查询用户
    @SuppressLint("Recycle")
    public boolean query(UserInfo user) {
        String sql = "SELECT * FROM " + TABLE_USERS_INFO + " WHERE username = ? AND password = ?";
        String[] selectionArgs = {user.getUsername(), user.getPassword()};
        Log.d("UserDBHelper.query", sql);
        return mRDB.rawQuery(sql, selectionArgs).moveToNext();
    }
}
