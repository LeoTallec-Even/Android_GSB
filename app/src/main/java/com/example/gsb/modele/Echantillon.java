package com.example.gsb.modele;

public class Echantillon {

    protected String code;
    protected String libelle;
    protected String quantiteStock;
    public Echantillon(String code, String libelle, String quantiteStock) {
        this.code = code;
        this.libelle = libelle;
        this.quantiteStock = quantiteStock;
    }
    public String getCode(){
        return this.code;
    }
    public void setCode (String code){
        this.code=code;
    }
    public String getLibelle (){
        return this.libelle;
    }
    public void setLibelle (String libelle){
        this.libelle = libelle;
    }
    public String getQuantiteStock (){
        return this.quantiteStock;
    }
    public void setQuantiteStock (String quantiteStock){
        this.quantiteStock=quantiteStock;
    }


}