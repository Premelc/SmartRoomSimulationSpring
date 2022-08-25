package com.example.smartroomsimulationspring.Operations;

import com.example.smartroomsimulationspring.DB.*;
import com.example.smartroomsimulationspring.dataset.*;
import com.mongodb.client.*;
import org.bson.*;

import java.sql.*;

public class Warning {

    public static String rule1 = "Window is open and air condition is running while guest is not present in the room";
    public static String rule2 = "Measured temperature is significantly different than the set temperature";
    public static String rule3 = "Water was left on after guest left the room";

    public static void checkRoomStateAgainstRules(AdriaIndoorDataset roomState , Timestamp ts){
        checkRoomStateAgainstRuleOne(roomState , ts);
        checkRoomStateAgainstRuleTwo(roomState , ts);
        checkRoomStateAgainstRuleThree(roomState, ts);
    }

    public static void checkRoomStateAgainstRuleOne(AdriaIndoorDataset roomState , Timestamp ts) {
        if (roomState.getPrisutnost() != 1 && roomState.getStatusKlime() != 0 && roomState.getProzor() != 0) {
            MongoClient mongoClient = Connect.getClient().mongoClient;
            MongoDatabase SmartRoomTrialDb = mongoClient.getDatabase("2022");
            MongoCollection<Document> Collection = SmartRoomTrialDb.getCollection("Warnings");
            Collection.insertOne(WarningDocument.createDoc(roomState.getRoomName(),rule1 , ts , 1));
        }
    }

    public static void checkRoomStateAgainstRuleTwo(AdriaIndoorDataset roomState , Timestamp ts) {
        if (roomState.getIzmjerena() - roomState.getZadana() > 10 || roomState.getIzmjerena() - roomState.getZadana() < -10) {
            MongoClient mongoClient = Connect.getClient().mongoClient;
            MongoDatabase SmartRoomTrialDb = mongoClient.getDatabase("2022");
            MongoCollection<Document> Collection = SmartRoomTrialDb.getCollection("Warnings");
            Collection.insertOne(WarningDocument.createDoc(roomState.getRoomName(),rule2 , ts , 2));
        }
    }

    public static void checkRoomStateAgainstRuleThree(AdriaIndoorDataset roomState , Timestamp ts) {
        if (roomState.getVentil() == 1 && roomState.getPrisutnost() == 0) {
            MongoClient mongoClient = Connect.getClient().mongoClient;
            MongoDatabase SmartRoomTrialDb = mongoClient.getDatabase("2022");
            MongoCollection<Document> Collection = SmartRoomTrialDb.getCollection("Warnings");
            Collection.insertOne(WarningDocument.createDoc(roomState.getRoomName(),rule3 , ts , 3));
        }
    }
}
