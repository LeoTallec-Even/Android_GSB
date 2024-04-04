package com.example.gsb.modele;

public class Mouvement {

    protected String code;
    protected String date;
    protected int quotient;
    protected String type;
    public Mouvement(String code, String date,int quotient, String type) {
        this.code = code;
        this.date = date;
        this.quotient = quotient;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuotient() {
        return quotient;
    }

    public void setQuotient(int quotient) {
        this.quotient = quotient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}