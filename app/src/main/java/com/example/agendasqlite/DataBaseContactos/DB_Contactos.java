package com.example.agendasqlite.DataBaseContactos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.agendasqlite.Model.Contactos;

public class DB_Contactos extends SQLiteOpenHelper {

    private static final int DB_VERSION = 5;
    private static final String DB_NAME = "DB_Contactos.db";
    private static final String TABLE_NAME = "Contactos";

    //Creamos la tabla contactos con id autoincrementable
 private static final String instruction = "CREATE TABLE " + TABLE_NAME + " (" +
            "IdContacto INTEGER PRIMARY KEY AUTOINCREMENT,"+
            " name VARCHAR(100)," +
            " surname VARCHAR(100)," +
            " mail VARCHAR(100)," +
            " phone VARCHAR(20),"+
            " address VARCHAR(100))";


    public DB_Contactos(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(instruction);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
