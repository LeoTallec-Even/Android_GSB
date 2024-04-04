package com.example.gsb.outils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateBdMouvement extends SQLiteOpenHelper {
    // Définition de la requête SQL pour créer la table 'mouvement'
    private static final String TABLE_MOUV = "mouvement";
    public static final String COL_ID = "_id";
    public static final String COL_CODE = "CODE";
    public static final String COL_DATE = "DATE";
    public static final String COL_QUOTIENT = "QUOTIENT";
    public static final String COL_TYPE = "TYPE";
    private static final String CREATE_BDD = "CREATE TABLE "+TABLE_MOUV+" (" +
            COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_CODE+ " TEXT NOT NULL," +
            COL_DATE + " TEXT NOT NULL," +
            COL_QUOTIENT + " INTEGER NOT NULL," +
            COL_TYPE + " INTEGER NOT NULL);";

    // Constructeur de la classe CreateBdMouvement
    public CreateBdMouvement(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Appelée lorsque la base de données est créée pour la première fois
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table 'mouvement' en exécutant la requête SQL
        try{
            db.execSQL(CREATE_BDD);
            System.out.println("Pour quoi tu veut pas!!!!!");
        }catch (Exception e){
            System.out.println("Erreur !!!" + e.getClass() + e.getMessage());
        }
    }

    // Appelée lorsque la base de données doit être mise à niveau
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Suppression de la table 'mouvement' existante si elle existe
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MOUV+";");
        // Recréation de la table 'mouvement' avec le nouveau schéma
        onCreate(db);
    }
}
