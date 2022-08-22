package com.example.smartroomsimulationspring.Simulations;

import com.example.smartroomsimulationspring.DB.*;
import com.example.smartroomsimulationspring.Operations.*;
import com.example.smartroomsimulationspring.dataset.*;
import com.mongodb.client.*;

import java.sql.*;
import java.util.Date;

public class AdriaIndoorSimulator{

        public static String AdriaLog = "src\\main\\resources\\Logs\\AdriaIndoorLog.txt";
        public Boolean priority;

    public AdriaIndoorSimulator(Boolean priority) {
        this.priority = priority;
    }

    public void SmartRoomReadingsSimulator(Timestamp ts) {
            Logs.logMessage(AdriaLog , "----------------------------------------------");
            Logs.logMessage(AdriaLog , "Connection started: " + ts);
            Logs.logMessage(AdriaLog , "----------------------------------------------");
            InsertDocuments.insertSingleReading(AdriaLog, Filenames.AdriaCollectionName , Filenames.adriaRoomNames, Filenames.adriaFolderNames[58] ,ts , Filenames.AdriaRes , priority);
            Logs.logMessage(AdriaLog , "----------------------------------------------");
            Logs.logMessage(AdriaLog , "Connection closed: " + ts);
            Logs.logMessage(AdriaLog , "----------------------------------------------");
        }

        public void run() {
                    Date date = new Date();
                    Timestamp ts = new Timestamp(date.getTime());
                    ts.setTime(ts.getTime() + 7200000);
                    SmartRoomReadingsSimulator(ts);
    }
}
