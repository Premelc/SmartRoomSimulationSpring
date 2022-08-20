package com.example.smartroomsimulationspring.DB;

import com.example.smartroomsimulationspring.dataset.*;
import org.bson.*;
import org.bson.types.*;

import java.sql.*;

public class AdriaIndoorDocument implements DocumentI {
    public static Document createDoc(AdriaIndoorDataset data){
        Document doc = new Document("_id" , /*data.get_id()*/ new ObjectId());
            doc.append("vrijeme" , data.getTimestamp().getTime())
                    .append("zadana" , data.getZadana())
                    .append("izmjerena" ,data.getIzmjerena())
                    .append("statusKlime" , data.getStatusKlime())
                    .append("brzinaPuhanja" ,data.getBrzinaPuhanja())
                    .append("ventil" , data.getVentil())
                    .append("prisutnost" , data.getPrisutnost())
                    .append("prozor" ,data.getProzor())
                    .append("modKlime" , data.getModKlime())
                    .append("wcSet" , data.getWcSet())
                    .append("wcMjerenja" , data.getWcMjerena())
                    .append("_class" , "com.premelc.smartroommonitoringapp.models.AdriaIndoor");
        return doc;
    }
    public static Document createDoc(AdriaIndoorDataset data , Timestamp ts){
        Document doc = new Document("_id" , /*data.get_id()*/ new ObjectId());
        doc.append("vrijeme" , ts.getTime())
                .append("zadana" , data.getZadana())
                .append("izmjerena" ,data.getIzmjerena())
                .append("statusKlime" , data.getStatusKlime())
                .append("brzinaPuhanja" ,data.getBrzinaPuhanja())
                .append("ventil" , data.getVentil())
                .append("prisutnost" , data.getPrisutnost())
                .append("prozor" ,data.getProzor())
                .append("modKlime" , data.getModKlime())
                .append("wcSet" , data.getWcSet())
                .append("wcMjerenja" , data.getWcMjerena())
                .append("_class" , "com.premelc.smartroommonitoringapp.models.AdriaIndoor");
        return doc;
    }
}
