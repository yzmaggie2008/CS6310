package com.a710.cs6310.service;

import com.a710.cs6310.common.Point;
import com.a710.cs6310.model.Gopher;
import com.a710.cs6310.model.Mower;
import com.a710.cs6310.model.MowersSystem;

import java.util.List;
import java.util.Set;

public class GopherMovingStrategyImp implements GopherMovingStrategy {
    private MowersSystem ms;
    private int[][] currentLawn;
    private List<Mower> mowers;
    private Set<Integer> traceSet; // 记录别的gopher已经追踪的mower的ID
    private List<Gopher> gophers;
    private int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    private int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};
    private int n, m;

    @Override
    public void gopherAction(int id) {

    }

    Point BFS(List<Mower> mowers, Set<Integer> traceSet) {
        return null;
    }
}
