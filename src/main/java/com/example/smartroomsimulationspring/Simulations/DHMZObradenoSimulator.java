package com.example.smartroomsimulationspring.Simulations;

import com.example.smartroomsimulationspring.DB.*;
import com.example.smartroomsimulationspring.Operations.*;
import com.example.smartroomsimulationspring.dataset.*;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class DHMZObradenoSimulator extends TimerTask {

    public static String DHMZObradenoLog = "src\\main\\resources\\Logs\\DHMZObradenoLog.txt";

    public void SmartRoomReadingsSimulator(Timestamp ts){
        Logs.logMessage(DHMZObradenoLog , "----------------------------------------------");
        Logs.logMessage(DHMZObradenoLog , "Connection started: " + ts);
        Logs.logMessage(DHMZObradenoLog , "----------------------------------------------");

        InsertDocuments.insertSingleReading(DHMZObradenoLog, Filenames.DHMZObradenoCollectionName , Filenames.DHMZObradenoFileNames , Filenames.DHMZObradenoFolderNames[0], ts , Filenames.DHMZObradeniRes);

        Logs.logMessage(DHMZObradenoLog , "----------------------------------------------");
        Logs.logMessage(DHMZObradenoLog , "Connection closed: " + ts);
        Logs.logMessage(DHMZObradenoLog , "----------------------------------------------");
    }

    public void run() {
        //OVO TIMER POKREĆE
        try{
                Date date = new Date();
                Timestamp ts = new Timestamp(date.getTime());
                ts.setTime(ts.getTime() + 7200000);
                SmartRoomReadingsSimulator(ts);
                Thread.sleep(200);
        }catch (InterruptedException e){
            System.out.println("Closing thread " + Thread.currentThread().getName());
        }

    }
}
