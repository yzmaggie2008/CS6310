package com.a710.cs6310.service;

import com.a710.cs6310.model.form.SystemStatus;

public interface SimulationService {
    SystemStatus readFile(String filename);

    SystemStatus getNextTurnStatus();

    SystemStatus getFinalTurnStatus();

    SystemStatus quitSimulation();
}
