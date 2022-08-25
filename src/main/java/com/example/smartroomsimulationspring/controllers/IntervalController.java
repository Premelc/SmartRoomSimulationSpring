package com.example.smartroomsimulationspring.controllers;

import com.example.smartroomsimulationspring.Simulations.*;
import com.example.smartroomsimulationspring.dataset.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class IntervalController {
    public static String priorityRoom;
    public static Long intervalGlobal;
    public static List<Thread> threads = new ArrayList<>();

    @GetMapping("/simulator/{interval}/{roomId}")
    public String setInterval(@PathVariable Long interval , @PathVariable String roomId) {
        try {
            for (Thread t : threads) {
                System.out.println(t.getName() + " Trying to close");
                t.interrupt();
            }
            priorityRoom = roomId;
            intervalGlobal = interval;
            Thread t0 = new Simulator(Filenames.AdriaCollectionName , interval , true);
            Thread t1 = new Simulator(Filenames.AdriaCollectionName, interval , false);
            Thread t2 = new Simulator(Filenames.DHMZObradenoCollectionName, interval , false);
            threads.add(t0);
            threads.add(t1);
            threads.add(t2);
            t0.start();
            t1.start();
            t2.start();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return e.getMessage();
        }
        return "Success, changed interval to: " + interval;
    }

    @GetMapping("/")
    String hello() {
        return "This tutorial is the best. All hail the great Premo.";
    }
}
