package com.example.user.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class goodsDB extends SQLiteOpenHelper {

    private static final String database = "i1.db";
    private static final int version = 1;

    public goodsDB(Context context,String name,SQLiteDatabase.CursorFactory factory,
                      int version){
        super(context,name,factory,version);
    }

    public goodsDB(Context context){
        this(context,database,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE myTable1(_id integer primary key autoincrement,"+
                "goods text no null,"+"price text no null,"+"describe text no null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS myTable1");
        onCreate(db);
    }
}
