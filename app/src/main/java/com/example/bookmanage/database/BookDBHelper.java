package com.example.bookmanage.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bookmanage.Book;

import java.util.ArrayList;


@SuppressLint("DefaultLocale")
public class BookDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "BookDBHelper";
    private static final String DB_NAME = "book.db";
    private static final int DB_VERSION = 1;
    private static BookDBHelper mHelper = null;
    private SQLiteDatabase mDB = null;
    public static final String TABLE_NAME = "book";

    private BookDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private BookDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public static BookDBHelper getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new BookDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new BookDBHelper(context);
        }
        return mHelper;
    }

    public SQLiteDatabase openReadLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    public SQLiteDatabase openWriteLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    public void closeLink() {
        if (mDB != null && mDB.isOpen()) {
            mDB.close();
            mDB = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        Log.d(TAG, "drop_sql:" + drop_sql);
        db.execSQL(drop_sql);
        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "book_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
                + "book_name VARCHAR NOT NULL,"
                + "author VARCHAR NOT NULL,"
                + "ISBN VARCHAR PRIMARY KEY  NOT NULL,"
                + "publish_year VARCHAR NOT NULL"
                + "publish_club VARCHAR NOT NULL"
                + ");";
        Log.d(TAG, "create_sql:" + create_sql);
        db.execSQL(create_sql);
    }

    // 修改数据库，执行表结构变更语句
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade oldVersion=" + oldVersion + ", newVersion=" + newVersion);
        if (newVersion > 1) {
            //Android的ALTER命令不支持一次添加多列，只能分多次添加
            String alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "phone VARCHAR;";
            Log.d(TAG, "alter_sql:" + alter_sql);
            db.execSQL(alter_sql);
            alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "password VARCHAR;";
            Log.d(TAG, "alter_sql:" + alter_sql);
            db.execSQL(alter_sql);
        }
    }

    // 根据指定条件删除表记录
    public int delete(String condition) {
        // 执行删除记录动作，该语句返回删除记录的数目
        return mDB.delete(TABLE_NAME, condition, null);
    }

    // 删除该表的所有记录
    public int deleteAll() {
        // 执行删除记录动作，该语句返回删除记录的数目
        return mDB.delete(TABLE_NAME, "1=1", null);
    }

    // 往该表添加一条记录
    public int insert(Book book) {
        ArrayList<Book> tempArray;
        if (book.getBook_name() != null && book.getBook_name().length() > 0) {
            String condition = String.format("name='%s'", book.getBook_name());
            tempArray = query(condition);
            if (tempArray.size() > 0) {
                return 1;
            }
        }
//            // 如果存在同样的手机号码，则更新记录
//            if (info.phone != null && info.phone.length() > 0) {
//                String condition = String.format("phone='%s'", info.phone);
//                tempArray = query(condition);
//                if (tempArray.size() > 0) {
//                    update(info, condition);
//                    result = tempArray.get(0).rowid;
//                    continue;
//                }
//            }

        // 不存在唯一性重复的记录，则插入新记录
        ContentValues cv = new ContentValues();
        cv.put("book_name", book.getBook_name());
        cv.put("author", book.getAuthor());
        cv.put("ISBN", book.getISBN());
        cv.put("publish_year", book.getPublish_year());
        cv.put("publish_club", book.getPublish_club());
        mDB.insert(TABLE_NAME, "", cv);
        return 0;
    }

    // 根据条件更新指定的表记录
    public int update(Book info, String condition) {
        ContentValues cv = new ContentValues();
        cv.put("bookName", info.getBook_name());
        cv.put("author", info.getAuthor());
        cv.put("ISBN", info.getISBN());
        cv.put("publish_year", info.getPublish_year());
        cv.put("publish_club", info.getPublish_club());
        // 执行更新记录动作，该语句返回记录更新的数目
        return mDB.update(TABLE_NAME, cv, condition, null);
    }

    public int update(Book info) {
        // 执行更新记录动作，该语句返回记录更新的数目
        return update(info, "ISBN=" + info.getISBN());
    }


    public ArrayList<Book> query(String condition) {
        String sql = String.format("select book_name,author,ISBN,publish_year,publish_club" +
                " from %s where %s;", TABLE_NAME, condition);
        Log.d(TAG, "query sql: " + sql);
        ArrayList<Book> bookArray = new ArrayList<Book>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mDB.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.setBook_name(cursor.getString(0));
            book.setAuthor(cursor.getString(1));
            book.setISBN(cursor.getString(2));
            book.setPublish_year(cursor.getString(3));
            book.setPublish_club(cursor.getString(4));

            bookArray.add(book);
        }
        cursor.close();
        return bookArray;
    }

//    // 根据手机号码查询指定记录
//    public Book queryByPhone(String phone) {
//        Book info = null;
//        ArrayList<Book> infoArray = query(String.format("phone='%s'", phone));
//        if (infoArray.size() > 0) {
//            info = infoArray.get(0);
//        }
//        return info;
//    }

}
