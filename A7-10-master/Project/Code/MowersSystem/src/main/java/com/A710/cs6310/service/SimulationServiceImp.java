package com.a710.cs6310.service;

import com.a710.cs6310.common.Direction;
import com.a710.cs6310.dao.MowersSystemDAO;
import com.a710.cs6310.model.Mower;
import com.a710.cs6310.model.MowersSystem;
import com.a710.cs6310.model.SystemState;
import com.a710.cs6310.model.form.SystemStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class SimulationServiceImp implements SimulationService {
    private static final String RESOURCE_DATA = "src/main/resources/data/";
    private MowersSystem ms = MowersSystemDAO.mowersSystem;
    int trackTurnsCompleted = 0;
    Boolean showState = Boolean.FALSE;

    // upload file and return the origin system status object to controller layer
    @Override
    public SystemStatus readFile(String filename) {
        // to do: add the check of state if not initial, give message simulation is on going
        // solution 1: mute the upload button on UI if state is notinitial
        //  todo 归零
        // upload file begin here
        uploadStartingFile(ms, filename);

        //read data from ms
        SystemStatus ss = getCurrentSystemStatus();
        ss.setSummaryInfo("-");
        return ss;
    }

    // request next TurnStatus, and return system status
    //if reach max turn, send message, and change state to "end"
    @Override
    public SystemStatus getNextTurnStatus() {
        //check system state: uninital, maxturn, end
        //UI solution: mute nextturn button is maxturn reach,
        //set summary info to final report info
        //halt the simulation when all mowers crash
        if (ms.getCountAliveMower() == 0) {
            //do something, break;
        }

        //Halt the simulation run at the appropriate time
        if (ms.getCountMowGrass() == ms.getTotalGrass()) {
            //do something, break;
        }
        //System.out.println("===================+===================");
        //System.out.println("turn "+ turns +" ");

        SingleSimulationTurn();
        //to do: change message
        //read data from ms
        SystemStatus ss = getCurrentSystemStatus();
        ss.setSummaryInfo("-");
        //to do check return maxturn:ss.setSummaryInfo("final report: ");
        return ss;

    }


    @Override
    public SystemStatus getFinalTurnStatus() {
        // TODO need to modify, check corner case
        for (int turns = ms.getCurrentTurn(); turns <= ms.getMaxTurn(); turns++) {
            if (ms.getCountAliveMower() == 0) {
                break;
            }
            //Halt the simulation run at the appropriate time
            if (ms.getCountMowGrass() == ms.getTotalGrass()) {
                break;
            }
            SingleSimulationTurn();

        }
        //to do: change message
        //read data from ms
        SystemStatus ss = getCurrentSystemStatus();
        ss.setSummaryInfo("final report: ");
        return ss;

    }

    @Override
    public SystemStatus quitSimulation() {
        // TODO need to modify
        // to do : final report
        SystemStatus ss = getCurrentSystemStatus();
        ss.setSummaryInfo("final report: ");
        return ss;
    }


    //helper methods:
    //
    // read file to initialize attribute
    // File class, ParsInputToFile class is not created but only use this method for input
    public static void uploadStartingFile(MowersSystem ms, String testFileName) {
        final String DELIMITER = ",";
        String file = RESOURCE_DATA + testFileName;
        try {

            Scanner takeCommand = new Scanner(new File(file));
            String[] tokens;
            int i, j, k;

            // read in the lawn information
            tokens = takeCommand.nextLine().split(DELIMITER);
            int lawnWidth = Integer.parseInt(tokens[0]);
            tokens = takeCommand.nextLine().split(DELIMITER);
            int lawnHeight = Integer.parseInt(tokens[0]);

            // generate the lawn information
            int[][] lawnCurrent = new int[lawnWidth][lawnHeight];
            ms.setLawnCurrent(lawnCurrent);


            for (i = 0; i < lawnWidth; i++) {
                for (j = 0; j < lawnHeight; j++) {
                    lawnCurrent[i][j] = 1;
                }
            }
            int totalGrass = lawnHeight * lawnWidth;


            // read in the lawnmower starting information
            tokens = takeCommand.nextLine().split(DELIMITER);
            int countAliveMower = Integer.parseInt(tokens[0]);


            int countMowGrass = 0;
            List<Mower> mowers = new ArrayList<>();
            int[] mowerX = new int[countAliveMower];
			int[] mowerY = new int[countAliveMower];
			for (k = 1; k <= countAliveMower; k++) {
                tokens = takeCommand.nextLine().split(DELIMITER);

                mowerX[k - 1] = Integer.parseInt(tokens[0]);
                mowerY[k - 1] = Integer.parseInt(tokens[1]);

                String mowerDirection = tokens[2].toUpperCase();
                int choiceStrategy = Integer.parseInt(tokens[3]);

                //Initialize the mower
                Mower mower = new Mower();
                mower.setCrash(false);
                mower.setId(k);
                mower.setDir(Direction.valueOf(mowerDirection));
                mower.setStrategyId(choiceStrategy);

                // mow the grass at the initial location
                lawnCurrent[mowerX[k - 1]][mowerY[k - 1]] = -k;// mower value -id
                countMowGrass++;

                //add mower to list
                mowers.add(mower);
            }
            ms.setMowers(mowers);
            // read in the crater information
            tokens = takeCommand.nextLine().split(DELIMITER);
            int numCraters = Integer.parseInt(tokens[0]);
            totalGrass -= numCraters;
            for (k = 0; k < numCraters; k++) {
                tokens = takeCommand.nextLine().split(DELIMITER);

                // place a crater at the given location
                lawnCurrent[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[1])] = 2;
            }

            //read in maxTurn
            tokens = takeCommand.nextLine().split(DELIMITER);

            ms.setMaxTurn(Integer.parseInt(tokens[0]));
            ms.setTotalGrass(totalGrass);
            ms.setCountAliveMower(countAliveMower);
            ms.setCountMowGrass(countMowGrass);
            ms.setMowerX(mowerX);
            ms.setMowerY(mowerY);
            ms.getStates().get(0).setGameState(SystemState.uploadfile.toString());

//		            //record lawn info
//		            lawnCurrent.setLawnGrid(lawnInfo);

            takeCommand.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            ms.getStates().get(0).setMessage("ERROR: Test scenario file error or file name not found." + System.lineSeparator() + e.toString());

        }
    }

    SystemStatus getCurrentSystemStatus() {
        SystemStatus ss = new SystemStatus();
        //read data from ms
        ss.setTurn(ms.getCurrentTurn());
        ss.setGameState(ms.getStates().get(ms.getStates().size() - 1).getGameState());
        ss.setBody(ms.getStates().get(ms.getStates().size() - 1).getBody());
        ss.setMessage(ms.getStates().get(ms.getStates().size() - 1).getMessage());
        ss.setDigitLawn(ms.getLawnCurrent());
        ss.setNumOfMower(ms.getMowers().size());
        //ss.setNumOfGopher();    need to add later when ms has gopher
        ss.setMaxTurn(ms.getMaxTurn());
        ss.setCountAliveMower(ms.getCountMowGrass());
        ss.setTotalGrass(ms.getTotalGrass());
//			ss.setSummaryInfo("-");
        return ss;
    }


    // to do:need to check
    void SingleSimulationTurn() {
        try {

            trackTurnsCompleted = ms.getCurrentTurn();
            List<Mower> mowers = ms.getMowers();
            for (int k1 = 1; k1 <= mowers.size(); k1++) {
                Mower mower = mowers.get(k1 - 1);
                if (mower.isCrash()) {
                    continue;
                }
//	                System.out.println("m"+k1+" strategy:"+mower.getStrategyId());
                //choose MovingStrategy strategy;
                if (mower.getStrategyId() == 0) {
                    MovingStrategy strategy = new RandamStrategy();
                    ms.pollMowerForAction(strategy, k1);
                    ms.validateMowerAction(strategy, k1);
                    //(9) Display the appropriate information during and after the simulation run
                    ms.displayActionAndResponses(strategy, k1);
                    if (showState) {
                        ms.renderLawn(strategy);
                    }

                } else {
                    MovingStrategy strategy = new OptimizeStrategy();
                    ms.pollMowerForAction(strategy, k1);
                    ms.validateMowerAction(strategy, k1);
                    ms.displayActionAndResponses(strategy, k1);
                    if (showState) {
                        ms.renderLawn(strategy);
                    }
                }
                // to do need to check maxturn
                ms.setCurrentTurn(ms.getCurrentTurn() + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
            // todo: add this to message
        }
    }

}
