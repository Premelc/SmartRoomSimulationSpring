package com.example.smartroomsimulationspring.dataset;

import java.sql.*;
import java.time.*;
import java.time.format.*;

public class AdriaIndoorDataset implements Dataset{
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy.");

    private String _id;
    private int RB;
    private String roomName;
    private LocalTime vrijeme;
    private Timestamp timestamp;
    private int zadana;
    private int izmjerena;
    private int statusKlime;
    private int brzinaPuhanja;
    private int ventil;
    private int prisutnost;
    private int prozor;
    private char modKlime;
    private int wcSet;
    private int wcMjerena;

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

    public LocalTime getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(LocalTime vrijeme) {
        this.vrijeme = vrijeme;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRB() {
        return RB;
    }

    public void setRB(int RB) {
        this.RB = RB;
    }

    public int getZadana() {
        return zadana;
    }

    public void setZadana(int zadana) {
        this.zadana = zadana;
    }

    public int getIzmjerena() {
        return izmjerena;
    }

    public void setIzmjerena(int izmjerena) {
        this.izmjerena = izmjerena;
    }

    public int getStatusKlime() {
        return statusKlime;
    }

    public void setStatusKlime(int statusKlime) {
        this.statusKlime = statusKlime;
    }

    public int getBrzinaPuhanja() {
        return brzinaPuhanja;
    }

    public void setBrzinaPuhanja(int brzinaPuhanja) {
        this.brzinaPuhanja = brzinaPuhanja;
    }

    public int getVentil() {
        return ventil;
    }

    public void setVentil(int ventil) {
        this.ventil = ventil;
    }

    public int getPrisutnost() {
        return prisutnost;
    }

    public void setPrisutnost(int prisutnost) {
        this.prisutnost = prisutnost;
    }

    public int getProzor() {
        return prozor;
    }

    public void setProzor(int prozor) {
        this.prozor = prozor;
    }

    public char getModKlime() {
        return modKlime;
    }

    public void setModKlime(char modKlime) {
        this.modKlime = modKlime;
    }

    public int getWcSet() {
        return wcSet;
    }

    public void setWcSet(int wcSet) {
        this.wcSet = wcSet;
    }

    public int getWcMjerena() {
        return wcMjerena;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setWcMjerena(int wcMjerena) {
        this.wcMjerena = wcMjerena;
    }

    //MAIN CONSTRUCTOR
    public AdriaIndoorDataset(String[] csv){
        this.setRB(Integer.parseInt(csv[0]));
        //this.set_id();
        this.setVrijeme(LocalTime.parse(csv[2]));
        this.setTimestamp(Timestamp.valueOf(LocalDate.parse(csv[1] , AdriaIndoorDataset.formatter) + " " + csv[2]));
        this.getTimestamp().setTime(this.getTimestamp().getTime());
        this.setZadana(Integer.parseInt(csv[3]));
        this.setIzmjerena(Integer.parseInt(csv[4]));
        this.setStatusKlime(Integer.parseInt(csv[5]));
        this.setBrzinaPuhanja(Integer.parseInt(csv[6]));
        this.setVentil(Integer.parseInt(csv[7]));
        this.setPrisutnost(Integer.parseInt(csv[8]));
        this.setProzor((Integer.parseInt(csv[9])));
        this.setModKlime(csv[10].charAt(0));
        this.setWcSet(Integer.parseInt(csv[11]));
        this.setWcMjerena(Integer.parseInt(csv[12]));
        this.setRoomName(csv[13]);
    }

    @Override
    public String toString() {
        return "AdriaIndoorDataset{" +
                "_id='" + _id + '\'' +
                ", RB=" + RB +
                ", roomName='" + roomName + '\'' +
                ", vrijeme=" + vrijeme +
                ", timestamp=" + timestamp +
                ", zadana=" + zadana +
                ", izmjerena=" + izmjerena +
                ", statusKlime=" + statusKlime +
                ", brzinaPuhanja=" + brzinaPuhanja +
                ", ventil=" + ventil +
                ", prisutnost=" + prisutnost +
                ", prozor=" + prozor +
                ", modKlime=" + modKlime +
                ", wcSet=" + wcSet +
                ", wcMjerena=" + wcMjerena +
                '}';
    }
}
