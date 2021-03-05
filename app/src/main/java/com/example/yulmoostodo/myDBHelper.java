package com.example.yulmoostodo;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.util.Date;

public class myDBHelper  {

    private String DATABASE_NAME = "list.db";
    private int DATABASE_VERSION = 1;
    private Context context;
    private DatabaseHelper dbhelper;
    SQLiteDatabase sqldb;


    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ListContract.ListEntry.SQL_CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(ListContract.ListEntry.SQL_DELETE_TABLE);
            onCreate(db);
        }
    }
    public myDBHelper(Context context){this.context = context;}

    public myDBHelper open() throws SQLException{
        dbhelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqldb = dbhelper.getWritableDatabase();
        return this;
    }

    public void create() {dbhelper.onCreate(sqldb);}

    public void close() {sqldb.close();}

    public long insertRecord(String date, String contents, int chk, int chk_imp){

        ContentValues values = new ContentValues();
        values.put(ListContract.ListEntry.COLUMN_DATE, date);
        values.put(ListContract.ListEntry.COLUMN_CONTENTS, contents);
        values.put(ListContract.ListEntry.COLUMN_CHK, chk);
        values.put(ListContract.ListEntry.COLUMN_CHK_IMP, chk_imp);

       return sqldb.insert(ListContract.ListEntry.TABLE_NAME, null, values);

    }
    public Cursor readRecordOrderbyId(){
        String[] projection = {
                BaseColumns._ID,
                ListContract.ListEntry.COLUMN_DATE,
                ListContract.ListEntry.COLUMN_CONTENTS,
                ListContract.ListEntry.COLUMN_CHK,
                ListContract.ListEntry.COLUMN_CHK_IMP
        };
        String sortOrder = BaseColumns._ID + " DESC";

        Cursor cursor = sqldb.query(
                ListContract.ListEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        return cursor;
    }

    public Cursor searchColumns(String columnName, String search) {
        Cursor cursor = sqldb.rawQuery( "SELECT * FROM " + ListContract.ListEntry.TABLE_NAME + " WHERE " + columnName + " = " + search + ";", null);
        return cursor;
    }
    public Cursor origin() {
        Cursor cursor = sqldb.rawQuery( "SELECT * FROM " + ListContract.ListEntry.TABLE_NAME ,null);
        return cursor;
    }
    public void deleteColumn(Integer id) {
        sqldb.execSQL("DELETE FROM " + ListContract.ListEntry.TABLE_NAME + " WHERE _id = '" + id + "';");
    }
    public void updateColumn_chk(Integer id, int TF) {
        sqldb.execSQL("UPDATE "+ ListContract.ListEntry.TABLE_NAME + " SET " + ListContract.ListEntry.COLUMN_CHK + " = " + TF + " WHERE _id = '" + id + "';" );
    }

}