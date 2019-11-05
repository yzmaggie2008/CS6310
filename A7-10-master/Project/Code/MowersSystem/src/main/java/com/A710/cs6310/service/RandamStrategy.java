package com.a710.cs6310.service;

import com.a710.cs6310.common.Direction;
import com.a710.cs6310.dao.MowersSystemDAO;
import com.a710.cs6310.model.Mower;
import com.a710.cs6310.model.MowersSystem;

import java.util.List;
import java.util.Random;

//@Service
public class RandamStrategy implements MovingStrategy {
    private Random randGenerator = new Random();
    private MowersSystem ms = MowersSystemDAO.mowersSystem;
    private int[][] currentLawn = ms.getLawnCurrent();
    private List<Mower> mowers = ms.getMowers();

    String trackAction, trackNewDirection, trackMoveCheck, trackScanResults;

    int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};
    int n = currentLawn.length;
    int m = currentLawn[0].length;

    @Override
    public void pollMowerForAction(int id) {

//		System.out.println("======");
        int moveRandomChoice = randGenerator.nextInt(100);

        if (moveRandomChoice < 10) {
            // do nothing
            trackAction = "pass";
        } else if (moveRandomChoice < 35) {
            // check your surroundings
            trackAction = "scan";
        } else if (moveRandomChoice < 60) {
            // change direction
            trackAction = "steer";
        } else {
            // move forward
            trackAction = "move";
        }
    }


    @Override
    public void validateMowerAction(int id) {

        Mower mower = mowers.get(id - 1);


//		if(mower.isCrash()) {
//			return;
//		}

        int x = ms.getMowerX()[id - 1];
        int y = ms.getMowerY()[id - 1];


        if (trackAction.equals("pass")) {
            trackMoveCheck = "ok";

        } else if (trackAction.equals("scan")) {
            // check your surroundings: scan

            trackMoveCheck = "ok";

            String[] surroundings = new String[8];
            for (int k = 0; k < 8; k++) {
                int xx = x + dx[k];
                int yy = y + dy[k];

                if (xx < 0 || xx >= n || yy < 0 || yy >= m) {
                    surroundings[k] = "fence";
                } else if (currentLawn[xx][yy] < 0) {
                    surroundings[k] = "mower";
                } else if (currentLawn[xx][yy] == 1) {
                    surroundings[k] = "grass";
                } else if (currentLawn[xx][yy] == 0) {
                    surroundings[k] = "empty";
                } else if (currentLawn[xx][yy] == 2) {
                    surroundings[k] = "crater";
                } else {
                    surroundings[k] = "unknown";
                }

                if (k == 0) trackScanResults = surroundings[k];
                else trackScanResults = trackScanResults + "," + surroundings[k];

            }
            mower.setSurroundings(surroundings);

        } else if (trackAction.equals("steer")) {
            // change direction

            trackMoveCheck = "ok";

            if (randGenerator.nextInt(100) < 85) {
                int newdir = (mower.getDir().ordinal() + 1) % 8;
                mower.setDir(Direction.values()[newdir]);
                trackNewDirection = Direction.values()[newdir].toString();
            } else {
                trackNewDirection = mower.getDir().toString();
            }
        } else if (trackAction.equals("move")) {
            // move forward
            currentLawn[x][y] = 0;
            int dirK = mower.getDir().ordinal();
            int xnew = x + dx[dirK], ynew = y + dy[dirK];


            if (xnew < 0 || xnew >= n || ynew < 0 || ynew >= m) {//fence
                mower.setCrash(true);
                ms.setCountAliveMower(ms.getCountAliveMower() - 1);
                trackMoveCheck = "crash";
            } else if (currentLawn[xnew][ynew] == 2) {//crater
                mower.setCrash(true);
                ms.setCountAliveMower(ms.getCountAliveMower() - 1);
                trackMoveCheck = "crash";
            } else if (currentLawn[xnew][ynew] < 0) {//collision with another mower
                mower.setCrash(true);
                ms.setCountAliveMower(ms.getCountAliveMower() - 2);
//            	for(int i=0; i < mowers.size(); i++) {
//            		Mower mowerPre = mowers.get(i);
//            		if(!mowerPre.isCrash() && mowerPre.getPosition().comparePoint(xnew, ynew)) {
//            			mowerPre.setCrash(true);
//            			break;
//            		}
//            	}
                mowers.get(-currentLawn[xnew][ynew] - 1).setCrash(true);
                currentLawn[xnew][ynew] = 0;
                trackMoveCheck = "crash";
            } else {//move successful

                ms.getMowerX()[id - 1] = xnew;
                ms.getMowerY()[id - 1] = ynew;

                trackMoveCheck = "ok";
                if (currentLawn[xnew][ynew] == 1) {//cut grass

                    ms.setCountMowGrass(ms.getCountMowGrass() + 1);
                }
                currentLawn[xnew][ynew] = -id;
            }
        } else {
            mower.setCrash(true);
            trackMoveCheck = "crash";
        }
    }

    @Override
    public void displayActionAndResponses(int id) {
        // display the mower's actions
//		Mower mower = mowers.get(id-1);
//		if(mower.isCrash()) {
//			return;
//		}
        System.out.print("m" + (id - 1) + "," + trackAction);
        if (trackAction.equals("steer")) {
            System.out.println("," + trackNewDirection);
        } else {
            System.out.println();
        }

        // display the simulation checks and/or responses

        if (trackAction.equals("move") || trackAction.equals("steer") || trackAction.equals("pass")) {
//        	System.out.println("status: ");
            System.out.println(trackMoveCheck);
        } else if (trackAction.equals("scan")) {//  //(7) Provide the correct data to the mower when it performs a scan() action;
//        	System.out.println("scan result: ");
            System.out.println(trackScanResults);
        } else {
            System.out.println("action not recognized");
        }

    }


    @Override
    public void renderLawn() {
        int i, j;
        int charWidth = 2 * n + 2;

        // display the rows of the lawn from top to bottom
        for (j = m - 1; j >= 0; j--) {
            renderHorizontalBar(charWidth);

            // display the Y-direction identifier
            System.out.print(j);

            // display the contents of each square on this row
            for (i = 0; i < n; i++) {
                System.out.print("|");

                // the mower overrides all other contents
                if (currentLawn[i][j] < 0) {
                    System.out.print(-currentLawn[i][j] - 1);

                } else {
                    switch (currentLawn[i][j]) {
                        case 0:
                            System.out.print(" ");
                            break;
                        case 1:
                            System.out.print("g");
                            break;
                        case 2:
                            System.out.print("c");
                            break;
                        default:
                            break;
                    }
                }
            }
            System.out.println("|");
        }
        renderHorizontalBar(charWidth);

        // display the column X-direction identifiers
        System.out.print(" ");
        for (i = 0; i < n; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        // display the mower's directions and strategy
        for (int k = 0; k < mowers.size(); k++) {
            Mower mower = mowers.get(k);
            if (mower.isCrash()) {
                continue;
            }
            System.out.println("dir m" + k + ": " + mower.getDir().toString());
            System.out.println("m" + k + " strategy id: " + mower.getStrategyId());
        }
        System.out.println();
    }


    @Override
    public void renderHorizontalBar(int size) {
        System.out.print(" ");
        for (int k = 0; k < size; k++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
