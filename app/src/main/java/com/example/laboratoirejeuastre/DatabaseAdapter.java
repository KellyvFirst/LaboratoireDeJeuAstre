package com.example.laboratoirejeuastre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addPlanet(Planet planet) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, planet.getName());
        values.put(DatabaseHelper.COLUMN_SIZE, planet.getSize());
        values.put(DatabaseHelper.COLUMN_STATUS, planet.getStatus());
        values.put(DatabaseHelper.COLUMN_IMAGE, planet.getImage());
        values.put(DatabaseHelper.COLUMN_X, planet.getX());
        values.put(DatabaseHelper.COLUMN_Y, planet.getY());
        long id = db.insert(DatabaseHelper.TABLE_PLANETS, null, values);
        db.close();
        if (id > Integer.MAX_VALUE) {
            throw new IllegalStateException("L'identifiant est trop grand pour être converti en int");
        }
        planet.setId((int) id); // Convertir l'identifiant en int avant de le définir
        return id;
    }

    public Planet getPlanet(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PLANETS, null, DatabaseHelper.COLUMN_ID + " = ?", new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Planet planet = new Planet(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SIZE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE)),
                    cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_X)),
                    cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_Y))
            );
            cursor.close();
            db.close();
            return planet;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }


    public List<Planet> getAllPlanets() {
        List<Planet> planets = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PLANETS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Planet planet = new Planet();

                planet.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
                planet.setSize(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SIZE)));
                planet.setStatus(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS)));
                planet.setImage(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE)));
                planet.setX(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_X)));
                planet.setY(cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_Y)));
                planets.add(planet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return planets;
    }
    public int updatePlanet(Planet planet) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, planet.getName());
        values.put(DatabaseHelper.COLUMN_SIZE, planet.getSize());
        values.put(DatabaseHelper.COLUMN_STATUS, planet.getStatus());
        values.put(DatabaseHelper.COLUMN_IMAGE, planet.getImage());
        values.put(DatabaseHelper.COLUMN_X, planet.getX());
        values.put(DatabaseHelper.COLUMN_Y, planet.getY());

        // Mise à jour de la planète dans la base de données
        int rowsAffected = db.update(DatabaseHelper.TABLE_PLANETS, values, DatabaseHelper.COLUMN_ID + " = ?", new String[] { String.valueOf(planet.getId()) });
        db.close();
        return rowsAffected;
    }

}
