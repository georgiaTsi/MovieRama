package com.example.movierama;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieFavoriteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Movies db";

    public MovieFavoriteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Movies(ID Integer,IsFavorite Boolean);");
    }

    public void insertMovie(Integer id){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("IsFavorite", false);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Movies", null, contentValues);
    }

    public boolean getIsFavorite(Integer id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select IsFavorite from Movies where ID="+id+"", null );
        res.moveToFirst();

        Integer isFavorite = res.getColumnIndex("IsFavorite");

        if (!res.isClosed())  {
            res.close();
        }

        return !isFavorite.equals(0);
    }

    public boolean updateMovie(Integer id, Boolean isFavorite){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
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
