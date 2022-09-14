package com.example.smartroomsimulationspring.Sim;

import com.example.smartroomsimulationspring.DB.*;
import com.example.smartroomsimulationspring.Simulations.*;
import com.example.smartroomsimulationspring.dataset.*;
import com.mongodb.client.*;
import com.mongodb.client.model.*;

import static com.example.smartroomsimulationspring.Simulations.AdriaIndoorSimulator.*;

public class mainSmartRoomSimulator{

    public static void SimulationInit(){
        long start1 = System.currentTimeMillis();
        System.out.println("Spajam se na bazu...");
        MongoClient mongoClient = Connect.getClient().mongoClient;
        archive(mongoClient);
        //archiveAlternate(mongoClient);
        //createIndexes(mongoClient);
        long end1 = System.currentTimeMillis();
        System.out.println("Done");
        System.out.println("Executed in: " + (end1 - start1) + "ms");
        return;
    }

    public static void archive(MongoClient mongoClient){
       // InsertDocuments.ArchiveAllData(DHMZObradenoSimulator.DHMZObradenoLog, Filenames.DHMZObradenoCollectionName , Filenames.DHMZObradenoFileNames , Filenames.DHMZObradenoFolderNames,  Filenames.DHMZObradeniRes);
       // InsertDocuments.ArchiveAllData(AdriaLog, Filenames.AdriaCollectionName, Filenames.adriaRoomNames , Filenames.adriaFolderNames,  Filenames.AdriaRes);
        createIndexes(mongoClient);
    }

    public static void archiveAlternate(MongoClient mongoClient){
       // InsertDocuments.ArchiveAllDataAlternate(DHMZObradenoSimulator.DHMZObradenoLog, Filenames.DHMZObradenoCollectionName , Filenames.DHMZObradenoFileNames , Filenames.DHMZObradenoFolderNames,  Filenames.DHMZObradeniRes);
        InsertDocuments.ArchiveAllDataAlternate(AdriaLog, Filenames.AdriaCollectionName, Filenames.adriaRoomNames , Filenames.adriaFolderNames,  Filenames.AdriaRes);
        createIndexesAlternate(mongoClient);
    }

    public static void createIndexes(MongoClient mongoClient){
        String[] years = {"2013" , "2014" , "2015" , "2016" , "2017" , "2018" , "2019" , "2020" , "2021" , "2022"};

        for(String s : years){
            MongoDatabase db = mongoClient.getDatabase(s);
            for(String str : Filenames.adriaRoomNames){
                String c =  str.substring(5 , str.length()-4);
               String res = db.getCollection(c).createIndex(Indexes.descending("vrijeme"));
               System.out.println("created index : "+ res + " in " + s + " " + str);
            }
            String res = db.getCollection("DHMZObradeno").createIndex(Indexes.descending("vrijeme"));
            System.out.println("created index : "+ res + " in " + s + " " + "DHMZObradeno");
        }

    }

    public static void createIndexesAlternate(MongoClient mongoClient){
        String[] years = {"2013" , "2014" , "2015" , "2016" , "2017" , "2018" , "2019" , "2020" , "2021"};

        for(String s : Filenames.adriaRoomNames){
            MongoDatabase db = mongoClient.getDatabase(s.substring(5, s.length() - 4));
            for(String str : years){
                String res = db.getCollection(str).createIndex(Indexes.descending("vrijeme"));
                System.out.println("created index : "+ res + " in " + s + " " + str);
            }

        }
        MongoDatabase db = mongoClient.getDatabase("DHMZObradeno");
        for(String str : years){
            String res = db.getCollection(str).createIndex(Indexes.descending("vrijeme"));
            System.out.println("created index : "+ res + " in " + str + " " + "DHMZObradeno");
        }
    }

}
