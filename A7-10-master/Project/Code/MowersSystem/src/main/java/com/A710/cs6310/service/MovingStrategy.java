package com.a710.cs6310.service;


public interface MovingStrategy {
    void pollMowerForAction(int id);

    void validateMowerAction(int id);

    void displayActionAndResponses(int id);

    void renderLawn();

    void renderHorizontalBar(int size);
}

