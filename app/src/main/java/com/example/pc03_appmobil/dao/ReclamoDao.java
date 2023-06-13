package com.example.pc03_appmobil.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pc03_appmobil.enums.ReclamoEnun;
import com.example.pc03_appmobil.model.Reclamo;

import java.util.ArrayList;
import java.util.List;

public class ReclamoDao {

    private final SQLiteDatabase database;

    public ReclamoDao(SQLiteDatabase database) {
        this.database = database;
    }


    public long insert(Reclamo reclamo) {
        ContentValues values = new ContentValues();
        values.put(ReclamoEnun.COL_CODIGO.getValue(), reclamo.getCodigo());
        values.put(ReclamoEnun.COL_ASUNTO.getValue(), reclamo.getAsunto());
        values.put(ReclamoEnun.COL_DESCRIPCION.getValue(), reclamo.getDescripcion());
        values.put(ReclamoEnun.COL_ESTADO.getValue(), reclamo.getEstado());
        values.put(ReclamoEnun.COL_FECHA.getValue(), reclamo.getFecha());

        /**
         * SQL requiere al menos un nombre de columna para insertar una fila
         * y el parámetro nullColumnHack se utiliza para insertar un valor NULL en caso de valores vacíos.
         */
        return database.insert(ReclamoEnun.TABLE_NAME.getValue(), null, values);
    }

    public long update(Reclamo reclamo) {
        ContentValues values = new ContentValues();
        values.put(ReclamoEnun.COL_CODIGO.getValue(), reclamo.getCodigo());
        values.put(ReclamoEnun.COL_ASUNTO.getValue(), reclamo.getAsunto());
        values.put(ReclamoEnun.COL_DESCRIPCION.getValue(), reclamo.getDescripcion());
        values.put(ReclamoEnun.COL_ESTADO.getValue(), reclamo.getEstado());
        values.put(ReclamoEnun.COL_FECHA.getValue(), reclamo.getFecha());

        String whereClause = String.format("%s=?", ReclamoEnun.COL_ID.getValue());
        String[] whereArgs = {String.valueOf(reclamo.getId())};
        return database.update(ReclamoEnun.TABLE_NAME.getValue(), values, whereClause, whereArgs);
    }

    public long deleteById(String id) {
        String whereClause = String.format("%s=?", ReclamoEnun.COL_ID.getValue());
        String[] whereArgs = {id};
        return database.delete(ReclamoEnun.TABLE_NAME.getValue(), whereClause, whereArgs);
    }

    public List<Reclamo> getAll() {
        List<Reclamo> reclamos = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", ReclamoEnun.TABLE_NAME.getValue());

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(ReclamoEnun.COL_ID.getValue());
                int codigoIndex = cursor.getColumnIndex(ReclamoEnun.COL_CODIGO.getValue());
                int asuntoIndex = cursor.getColumnIndex(ReclamoEnun.COL_ASUNTO.getValue());
                int descripcionIndex = cursor.getColumnIndex(ReclamoEnun.COL_DESCRIPCION.getValue());
                int estadoIndex = cursor.getColumnIndex(ReclamoEnun.COL_ESTADO.getValue());
                int fechaIndex = cursor.getColumnIndex(ReclamoEnun.COL_FECHA.getValue());

                int id = cursor.getInt(idIndex);
                String codigo = cursor.getString(codigoIndex);
                String asunto = cursor.getString(asuntoIndex);
                String descripcion = cursor.getString(descripcionIndex);
                String estado = cursor.getString(estadoIndex);
                String fecha = cursor.getString(fechaIndex);

                Reclamo reclamo = new Reclamo(id,asunto,codigo,descripcion,estado,fecha);
                reclamos.add(reclamo);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return reclamos;

    }

    public long deleteAll() {
        //String query = String.format("DELETE FROM %s", LibroEnum.TABLE_NAME.getValue());
        return database.delete(ReclamoEnun.TABLE_NAME.getValue(), null, null);
    }
}
