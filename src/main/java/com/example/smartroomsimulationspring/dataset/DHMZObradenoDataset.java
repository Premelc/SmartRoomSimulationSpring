package com.example.smartroomsimulationspring.dataset;

import com.example.smartroomsimulationspring.Operations.*;

import java.sql.*;
import java.time.*;
import java.time.format.*;

public class DHMZObradenoDataset implements Dataset {

    public static final DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("d.MM.yyyy.");
    public static final DateTimeFormatter TimeFormatter = DateTimeFormatter.ofPattern("hh:MM:SS");

    private String _id;
    private long vrijeme;
    private Timestamp ts;
    private float zracenje;
    private float temperatura;
    private float smjerVjetra;
    private float brzinaVjetra;
    private float relativnaVlaznost;

    public String get_id() {
        return _id;
    }

    public void set_id() {

        int n = 20;

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(Filenames.AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(Filenames.AlphaNumericString
                    .charAt(index));
        }
        String prefix = String.valueOf(System.currentTimeMillis());
        String sufix = String.valueOf(System.currentTimeMillis());

        prefix = prefix.substring(prefix.length()-4 , prefix.length()-2);
        sufix = sufix.substring(prefix.length()-2 , prefix.length());

        this._id = ( prefix + sb.toString() + sufix);
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }



    public long getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(long vrijeme) {
        this.vrijeme = vrijeme;
    }

    public float getZracenje() {
        return zracenje;
    }

    public void setZracenje(float zracenje) {
        this.zracenje = zracenje;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getSmjerVjetra() {
        return smjerVjetra;
    }

    public void setSmjerVjetra(float smjerVjetra) {
        this.smjerVjetra = smjerVjetra;
    }

    public float getBrzinaVjetra() {
        return brzinaVjetra;
    }

    public void setBrzinaVjetra(float brzinaVjetra) {
        this.brzinaVjetra = brzinaVjetra;
    }

    public float getRelativnaVlaznost() {
        return relativnaVlaznost;
    }

    public void setRelativnaVlaznost(float relativnaVlaznost) {
        this.relativnaVlaznost = relativnaVlaznost;
    }

    public DHMZObradenoDataset(String[]csv){
            //this.set_id();
            this.setTs(Timestamp.valueOf(LocalDate.parse(Reader.parseDateTime(csv[0])[0]) + " " + Reader.parseDateTime(csv[0])[1] ));
            this.getTs().setTime(this.getTs().getTime());
            this.setVrijeme(this.getTs().getTime());
        try {
            this.setZracenje(Float.parseFloat(csv[2]));
        }catch(NumberFormatException nfe){
            //ako je vrijednost "nan" ili
            this.setZracenje(-1);
        }
        try {
            this.setTemperatura(Float.parseFloat(csv[3]));
        }catch(NumberFormatException nfe){
            //ako je vrijednost "nan" ili
            this.setTemperatura(-1);
        }
        try {
            this.setSmjerVjetra(Float.parseFloat(csv[4]));
        }catch(NumberFormatException nfe){
            //ako je vrijednost "nan" ili
            this.setSmjerVjetra(-1);
        }
        try{
            this.setBrzinaVjetra(Float.parseFloat(csv[5]));
        }catch(NumberFormatException nfe){
            //ako je vrijednost "nan" ili
            this.setBrzinaVjetra(-1);
        }
        try {
            this.setRelativnaVlaznost(Float.parseFloat(csv[6]));
        }catch(NumberFormatException nfe){
            //ako je vrijednost "nan" ili
            this.setRelativnaVlaznost(-1);
        }
    }

}
