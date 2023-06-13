package com.example.pc03_appmobil.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pc03_appmobil.enums.ReclamoEnun;


public class DatabaseHelper extends SQLiteOpenHelper {
    // El patrón de diseño Singleton
    private static final String DATABASE_NAME = "Reclamo.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryReclamoDrop =String.format("DROP TABLE IF EXISTS mi_reclamos;");
        db.execSQL(queryReclamoDrop);
       String queryReclamo = String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT,  %s TEXT, %s TEXT, %s TEXT);",
                ReclamoEnun.TABLE_NAME.getValue(),
                ReclamoEnun.COL_ID.getValue(),
                ReclamoEnun.COL_CODIGO.getValue(),
                ReclamoEnun.COL_ASUNTO.getValue(),
                ReclamoEnun.COL_DESCRIPCION.getValue(),
                ReclamoEnun.COL_ESTADO.getValue(),
                ReclamoEnun.COL_FECHA.getValue()
        );
       System.out.println("entra a eliminae");
         db.execSQL(queryReclamo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No se realiza ninguna operación en este método
    }
}
