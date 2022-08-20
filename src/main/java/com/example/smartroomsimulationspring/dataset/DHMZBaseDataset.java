package com.example.smartroomsimulationspring.dataset;

import com.example.smartroomsimulationspring.Operations.*;

import java.time.*;
import java.time.format.*;

public class DHMZBaseDataset implements Dataset {

    public static final DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
    public static final DateTimeFormatter TimeFormatter = DateTimeFormatter.ofPattern("H:mm");

    private int RB;
    private LocalDate datum;
    private LocalTime vrijeme;
    private float humidity;
    private float outsideTemp;
    private float irridiation;
    private float windDirection;
    private float windSpeed;

    public int getRB() {
        return RB;
    }

    public void setRB(int RB) {
        this.RB = RB;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(LocalTime vrijeme) {
        this.vrijeme = vrijeme;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getOutsideTemp() {
        return outsideTemp;
    }

    public void setOutsideTemp(float outsideTemp) {
        this.outsideTemp = outsideTemp;
    }

    public float getIrridiation() {
        return irridiation;
    }

    public void setIrridiation(float irridiation) {
        this.irridiation = irridiation;
    }

    public float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(float windDirection) {
        this.windDirection = windDirection;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public DHMZBaseDataset(String[]csv) {
        this.setRB(Integer.parseInt(csv[0]));
        this.setDatum(LocalDate.parse(Reader.parseDHMZbaseDateTime(csv[1])[0] , DHMZBaseDataset.DateFormatter));
        this.setVrijeme(LocalTime.parse(Reader.parseDHMZbaseDateTime(csv[1])[1] , DHMZBaseDataset.TimeFormatter));
        this.setHumidity(Float.parseFloat(csv[2]));
        this.setOutsideTemp(Float.parseFloat(csv[3]));
        this.setIrridiation(Float.parseFloat(csv[4]));
        this.setWindDirection(Float.parseFloat(csv[5]));
        this.setWindSpeed(Float.parseFloat(csv[6]));
    }

    @Override
    public String toString() {
        return "DHMZbase{" +
                "RB=" + RB +
                ", datum=" + datum +
                ", vrijeme=" + vrijeme +
                ", humidity=" + humidity +
                ", outsideTemp=" + outsideTemp +
                ", irridiation=" + irridiation +
                ", windDirection=" + windDirection +
                ", windSpeed=" + windSpeed +
                '}';
    }
}
