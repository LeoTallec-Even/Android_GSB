package com.example.gsb.echantillon;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.example.gsb.R;
import com.example.gsb.modele.Echantillon;
import com.example.gsb.modele.Mouvement;
import com.example.gsb.outils.BdAdapteMouvement;
import com.example.gsb.outils.BdAdapter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MajStock extends Activity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // C'est la première chose à faire !!!!!!
        setContentView(R.layout.maj_stock);
        Spinner Code;
        EditText Quantite;
        Button btn_Supprimer = (Button)findViewById(R.id.btn_supprimer);;
        Code = (Spinner) findViewById (R.id.spinoza);
        Quantite = (EditText) findViewById (R.id.etxt_quantite);
        TextView txt_Error = (TextView)findViewById(R.id.txt_Error) ;
        Button buttonQuitter = (Button)findViewById(R.id.buttonQuitterAjout);


        buttonQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //fermeture de la fenêtre
            }
        });

        BdAdapter Echant = new BdAdapter(getBaseContext());
        Echant.open();
        Cursor codeBdd = Echant.getCode();
        if(codeBdd != null && codeBdd.moveToFirst()) {
            List<String> listeDeToutLesStrings = new ArrayList<>();
            do {
                listeDeToutLesStrings.add(codeBdd.getString(0));
            } while (codeBdd.moveToNext());
            codeBdd.close();
            String[] listeCode = listeDeToutLesStrings.toArray(new String[0]);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listeCode);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spnClients = (Spinner) findViewById(R.id.spinoza);
            spnClients.setAdapter(adapter);
        }

        btn_Supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BdAdapter Bdd = new BdAdapter(getBaseContext());
                Bdd.open();
                try{
                    Echantillon echant = Bdd.getEchantillonWithCode(Code.getSelectedItem().toString());
                    Log.i("Code : ",echant.getCode());
                    echant.setQuantiteStock(String.valueOf(Math.max(Integer.parseInt(echant.getQuantiteStock()) - Integer.parseInt(Quantite.getText().toString()), 0)));
                    int resultat = Bdd.updateEchantillon(echant.getCode(),echant);
                    Bdd.close();

                    if(resultat != 0) {
                        if((Math.max(Integer.parseInt(echant.getQuantiteStock()), 0))==0){
                            txt_Error.setText("ATTENTION LA QTT EST DE 0 MAINTENANT");
                        } else{
                            txt_Error.setText("Database mise à jour");
                            try{

                                LocalDate currentDate = LocalDate.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                String formattedDate = currentDate.format(formatter);
                                Mouvement retirer = new Mouvement(echant.getCode(),formattedDate,Integer.parseInt(Quantite.getText().toString()),"0");

                                BdAdapteMouvement BddMouv = new BdAdapteMouvement(getBaseContext());
                                BddMouv.open();
                                Log.i("test",retirer.getCode()+","+retirer.getDate()+","+retirer.getType()+","+retirer.getQuotient());
                                BddMouv.insererMouvement(retirer);
                                txt_Error.setText("Database et Mouvement mise à jour");
                                BddMouv.close();

                            }
                            catch(Exception e){
                                txt_Error.setText(e.getMessage()+","+e.getClass());
                            }

                        }
                    }
                    else {
                        txt_Error.setText("keske c'est que ces Carabistouille");
                    }
                }
                catch (NumberFormatException e ){
                    txt_Error.setText("la Quantité est trop grande !");
                }
                catch (Exception e){
                    txt_Error.setText(e.getClass() + " -> " + e.getMessage());
                }



            }

        });





        Button btn_validation = (Button)findViewById(R.id.btn_validation);
        btn_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BdAdapter Bdd = new BdAdapter(getBaseContext());
                Bdd.open();
                try{


                    Echantillon echant = Bdd.getEchantillonWithCode(Code.getSelectedItem().toString());
                    echant.setQuantiteStock(String.valueOf(Math.max(Integer.parseInt(echant.getQuantiteStock()) + Integer.parseInt(Quantite.getText().toString()), 0)));
                    int resultat = Bdd.updateEchantillon(echant.getCode(),echant);


                    if(resultat != 0) {
                        txt_Error.setText("La base de donnée a été mise à jour !");
                        LocalDate currentDate = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String formattedDate = currentDate.format(formatter);
                        Mouvement retirer = new Mouvement(echant.getCode(),formattedDate,Integer.parseInt(Quantite.getText().toString()),"1");

                        BdAdapteMouvement BddMouv = new BdAdapteMouvement(getBaseContext());
                        BddMouv.open();
                        Log.i("test",retirer.getCode()+","+retirer.getDate()+","+retirer.getType()+","+retirer.getQuotient());
                        BddMouv.insererMouvement(retirer);
                        txt_Error.setText("Database et Mouvement mise à jour");
                        BddMouv.close();
                    }
                    else {
                        txt_Error.setText("keske c'est que ces Carabistouille");
                    }
                }
                catch (NumberFormatException e ){
                    txt_Error.setText("la quantité est trop grande !");
                }
                catch (Exception e){
                    txt_Error.setText(e.getClass() + " -> " + e.getMessage());
                }

                Bdd.close();

            }

        });

    }

}