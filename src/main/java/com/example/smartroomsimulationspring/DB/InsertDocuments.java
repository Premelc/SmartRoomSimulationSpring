package com.example.smartroomsimulationspring.DB;

import com.example.smartroomsimulationspring.Operations.Reader;
import com.example.smartroomsimulationspring.Operations.*;
import com.example.smartroomsimulationspring.controllers.*;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.internal.operation.*;
import org.bson.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.sql.*;
import java.util.Date;
import java.util.*;

import static com.example.smartroomsimulationspring.Simulations.AdriaIndoorSimulator.AdriaPrioLog;
import static com.example.smartroomsimulationspring.dataset.Filenames.*;

public class InsertDocuments {

    public static List<Document> insertSingleReading(String log, String collectionName, String[] fileName, String folderName, Timestamp ts, String resPath , Boolean priority) {
        long start1 = System.currentTimeMillis();

        MongoClient mongoClient = Connect.getClient().mongoClient;
        MongoCollection<Document> Collection = null;
        List<Document> list = new ArrayList<>();
        MongoDatabase SmartRoomTrialDb = mongoClient.getDatabase("2022");
        if(priority){
            String file = fileName[0].substring(0,5) + IntervalController.priorityRoom + fileName[0].substring(8);
            String path = resPath + "" + folderName + "\\" + file;
            list.clear();
            try {
                BufferedReader br = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8);
                WrappedReader wr = new WrappedReader(file, br, path);
                list.addAll(Reader.SingleLine(wr, ts, collectionName));
                Collection = SmartRoomTrialDb.getCollection(IntervalController.priorityRoom);
                wr.getBr().close();
                wr = null;
            } catch (IOException ioe) {
            }
            try {
                assert Collection != null;
                Collection.insertMany(list, new InsertManyOptions().ordered(false));
                long end = System.currentTimeMillis();
                Logs.logMessage(AdriaPrioLog, (end - start1)+ "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            for (int i = 0; i < fileName.length; i++) {
                String path = resPath + "" + folderName + "\\" + fileName[i];
                if(fileName[i].equals(fileName[0].substring(0,5) + IntervalController.priorityRoom + fileName[0].substring(8))){
                    //do nothing
                }else{
                    list.clear();
                    try {
                        BufferedReader br = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8);
                        WrappedReader wr = new WrappedReader(fileName[i], br, path);
                        list.addAll(Reader.SingleLine(wr, ts, collectionName));
                        if (collectionName.equals(AdriaCollectionName)) {
                            Collection = SmartRoomTrialDb.getCollection(fileName[i].substring(5, fileName[i].length() - 4));
                        } else if (collectionName.equals(DHMZObradenoCollectionName)) {
                            Collection = SmartRoomTrialDb.getCollection("DHMZObradeno");
                        }
                        wr.getBr().close();
                        wr = null;
                    } catch (IOException ioe) {
                    }
                    try {
                        assert Collection != null;
                        Collection.insertMany(list, new InsertManyOptions().ordered(false));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            long end = System.currentTimeMillis();
            Logs.logMessage(log, (end - start1)+ "");
        }
        return list;
    }

    public static List<Document> ArchiveAllData(String log, String collectionName, String[] fileName, String[] folderName, String resPath) {
        long start1 = System.currentTimeMillis();
        MongoClient mongoClient = Connect.getClient().mongoClient;

        MongoDatabase SmartRoomTrialDb = mongoClient.getDatabase(archiveDBName);
        MongoCollection<Document> Collection = SmartRoomTrialDb.getCollection(collectionName);

        if (collectionName.equals(AdriaCollectionName)) {
            System.out.println("        Accessing collection " + collectionName);
            System.out.println("        " + fileName.length * 7330 * folderName.length + " Entries expected ");
            Logs.logMessage(log, "        Accessing collection " + collectionName);
            Logs.logMessage(log, "        " + fileName.length * 7330 * folderName.length + " Entries expected ");
        } else if (collectionName.equals(DHMZObradenoCollectionName)) {
            System.out.println("        Accessing collection " + collectionName);
            System.out.println("        " + 1 * 8761 + " Entries expected ");
            //Operations.Logs.logMessage(log, "        Accessing collection " + collectionName);
            //Operations.Logs.logMessage(log, "        " + 1 * 8761 + " Entries expected ");
        }

        List<Document> list = new ArrayList<>();
        int i = 0;
        int tot = 0;
        int j = 0;

        for (j = 0; j < folderName.length; j++) {
            for (i = 0; i < fileName.length; i++) {

                String path = resPath + "" + folderName[j] + "\\" + fileName[i];
                try {
                    BufferedReader br = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8);
                    WrappedReader wr = new WrappedReader(fileName[i], br, path);
                    list.addAll(Reader.fullFileAlternate(wr, collectionName));
                    if (collectionName.equals(AdriaCollectionName)) {
                        SmartRoomTrialDb = mongoClient.getDatabase(folderName[j].substring(0, folderName[j].length() - 3));
                        Collection = SmartRoomTrialDb.getCollection(fileName[i].substring(5, fileName[i].length() - 4));
                    } else if (collectionName.equals(DHMZObradenoCollectionName)) {
                        SmartRoomTrialDb = mongoClient.getDatabase(folderName[j].substring(folderName[j].length() - 4));
                        Collection = SmartRoomTrialDb.getCollection("DHMZObradeno");
                    }
                    wr.getBr().close();
                    wr = null;
                } catch (IOException ioe) {
                    //Dolazi do IOExceptiona ako ne pronadje file u folderu, nije potrebno išta napraviti jer traži sve fajlove u svakom folderu i sve ih prije ili kasnije pronađe
                    //System.out.println("Nije u tom direktoriju");
                }

                try {
                    Collection.insertMany(list, new InsertManyOptions().ordered(false));
                    System.out.println("        " + list.size() + " " + collectionName + " readings entered at: " + new Date(System.currentTimeMillis()));
                    //Operations.Logs.logMessage(log, "        " + list.size() + " " + collectionName + " readings entered at: " + new Date(System.currentTimeMillis()));
                    tot += list.size();
                    list.clear();
                    long end1 = System.currentTimeMillis();
                    System.out.println("        Elapsed time: " + (end1 - start1) + "ms");
                    System.out.println();
                    //Operations.Logs.logMessage(log, "        Elapsed time: " + (end1 - start1) + "ms");
                    //Operations.Logs.logMessage(log, " ");
                } catch (Exception e) {
                    //Operations.Logs.logMessage(log, "        " + "FAILED TO LOAD DOCUMENTS:  " + new Date(System.currentTimeMillis()));
                    //Operations.Logs.logMessage(log, e.getStackTrace().toString());
                    //Operations.Logs.logMessage(log, e.getMessage());
                    long end1 = System.currentTimeMillis();
                    //Operations.Logs.logMessage(log, "        Elapsed time: " + (end1 - start1) + "ms");
                } finally {
                    //Napravi index po vremenu
                    Collection.createIndex(Indexes.descending("vrijeme"));
                }
            }
        }


        long end2 = System.currentTimeMillis();
        System.out.println("        Total readings entered: " + tot);
        System.out.println("        Elapsed time: " + (end2 - start1) + "ms");
        Logs.logMessage(log, "        Total readings entered: " + tot);
        Logs.logMessage(log, "        Elapsed time: " + (end2 - start1) + "ms");


        return list;
    }

    public static List<Document> insertSingleReadingAlternate(String log, String collectionName, String[] fileName, String folderName, Timestamp ts, String resPath , Boolean priority) {
        long start1 = System.currentTimeMillis();
        MongoClient mongoClient = Connect.getClient().mongoClient;
        MongoCollection<Document> Collection = null;
        List<Document> list = new ArrayList<>();
        if(priority){
            MongoDatabase SmartRoomTrialDb = mongoClient.getDatabase(IntervalController.priorityRoom);
            String file = fileName[0].substring(0,5) + IntervalController.priorityRoom + fileName[0].substring(8);
            String path = resPath + "" + folderName + "\\" + file;
            list.clear();
            try {
                BufferedReader br = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8);
                WrappedReader wr = new WrappedReader(file, br, path);
                list.addAll(Reader.SingleLine(wr, ts, collectionName));
                Collection = SmartRoomTrialDb.getCollection("2022");
                wr.getBr().close();
                wr = null;
            } catch (IOException ioe) {
            }
            try {
                assert Collection != null;
                Collection.insertMany(list, new InsertManyOptions().ordered(false));
                long end = System.currentTimeMillis();
                Logs.logMessage(AdriaPrioLog, (end - start1)+ "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            for (int i = 0; i < fileName.length; i++) {
                String path = resPath + "" + folderName + "\\" + fileName[i];
                if(fileName[i].equals(fileName[0].substring(0,5) + IntervalController.priorityRoom + fileName[0].substring(8))){
                    //do nothing
                }else{
                    list.clear();
                    try {
                        BufferedReader br = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8);
                        WrappedReader wr = new WrappedReader(fileName[i], br, path);
                        list.addAll(Reader.SingleLine(wr, ts, collectionName));
                        if (collectionName.equals(AdriaCollectionName)) {
                            MongoDatabase SmartRoomTrialDb = mongoClient.getDatabase(fileName[i].substring(5, fileName[i].length() - 4));
                            Collection = SmartRoomTrialDb.getCollection("2022");
                        } else if (collectionName.equals(DHMZObradenoCollectionName)) {
                            MongoDatabase SmartRoomTrialDb = mongoClient.getDatabase("DHMZObradeno");
                            Collection = SmartRoomTrialDb.getCollection("2022");
                        }
                        wr.getBr().close();
                        wr = null;
                    } catch (IOException ioe) {
                    }
                    try {
                        assert Collection != null;
                        Collection.insertMany(list, new InsertManyOptions().ordered(false));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            long end = System.currentTimeMillis();
            Logs.logMessage(log, (end - start1)+ "");
        }
        return list;
    }

    public static List<Document> ArchiveAllDataAlternate(String log, String collectionName, String[] fileName, String[] folderName, String resPath) {
        long start1 = System.currentTimeMillis();
        MongoClient mongoClient = Connect.getClient().mongoClient;

        MongoDatabase SmartRoomTrialDb = mongoClient.getDatabase(archiveDBName);
        MongoCollection<Document> Collection = SmartRoomTrialDb.getCollection(collectionName);

        if (collectionName.equals(AdriaCollectionName)) {
            System.out.println("        Accessing collection " + collectionName);
            System.out.println("        " + fileName.length * 7330 * folderName.length + " Entries expected ");
            Logs.logMessage(log, "        Accessing collection " + collectionName);
            Logs.logMessage(log, "        " + fileName.length * 7330 * folderName.length + " Entries expected ");
        } else if (collectionName.equals(DHMZObradenoCollectionName)) {
            System.out.println("        Accessing collection " + collectionName);
            System.out.println("        " + 1 * 8761 + " Entries expected ");
            //Operations.Logs.logMessage(log, "        Accessing collection " + collectionName);
            //Operations.Logs.logMessage(log, "        " + 1 * 8761 + " Entries expected ");
        }

        List<Document> list = new ArrayList<>();
        int i = 0;
        int tot = 0;
        int j = 0;

        for (j = 0; j < folderName.length; j++) {
            for (i = 0; i < fileName.length; i++) {

                String path = resPath + "" + folderName[j] + "\\" + fileName[i];
                try {
                    BufferedReader br = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8);
                    WrappedReader wr = new WrappedReader(fileName[i], br, path);
                    System.out.println(fileName[i] + " ; " + path);
                    list.addAll(Reader.fullFileAlternate(wr, collectionName));
                    if (collectionName.equals(AdriaCollectionName)) {
                        SmartRoomTrialDb = mongoClient.getDatabase(fileName[i].substring(5, fileName[i].length() - 4));
                        Collection = SmartRoomTrialDb.getCollection(folderName[j].substring(0, folderName[j].length() - 3));
                    } else if (collectionName.equals(DHMZObradenoCollectionName)) {
                        SmartRoomTrialDb = mongoClient.getDatabase("DHMZObradeno");
                        Collection = SmartRoomTrialDb.getCollection(folderName[j].substring(folderName[j].length() - 4));
                    }
                    wr.getBr().close();
                    wr = null;
                } catch (IOException ioe) {
                    //Dolazi do IOExceptiona ako ne pronadje file u folderu, nije potrebno išta napraviti jer traži sve fajlove u svakom folderu i sve ih prije ili kasnije pronađe
                    //System.out.println("Nije u tom direktoriju");
                }

                try {
                    Collection.insertMany(list, new InsertManyOptions().ordered(false));
                    System.out.println("        " + list.size() + " " + collectionName + " readings entered at: " + new Date(System.currentTimeMillis()));
                    //Operations.Logs.logMessage(log, "        " + list.size() + " " + collectionName + " readings entered at: " + new Date(System.currentTimeMillis()));
                    tot += list.size();
                    list.clear();
                    long end1 = System.currentTimeMillis();
                    System.out.println("        Elapsed time: " + (end1 - start1) + "ms");
                    System.out.println();
                    //Operations.Logs.logMessage(log, "        Elapsed time: " + (end1 - start1) + "ms");
                    //Operations.Logs.logMessage(log, " ");
                } catch (Exception e) {
                    //Operations.Logs.logMessage(log, "        " + "FAILED TO LOAD DOCUMENTS:  " + new Date(System.currentTimeMillis()));
                    //Operations.Logs.logMessage(log, e.getStackTrace().toString());
                    //Operations.Logs.logMessage(log, e.getMessage());
                    long end1 = System.currentTimeMillis();
                    //Operations.Logs.logMessage(log, "        Elapsed time: " + (end1 - start1) + "ms");
                } finally {
                    //Napravi index po vremenu
                    Collection.createIndex(Indexes.descending("vrijeme"));
                }
            }
        }


        long end2 = System.currentTimeMillis();
        System.out.println("        Total readings entered: " + tot);
        System.out.println("        Elapsed time: " + (end2 - start1) + "ms");
        Logs.logMessage(log, "        Total readings entered: " + tot);
        Logs.logMessage(log, "        Elapsed time: " + (end2 - start1) + "ms");


        return list;
    }

}
