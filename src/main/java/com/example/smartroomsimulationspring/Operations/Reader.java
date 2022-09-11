package com.example.smartroomsimulationspring.Operations;

import com.example.smartroomsimulationspring.DB.*;
import com.example.smartroomsimulationspring.dataset.*;
import com.mongodb.client.*;
import org.bson.*;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Reader {

    public static List<Document> SingleLine(WrappedReader br , Timestamp ts , String collectionName){
        List<Document> list = new ArrayList<>();
        MongoClient mongoClient = Connect.getClient().mongoClient;
        try{
            String s = br.getBr().readLine();

            String[] attributes = s.split(",");

            if (attributes.length != 0) {
                if(collectionName.equals(Filenames.AdriaCollectionName)){
                    //Dodatno da se u atribute doda broj sobe za AdriaIndoorDataset
                    s= s + "," + br.getFileName().substring(5 , br.getFileName().length()-4);
                    attributes = s.split(",");
                    AdriaIndoorDataset roomState = new AdriaIndoorDataset(attributes);
                    Document doc = AdriaIndoorDocument.createDoc(roomState , ts);
                    Warning.checkRoomStateAgainstRules(roomState , ts);
                    list.add(doc);
                }
                else if(collectionName.equals(Filenames.DHMZObradenoCollectionName)){
                    Document doc = DHMZObradenoDocument.createDoc(new DHMZObradenoDataset(attributes) , ts);
                    list.add(doc);
                }
            }
            StringManipulator.cleanFirstRow(br);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return list;
    }

    public static List<Document> fullFile(WrappedReader br , String collectionName){
        List<Document> list = new ArrayList<>();
        try{
            String s = br.getBr().readLine();

            while(s != null){

                String[] attributes = s.split(",");

                if (attributes.length != 0) {
                    if(collectionName.equals(Filenames.AdriaCollectionName)){
                        //Dodatno da se u atribute doda broj sobe za AdriaIndoorDataset
                        s= s + "," + br.getFileName().substring(5 , br.getFileName().length()-4);
                        attributes = s.split(",");
                        Document doc = AdriaIndoorDocument.createDoc(new AdriaIndoorDataset(attributes));
                        list.add(doc);
                    }
                    else if(collectionName.equals(Filenames.DHMZObradenoCollectionName)){
                        Document doc = DHMZObradenoDocument.createDoc(new DHMZObradenoDataset(attributes));
                        list.add(doc);
                    }
                }
                //StringManipulator.cleanFirstRow(br);
                s = br.getBr().readLine();
            }
            }catch (IOException ioe){
            ioe.printStackTrace();
        }

        return list;
    }

    public static List<Document> fullFileAlternate(WrappedReader br , String collectionName){
        List<Document> list = new ArrayList<>();
        try{
            String s = br.getBr().readLine();

            while(s != null){

                String[] attributes = s.split(",");

                if (attributes.length != 0) {
                    if(collectionName.equals(Filenames.AdriaCollectionName)){
                        //Dodatno da se u atribute doda broj sobe za AdriaIndoorDataset
                        s= s + "," + br.getFileName().substring(5 , br.getFileName().length()-4);
                        attributes = s.split(",");
                        Document doc = AdriaIndoorDocument.createDoc(new AdriaIndoorDataset(attributes));
                        list.add(doc);
                    }
                    else if(collectionName.equals(Filenames.DHMZObradenoCollectionName)){
                        Document doc = DHMZObradenoDocument.createDoc(new DHMZObradenoDataset(attributes));
                        list.add(doc);
                    }
                }
                //StringManipulator.cleanFirstRow(br);
                s = br.getBr().readLine();
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

        return list;
    }

    public static List<Document> SingleLine(WrappedReader br , String collectionName){
        List<Document> list = new ArrayList<>();
        try{
            String s = br.getBr().readLine();

            String[] attributes = s.split(",");

            if (attributes.length != 0) {
                if(collectionName.equals(Filenames.AdriaCollectionName)){
                    //Dodatno da se u atribute doda broj sobe za AdriaIndoorDataset
                    s= s + "," + br.getFileName().substring(5 , br.getFileName().length()-4);
                    attributes = s.split(",");
                    Document doc = AdriaIndoorDocument.createDoc(new AdriaIndoorDataset(attributes));
                    list.add(doc);
                }
                else if(collectionName.equals(Filenames.DHMZObradenoCollectionName)){
                    Document doc = DHMZObradenoDocument.createDoc(new DHMZObradenoDataset(attributes));
                    list.add(doc);
                }
            }
            StringManipulator.cleanFirstRow(br);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return list;
    }

    public static String[] parseDateTime(String s){
        String[]str = s.split("-");
        str[0] = str[0] + "-" + str[1] + "-" + str[2];
        str[1] = str[3];
        return str;
    }

    public static String[] parseDHMZbaseDateTime(String s){
        String[]str = s.split(" ");
        return str;
    }
}
