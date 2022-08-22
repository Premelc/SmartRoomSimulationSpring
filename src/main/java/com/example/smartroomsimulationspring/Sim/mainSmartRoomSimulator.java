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
        //MongoClient mongoClient = Connect.mongoConnect();

        MongoClient mongoClient = Connect.getClient().mongoClient;
        Thread t1 = new Simulator(Filenames.AdriaCollectionName , 50000L ,false);
        Thread t2 = new Simulator(Filenames.DHMZObradenoCollectionName , 50000L , false);
        //Thread t3 = new Simulator(Filenames.DHMZBaseCollectionName ,mongoClient);

        t1.start();
        t2.start();
        //t3.start();

        //archive(mongoClient);
        //createIndexes(mongoClient);


        long end1 = System.currentTimeMillis();
        System.out.println("Done");
        System.out.println("Executed in: " + (end1 - start1) + "ms");
        return;
    }

    public static void archive(MongoClient mongoClient){
        InsertDocuments.insertAllAlternate(DHMZObradenoSimulator.DHMZObradenoLog, Filenames.DHMZObradenoCollectionName , Filenames.DHMZObradenoFileNames , Filenames.DHMZObradenoFolderNames,  Filenames.DHMZObradeniRes);
        InsertDocuments.insertAllAlternate(AdriaLog, Filenames.AdriaCollectionName, Filenames.adriaRoomNames , Filenames.adriaFolderNames,  Filenames.AdriaRes);
        createIndexes(mongoClient);
        //TODO dodati insertAllAlternate za DHMZBase podatke
    }

    public static void createIndexes(MongoClient mongoClient){
        String[] years = {"2013" , "2014" , "2015" , "2016" , "2017" , "2018" , "2019" , "2020" , "2021"};

        for(String s : years){
            MongoDatabase db = mongoClient.getDatabase(s);
            for(String str : Filenames.adriaRoomNames){
                //db.getCollection(str).drop();
                String c =  str.substring(5 , str.length()-4);
               String res = db.getCollection(c).createIndex(Indexes.descending("vrijeme"));
               System.out.println("created index : "+ res + " in " + s + " " + str);
            }

            String res = db.getCollection("DHMZObradeno").createIndex(Indexes.descending("vrijeme"));
            System.out.println("created index : "+ res + " in " + s + " " + "DHMZObradeno");
        }

    }

}
