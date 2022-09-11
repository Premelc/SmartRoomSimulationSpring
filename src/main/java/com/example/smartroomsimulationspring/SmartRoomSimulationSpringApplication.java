package com.example.smartroomsimulationspring;

import com.example.smartroomsimulationspring.Sim.*;
import com.example.smartroomsimulationspring.controllers.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartRoomSimulationSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartRoomSimulationSpringApplication.class, args);
        //IntervalController.changeInterval(1000L , "001");
        mainSmartRoomSimulator.SimulationInit();
    }

}
