package com.example.movierama.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.PreparedStatement;

public class MovieFavoriteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Movies db";

    public MovieFavoriteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Movies(ID Integer,IsFavorite INTEGER);");
    }

    public void insertMovie(Integer id){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("IsFavorite", 1);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Movies", null, contentValues);

        return;
    }

    public boolean getIsFavorite(Integer id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery( "SELECT IsFavorite FROM Movies WHERE ID='"+id+"'", null );
        res.moveToFirst();

        String isFavorite = res.getString(0);

        if (!res.isClosed())  {
            res.close();
        }

        return !isFavorite.equals("0");
    }

    public boolean updateMovie(Integer id, Boolean isFavoriteBoolean){
        Integer isFavorite = 0;
        if(isFavoriteBoolean)
            isFavorite = 1;

        ContentValues contentValues = new ContentValues();
        contentValues.put("IsFavorite", isFavorite);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update("Movies", contentValues, "id = ? ", new String[] {Integer.toString(id)});

        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
