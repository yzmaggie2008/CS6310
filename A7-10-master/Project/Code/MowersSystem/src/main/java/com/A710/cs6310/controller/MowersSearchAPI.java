package com.a710.cs6310.controller;

import com.a710.cs6310.model.form.SystemStatus;
import com.a710.cs6310.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MowersSearchAPI {

    @Autowired
    SimulationService ss;

    @GetMapping("/read/{file}")
    public SystemStatus readFileAndInitial(@PathVariable("file") String filename) {
        System.out.println("read file: " + filename);
        return ss.readFile(filename);
    }

    @GetMapping("/simulate/next")
    public SystemStatus simulateNext() {
        System.out.println("simulate next");
        return ss.getNextTurnStatus();
    }

    @GetMapping("/simulate/all")
    public SystemStatus simulateAll() {
        System.out.println("simulate next");
        return ss.getFinalTurnStatus();
    }

    @GetMapping("/simulate/quit")
    public SystemStatus quitRequest() {
        System.out.println("quit simulation request");
        return ss.quitSimulation();
    }


}
