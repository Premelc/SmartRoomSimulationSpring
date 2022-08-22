package com.example.smartroomsimulationspring.Simulations;

import com.example.smartroomsimulationspring.dataset.*;

public class Simulator extends Thread{

    private String colName;
    private Long interval;
    private Boolean priority;

    public Simulator(String colName , Long interval , Boolean priority){
        this.setColName(colName);
        this.setInterval(interval);
        this.setPriority(priority);
    }

    public void setPriority(Boolean priority){
        this.priority = priority;
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
            AdriaIndoorSimulator adriaSim = new AdriaIndoorSimulator(this.priority);
            try{
                while(true){
                    long start1 = System.currentTimeMillis();
                    adriaSim.run();
                    long end = System.currentTimeMillis();
                    System.out.println("Entered data in thread: " + Thread.currentThread().getName() + " " + (end-start1)/1000 + " seconds elapsed");
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
                    System.out.println("Entered data in thread: " + Thread.currentThread().getName() + " " + (end-start1)/1000 + " seconds elapsed");
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
        else{
            //TODO: Dodati simulator za DHMZBase
        }
    }
}
