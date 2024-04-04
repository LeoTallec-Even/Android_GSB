package com.example.gsb.outils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.gsb.modele.Mouvement;

public class BdAdapteMouvement {
    static final int VERSION_BDD = 21;
    private static final String NOM_BDD = "gsb.db";
    private static final String TABLE_MOUV = "mouvement";

    public static final String COL_ID = "_id";
    static final int NUM_COL_ID = 0;
    public static final String COL_CODE = "CODE";
    static final int NUM_COL_CODE = 1;
    public static final String COL_DATE = "DATE";
    static final int NUM_COL_DATE = 2;
    public static final String COL_QUOTIENT = "QUOTIENT";
    static final int NUM_COL_QUOTIENT = 3;
    public static final String COL_TYPE = "TYPE";
    static final int NUM_COL_TYPE = 4;

    private final CreateBdMouvement bdArticles;
    private final Context context;
    private SQLiteDatabase db;
    public BdAdapteMouvement(Context context){
        this.context = context;
        bdArticles = new CreateBdMouvement(context, NOM_BDD, null, VERSION_BDD);
    }

    //si la base n’existe pas, l’objet SQLiteOpenHelper exécute la méthode onCreate
    // si la version de la base a changé, la méthode onUpgrade sera lancée, dans les 2 cas l’appel
    // à getWritableDatabase permet d’ouvrir une connexion à la base en écriture
    public BdAdapteMouvement open(){
        db = bdArticles.getWritableDatabase();
        Log.i("MouvementOpen","Est ouvert : " + db.isOpen());
        return this;
    }
    public BdAdapteMouvement close (){
        db.close(); return null;
    }
    public long insererMouvement(Mouvement unMouvement){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne où on veut mettre la valeur)
        values.put(COL_CODE, unMouvement.getCode());
        values.put(COL_DATE, unMouvement.getDate());
        values.put(COL_QUOTIENT, unMouvement.getQuotient());
        values.put(COL_TYPE, unMouvement.getType());
        //on insère l'objet dans la BDD via le ContentValues
        return db.insert(TABLE_MOUV, null, values);
    }
    private Mouvement cursorToMouvement(Cursor c){
        //Cette méthode permet de convertir un cursor en un mouvement
        // si aucun élément n'a été retourné dans la requête, on renvoie null
        Mouvement unMouvement = null ;
        if (c.getCount() != 0){
            c.moveToFirst();//on se place sur le premier élément
            unMouvement = new Mouvement(null,null,0,null); //On créé un mouvement
            // on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            unMouvement.setCode(c.getString(NUM_COL_CODE));
            unMouvement.setDate(c.getString(NUM_COL_DATE));
            unMouvement.setQuotient(Integer.parseInt(c.getString(NUM_COL_QUOTIENT)));
            unMouvement.setType(c.getString(NUM_COL_TYPE));

        }
        c.close(); //On ferme le cursor
        return unMouvement; //On retourne mouvement
    }

    public Mouvement getMouvementWithCode(String unCode){ //Récupère dans un Cursor les valeurs correspondant à un mouvement grâce à sa designation)
        Cursor c = db.query(TABLE_MOUV, new String[] {COL_CODE, COL_DATE,COL_QUOTIENT, COL_TYPE}, COL_CODE + " LIKE \"" + unCode +"\"", null, null, null, null);
        return cursorToMouvement(c); }

    public int updateMouvement(String unCode, Mouvement unMouvement){ //La mise à jour d'un mouvement dans la BDD fonctionne plus ou moins comme une insertion,

        // il faut simple préciser quel mouvement on doit mettre à jour grâce à son code.
        ContentValues values = new ContentValues();
        values.put(COL_CODE, unMouvement.getCode());
        values.put(COL_DATE, unMouvement.getDate());
        values.put(COL_QUOTIENT, unMouvement.getQuotient());
        values.put(COL_TYPE, unMouvement.getType());
        return db.update(TABLE_MOUV, values, COL_CODE + " = \"" +unCode+"\"", null);
    }
    public int removeMouvementWithCode(String unCode) {
        //Suppression d'un Mouvement de la BDD grâce à son code
        return db.delete(TABLE_MOUV, COL_CODE + " = \"" +unCode+"\"", null);
    }
    public Cursor getData(){
        return db.rawQuery("SELECT * FROM mouvement", null);
    }
    public Cursor getMouvementRetirer(){
        return  db.rawQuery("SELECT * FROM mouvement WHERE TYPE = 0;",null);
    }
    public Cursor getMouvementAjout(){
        return  db.rawQuery("SELECT * FROM mouvement WHERE TYPE = 1;",null);
    }
}
