package com.example.bill.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bill.entity.BillInfo;

import java.util.ArrayList;
import java.util.List;

public class BillDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bill.db";//数据库名

    private static String TABLE_BILLS_INFO = "bill_info";//账单信息表名
    private static final int DB_VERSION = 1;//数据库版本号

    private static BillDBHelper mHelper = null;//数据库帮助器的唯一实例
    private SQLiteDatabase mRDB = null;//读数据库
    private SQLiteDatabase mWDB = null;//写数据库

    private BillDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private BillDBHelper(Context context, String username) {
        super(context, DB_NAME, null, DB_VERSION);
        //用户名
        TABLE_BILLS_INFO = "[" + username + "_bill_info" + "]";
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static BillDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new BillDBHelper(context);
        }
        return mHelper;
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static BillDBHelper getInstance(Context context, String username) {
        if (mHelper == null) {
            mHelper = new BillDBHelper(context, username);
        }else {
            TABLE_BILLS_INFO = "[" + username + "_bill_info" + "]";
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

    // 创建数据库，执行建表语句
    //当执行getWritableDatabase或getReadableDatabase时，若数据库文件不存在，则执行。
    // 也就是只执行一次
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建账单信息表
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BILLS_INFO +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " date VARCHAR NOT NULL," +
                " type INTEGER NOT NULL," +
                " amount DOUBLE NOT NULL," +
                " remark VARCHAR NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 保存一条订单记录
    public long insert(BillInfo bill) {
        ContentValues cv = new ContentValues();
        cv.put("date", bill.getDate());
        cv.put("type", bill.getType());
        cv.put("amount", bill.getAmount());
        cv.put("remark", bill.getRemark());
        return mWDB.insert(TABLE_BILLS_INFO, null, cv);
    }

    // 删除一条订单记录
    public int delete(int id) {
        //1表名、2字段名、3占位符的数据
        return mWDB.delete(TABLE_BILLS_INFO, "_id=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<BillInfo> queryByMonth(String yearMonth) {
        onCreate(mWDB);
        List<BillInfo> list = new ArrayList<>();
        // 2035-09-12
        // select * from bill_info where date like '2035-09%'
        String sql = "SELECT * FROM " + TABLE_BILLS_INFO + " WHERE date LIKE '" + yearMonth + "%'";
        Log.d("BillDBHelper", "queryByMonth: " + sql);
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            BillInfo bill = new BillInfo();
            bill.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            bill.setDate(cursor.getString(cursor.getColumnIndex("date")));
            bill.setType(cursor.getInt(cursor.getColumnIndex("type")));
            bill.setAmount(cursor.getDouble(cursor.getColumnIndex("amount")));
            bill.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            list.add(bill);
        }
        cursor.close();
        return list;
    }

}
