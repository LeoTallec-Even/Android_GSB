package com.example.gsb.outils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class CreateBdEchantillon extends SQLiteOpenHelper {

    private static final String TABLE_ECHANT = "echantillons";

    private static final String COL_CODE = "_id";

    private static final String COL_LIB = "LIB";

    private static final String COL_STOCK = "STOCK";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_ECHANT + " ("+
            COL_CODE + " TEXT NOT NULL PRIMARY KEY, " +
            COL_LIB + " TEXT NOT NULL, " +
            COL_STOCK + " TEXT NOT NULL);";

    public CreateBdEchantillon(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        // appelée lorsqu’aucune base n’existe et que la classe utilitaire doit en créer une
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        try{
            db.execSQL(CREATE_BDD);
            System.out.println("Pour quoi tu veut pas!!!!!");
        }catch (Exception e){
            System.out.println("Erreur !!!" + e.getClass() + e.getMessage());
        }

    }

    @Override // appelée si la version de la base a changé

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//On peut supprimer la table et de la recréer

        db.execSQL("DROP TABLE " + TABLE_ECHANT + ";");
        db.execSQL("DROP TABLE IF EXISTS echantillon ; ");
        onCreate(db);

    }

}
