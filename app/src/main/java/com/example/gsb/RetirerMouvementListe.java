package com.example.gsb;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.example.gsb.outils.BdAdapteMouvement;

public class RetirerMouvementListe extends Activity {

    private ListView listView;

    private BdAdapteMouvement mouvBdd;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.retirer_mouvement);
        listView = (ListView) findViewById(R.id.ListeMouvementRetirer);
        mouvBdd = new BdAdapteMouvement(this);

        //On ouvre la base de données pour écrire dedans
        mouvBdd.open();
        Cursor leCurseur = mouvBdd.getMouvementRetirer();

        // colonnes à afficher
        String[] colNoms = new String[] {BdAdapteMouvement.COL_ID,BdAdapteMouvement.COL_CODE, BdAdapteMouvement.COL_DATE,BdAdapteMouvement.COL_QUOTIENT,BdAdapteMouvement.COL_TYPE};

        // champs dans lesquelles afficher les colonnes
        int[] colNumeros = new int[] {R.id.txt_id,R.id.txt_code, R.id.txt_date,R.id.txt_quotient, R.id.txt_type};
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(this, R.layout.affichage_mouvement_retirer, leCurseur,colNoms,colNumeros);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        mouvBdd.close();


        //Button Quitter
        Button buttonQuitter = (Button)findViewById(R.id.buttonQuitterAjout);
        buttonQuitter.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                finish(); //fermeture de la fenêtre

            }

        });

    }

}