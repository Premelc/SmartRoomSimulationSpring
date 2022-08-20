package com.example.smartroomsimulationspring.controllers;

import com.example.smartroomsimulationspring.Simulations.*;
import com.example.smartroomsimulationspring.dataset.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class IntervalController {

    public static Long intervalGlobal;
    public static List<Thread> threads = new ArrayList<>();

    @GetMapping("/simulator/{interval}")
    public String setInterval(@PathVariable Long interval) {
        try {
            for (Thread t : threads) {
                System.out.println(t.getName() + " Trying to close");
                t.interrupt();
            }

            intervalGlobal = interval;
            Thread t1 = new Simulator(Filenames.AdriaCollectionName, interval);
            Thread t2 = new Simulator(Filenames.DHMZObradenoCollectionName, interval);
            threads.add(t1);
            threads.add(t2);
            t1.start();
            t2.start();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return e.getMessage();
        }
        return "Success, changed interval to: " + interval;
    }
}
