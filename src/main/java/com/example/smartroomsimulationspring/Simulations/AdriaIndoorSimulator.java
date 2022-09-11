package com.example.smartroomsimulationspring.Simulations;

import com.example.smartroomsimulationspring.DB.*;
import com.example.smartroomsimulationspring.Operations.*;
import com.example.smartroomsimulationspring.controllers.*;
import com.example.smartroomsimulationspring.dataset.*;

import java.sql.*;
import java.util.Date;

public class AdriaIndoorSimulator{

        public static String AdriaLog = "src\\main\\resources\\Logs\\AdriaIndoorLog.txt";
    public static String AdriaPrioLog = "src\\main\\resources\\Logs\\AdriaPrioLog.txt";

    private Boolean priority;

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public AdriaIndoorSimulator(Boolean priority) {
        this.setPriority(priority);
    }

    public void SmartRoomReadingsSimulator(Timestamp ts) {
        //InsertDocuments.insertSingleReadingAlternate(AdriaLog, Filenames.AdriaCollectionName , Filenames.adriaRoomNames, Filenames.adriaFolderNames[58] ,ts , Filenames.AdriaRes , getPriority());
            InsertDocuments.insertSingleReading(AdriaLog, Filenames.AdriaCollectionName , Filenames.adriaRoomNames, Filenames.adriaFolderNames[58] ,ts , Filenames.AdriaRes , getPriority());
        }

        public void run() {
                    Date date = new Date();
                    Timestamp ts = new Timestamp(date.getTime());
                    ts.setTime(ts.getTime() + 7200000);
                    SmartRoomReadingsSimulator(ts);
    }
}
