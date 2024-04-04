package com.example.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Vibrator;

import com.example.gsb.echantillon.AjoutEchantillon;
import com.example.gsb.echantillon.ListeEchantillon;
import com.example.gsb.echantillon.MajStock;
import com.example.gsb.modele.Echantillon;
import com.example.gsb.modele.Mouvement;
import com.example.gsb.outils.BdAdapteMouvement;
import com.example.gsb.outils.BdAdapter;

public class MainActivity extends AppCompatActivity {

    private Button buttonEchantillon;
    private Button buttonMouvementAjout;
    private Button buttonMouvementRetirer;
    private Button buttonQuitter;

    private Button buttonListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // récupération du Button grâce à son ID
        buttonEchantillon = (Button)findViewById(R.id.btn_ajouter_echantillon);
        buttonEchantillon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchactivity = new Intent(MainActivity.this, AjoutEchantillon.class);
                startActivity(launchactivity);
            }
        }) ;
        ;

        buttonListe = (Button)findViewById(R.id.btn_liste_echantillon);
        buttonListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lanuchactivity = new Intent(MainActivity.this, ListeEchantillon.class);
                startActivity(lanuchactivity);
            }
        });

        buttonEchantillon = (Button)findViewById(R.id.btn_maj_stock);
        buttonEchantillon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchactivity = new Intent(MainActivity.this, MajStock.class);
                startActivity(launchactivity);
            }
        });

        buttonMouvementAjout = (Button)findViewById(R.id.btn_mvt_ajout);
        buttonMouvementAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchactivity = new Intent(MainActivity.this, AjoutMouvementListe.class);
                startActivity(launchactivity);
            }
        });

        buttonMouvementRetirer = (Button)findViewById(R.id.btn_mvt_retirer);
        buttonMouvementRetirer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchactivity = new Intent(MainActivity.this, RetirerMouvementListe.class);
                startActivity(launchactivity);
            }
        });


        buttonQuitter = (Button)findViewById(R.id.Quitter);
        buttonQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(1);
            }
        }) ;



        // creation d’un jeu d’essai à ajout de quelques enregistrements


        //jeuEssaiBd();
        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 400 milliseconds
        v.vibrate(400);
    }

    public void jeuEssaiBd(){
        System.out.println("Test 01");
        try {
            //BdAdapteMouvement mouvBdd = new BdAdapteMouvement(this);
            //mouvBdd.open();
            //int resultat = (int) mouvBdd.insererMouvement(new Mouvement("code4","2024-12-12",10,"0"));
            //Toast.makeText(this, "Nbs mouv modifier/creer : " + resultat, Toast.LENGTH_LONG).show();
            //mouvBdd.close();
            //Création d'une instance de la classe echantBDD
            BdAdapter echantBdd = new BdAdapter(this);

            //On ouvre la base de données pour écrire dedans
            echantBdd.open();
            //On insère DES ECHANTILLONS DANS LA BD
            echantBdd.insererEchantillon(new Echantillon("code1", "lib1", "3"));
            echantBdd.insererEchantillon(new Echantillon("code2", "lib2", "5"));
            echantBdd.insererEchantillon(new Echantillon("code3", "lib3", "7"));
            echantBdd.insererEchantillon(new Echantillon("code4", "lib4", "6"));
            echantBdd.removeEchantillonWithCode("code4");

            echantBdd.updateEchantillon("code4",(new Echantillon("code4","lib4","9")));

            //toast.show();
            echantBdd.close();

        }catch (Exception e){
            //Si se déclanche Crier Gwenaël !
            Toast.makeText(this, "il y a une Erreur : "+ e.getClass() + "  : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}