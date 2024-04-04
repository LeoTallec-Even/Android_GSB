package com.example.gsb.echantillon;

import android.app.Activity;
import android.icu.text.DateIntervalFormat;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import com.example.gsb.AjoutMouvementListe;
import com.example.gsb.R;
import com.example.gsb.modele.Echantillon;
import com.example.gsb.modele.Mouvement;
import com.example.gsb.outils.BdAdapteMouvement;
import com.example.gsb.outils.BdAdapter;


public class AjoutEchantillon extends Activity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EditText Code;
        EditText Libelle;
        EditText Quantite;
        TextView messageErreur;



        setContentView(R.layout.ajout_echantillon);
        messageErreur = (TextView)findViewById(R.id.messageErreur);
        Button buttonQuitter = (Button)findViewById(R.id.buttonQuitterAjout);
        buttonQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //fermeture de la fenêtre

            }

        });
        Button Ajout = (Button)findViewById(R.id.btn_validation);
        Code = (EditText) findViewById (R.id.Code);
        Libelle = (EditText) findViewById (R.id.Libellé);
        Quantite = (EditText) findViewById (R.id.Quantite);
        Ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Echantillon Echant = new Echantillon(Code.getText().toString(), Libelle.getText().toString(), Quantite.getText().toString());
                    BdAdapter Bdd = new BdAdapter(getBaseContext());
                    Bdd.open();
                    Bdd.insererEchantillon(Echant);
                    Bdd.close();
                    BdAdapteMouvement BddMouvement = new BdAdapteMouvement(getBaseContext());
                    BddMouvement.open();

                    LocalDate currentDate = LocalDate.now();
                    // Formater la date au format YYYY-MM-DD
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate = currentDate.format(formatter);

                    Mouvement AjoutMouvement = new Mouvement(Code.getText().toString(), formattedDate, Integer.parseInt(Quantite.getText().toString()), "1");
                    BddMouvement.insererMouvement(AjoutMouvement);
                    BddMouvement.close();
                    messageErreur.setText("Ajout réussi");
                }
                catch(Exception e){
                    messageErreur.setText("Erreur : "+ e.getMessage() + " Class : " + e.getClass());
                }
            }

        });
        //TODO Message a l'ajout pour dire ca marche
    }

}