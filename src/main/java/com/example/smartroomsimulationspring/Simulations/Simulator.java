package com.example.smartroomsimulationspring.Simulations;

import com.example.smartroomsimulationspring.controllers.*;
import com.example.smartroomsimulationspring.dataset.*;

public class Simulator extends Thread{

    private String colName;
    private Long interval;
    private Boolean isPriority;

    public Simulator(String colName , Long interval , Boolean isPriority){
        this.setColName(colName);
        this.setInterval(interval);
        this.setIsPriority(isPriority);
    }

    public Boolean getIsPriority() {
        return isPriority;
    }
    public void setPriority(Boolean priority) {
        isPriority = priority;
    }

    public void setIsPriority(Boolean priority){
        this.isPriority = priority;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public void run() {
        //OVO TIMER POKREĆE
        if(this.getColName().equals(Filenames.AdriaCollectionName)){
            AdriaIndoorSimulator adriaSim = new AdriaIndoorSimulator(this.getIsPriority());
            try{
                while(true){
                    long start1 = System.currentTimeMillis();
                    adriaSim.run();
                    long end = System.currentTimeMillis();
                    System.out.println("Entered ADRIA data in thread: " + Thread.currentThread().getName() + " " + (end-start1)/1000 + " seconds elapsed , priority? " + this.getIsPriority());
                    IntervalController.intervalManipulation(this.interval);
                    if(this.interval - (end-start1) <= 0){
                        Thread.sleep(100);
                    }else{
                        Thread.sleep(this.interval - (end-start1));
                    }
                    end = System.currentTimeMillis();
                    System.out.println((end-start1)/1000 + " seconds elapsed realtime; interval:" + this.interval);
                }
            }catch (InterruptedException e){
                System.out.println("Closing thread " + Thread.currentThread().getName());
            }
        }
        else if(this.getColName().equals(Filenames.DHMZObradenoCollectionName)){
            DHMZObradenoSimulator dhmzSim = new DHMZObradenoSimulator();
            try{
                while(true){
                    long start1 = System.currentTimeMillis();
                    dhmzSim.run();
                    long end = System.currentTimeMillis();
                    System.out.println("Entered DHMZ data in thread: " + Thread.currentThread().getName() + " " + (end-start1)/1000 + " seconds elapsed");
                    IntervalController.intervalManipulation(this.interval);
                    if(this.interval - (end-start1) <= 0){
                        Thread.sleep(100);
                    }else{
                        Thread.sleep(this.interval - (end-start1));
                    }
                    end = System.currentTimeMillis();
                    System.out.println((end-start1) + " seconds elapsed realtime; interval:" + this.interval);
                }
            }catch (InterruptedException e){
                System.out.println("Closing thread " + Thread.currentThread().getName());
            }
        }
    }
}
