package com.example.smartroomsimulationspring.dataset;

import com.example.smartroomsimulationspring.DB.*;
import org.bson.*;
import org.bson.types.*;

import java.sql.*;

public class WarningDocument implements DocumentI {
    public static Document createDoc(String roomName , String rule , Timestamp ts){
        Document doc = new Document("_id" , new ObjectId());
        doc.append("roomName" , roomName)
                .append("timestamp" , ts.getTime())
                .append("message" , rule);
        return doc;
    }
}
