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
        InsertDocuments.insertSingleReading(DHMZObradenoLog, Filenames.DHMZObradenoCollectionName , new String[]{"Data2018.txt"} , Filenames.DHMZObradenoFolderNames[4], ts , Filenames.DHMZObradeniRes ,false);
        //InsertDocuments.insertSingleReadingAlternate(DHMZObradenoLog, Filenames.DHMZObradenoCollectionName , new String[]{"Data2018.txt"} , Filenames.DHMZObradenoFolderNames[4], ts , Filenames.DHMZObradeniRes ,false);
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
