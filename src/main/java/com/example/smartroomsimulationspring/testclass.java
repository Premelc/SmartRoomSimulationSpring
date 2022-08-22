package com.example.smartroomsimulationspring;

public class testclass {

    public static void main(String[] args) {
        String str = "room_001.csv";
        System.out.println(str.substring(0,5) + "001" + str.substring(8,str.length()));
    }
}
