package com.example.smartroomsimulationspring.Operations;

import com.example.smartroomsimulationspring.DB.*;
import com.example.smartroomsimulationspring.dataset.*;
import com.mongodb.client.*;
import org.bson.*;

import java.sql.*;

public class Warning {

    public static String rule1 = "Window is open and air condition is running while guest is not present in the room";

    public static void checkRoomStateAgainstRules(AdriaIndoorDataset roomState , Timestamp ts){
        checkRoomStateAgainstRuleOne(roomState , ts);
    }

    public static void checkRoomStateAgainstRuleOne(AdriaIndoorDataset roomState , Timestamp ts) {
        if (roomState.getPrisutnost() != 1 && roomState.getStatusKlime() != 0 && roomState.getProzor() != 0) {
            MongoClient mongoClient = Connect.getClient().mongoClient;
            System.out.println("WARNING: room " + roomState.getRoomName() + "Has broken rule: " + rule1);
            MongoDatabase SmartRoomTrialDb = mongoClient.getDatabase("2022");
            MongoCollection<Document> Collection = SmartRoomTrialDb.getCollection("Warnings");
            Collection.insertOne(WarningDocument.createDoc(roomState.getRoomName(),rule1 , ts , 1));
        }
    }
}
