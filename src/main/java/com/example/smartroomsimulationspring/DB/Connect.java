package com.example.smartroomsimulationspring.DB;

import com.mongodb.*;
import com.mongodb.client.*;

public class Connect {

    private static final String defaultConnection = "mongodb://localhost:27017";
    private static final ConnectionString connectionString = new ConnectionString("mongodb+srv://premelc:premelc1234@smartroommonitoring2022.nye6wof.mongodb.net/?retryWrites=true&w=majority");

    private static Connect connection = null;
    public MongoClient mongoClient;

    private Connect(){
       mongoClient =  mongoConnectToCloud();
       //mongoClient = mongoConnect();
    }

    public static Connect getClient(){
        if(connection == null){
            connection = new Connect();
        }
        return connection;
    }

    //Connect with the default connection string
    private static MongoClient mongoConnect(){
        MongoClient mc = MongoClients.create(defaultConnection);
        return mc;
    }

    private static MongoClient mongoConnectToCloud(){
        MongoClient mc = MongoClients.create(connectionString);
        return mc;
    }

}
