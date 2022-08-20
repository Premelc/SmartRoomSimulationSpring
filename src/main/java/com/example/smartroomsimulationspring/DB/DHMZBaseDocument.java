package com.example.smartroomsimulationspring.DB;

import com.example.smartroomsimulationspring.dataset.*;
import org.bson.*;
import org.bson.types.*;

import java.sql.*;

public class DHMZBaseDocument implements DocumentI {

    public static Document createDoc (DHMZBaseDataset data , Timestamp ts){
        Document doc = new Document("_id" , new ObjectId());
        return doc;
    }

    public static Document createDoc (DHMZBaseDataset data){
        Document doc = new Document("_id" , new ObjectId());
        return doc;
    }

}
