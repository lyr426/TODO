package com.example.yulmoostodo;

import android.provider.BaseColumns;

public final class ListContract {
    public static class ListEntry implements BaseColumns {
        public static final String TABLE_NAME = "List";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_CONTENTS = "contents";
        public static final String COLUMN_CHK = "chk";
        public static final String COLUMN_CHK_IMP = "chkImp";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                        + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_DATE + " TEXT, "
                        + COLUMN_CONTENTS + " TEXT, "
                        + COLUMN_CHK + " INTEGER, "
                        + COLUMN_CHK_IMP + " INTEGER)" ;
        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
