/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ai;

/**
 *
 * @author Mitchell
 */
public class queen {

    public static int numberBetterStates = 0; // current state heuristic value

    public static int currentHeuristic = 0; // current state heuristic value

    public static int lowestHeuristic = 0; // Set to 99 so anything is lower than it

    public static int heuristic = 0; // current heuristic value

    public static int conflictsDS = 0; // Note the conflicts are double of the actual H value because [0][0] and [1][1] would both hit

    public static int conflictsLR = 0; // Note the conflicts are double of the actual H value because [0][0] and [1][1] would both hit

    public static int conflictsDD = 0; // Note the conflicts are double of the actual H value because [0][0] and [1][1] would both hit

    public static int restarts = 0; // holds the amount of restarts

    public static int[][] currentBoard = new int[8][8]; // current state board

    public static int[][] board = new int[8][8]; // stores original board state

    public static int[][] lowestBoard = new int[8][8]; // lowest heuristic board

    public static int currentColumn = 0; // current H value

    public static int currentRow = 0; // current H value

    public static int statesChanged = 0; //number of states changed during the run of the program

    public static boolean solutionFound = false; //if solution is found

    public static void main(String[] args) {

        random(currentBoard);

        conflictFinder();

        System.out.println("Current h: " + currentHeuristic);
        System.out.println("Current state");
        displayBoard(currentBoard);

        while (true) {
            if (solutionFound == false) {
                neighborState();
            } else if (solutionFound == true) {
                System.out.println("\n");
                System.out.println("Current state");
                displayBoard(lowestBoard);
                System.out.println("Solution Found!");
                System.out.println("State changes: " + statesChanged);
                System.out.println("Restarts: " + restarts);
                break;
            }
        }

    }

    public static int[][] random(int[][] randomBoard) {
        for (int i = 0; i < 8; i++) { //populate the chessboard with queens
            int x = (int) (Math.random() * (7 - 0 + 1) + 0);
            randomBoard[x][i] = 1; // pleaces 1 queen on a random row of each column
        }
        return (randomBoard);
    }

    public static void displayBoard(int[][] displayBoard) {
        for (int i = 0; i < 8; i++) { //prints the board
            for (int j = 0; j < 8; j++) {
                if (j == 7) {
                    System.out.println(displayBoard[i][j]);
                } else {
                    System.out.print(displayBoard[i][j] + ",");
                }

            }
        }
    }

    public static void clearboard(int[][] clearboard) {
        for (int i = 0; i < 8; i++) { //prints the board
            for (int j = 0; j < 8; j++) {
                clearboard[i][j] = 0;
            }
        }
    }

    public static void conflictFinder() {

        currentHeuristic = 0; // resest all values when called so they dont add up
        conflictsDS = 0;
        conflictsDD = 0;
        conflictsLR = 0;

        for (int i = 0; i < 8; i++) {

            int row = queenFinder(i); // gets the row of the current queen to get the [row][col]

            diagonalSame(row, i);

            diagonalDifferent(row, i);

            leftRight(row, i);

            if (i == 7) {
                currentHeuristic += conflictsDS / 2;
                currentHeuristic += conflictsDD / 2;
                currentHeuristic += conflictsLR / 2;

            }
        }
    }

    public static int queenFinder(int column) { // column is known 

        for (int i = 0; i < 8; i++) {
            if (currentBoard[i][column] == 1) {

                return (i); // finds the row the queen is in

            }
        }
        return (0); // should never reach this
    }

    public static void diagonalSame(int row, int column) {

        boolean bounds = true;

        int nextRow = row;

        int nextColumn = column;

        while (bounds) { // this is for the upper left-up

            nextRow++;

            nextColumn++;

            // down row and to the right column
            if (nextColumn == 8 || nextRow == 8) { // check for bounds
                break; // kill the while
            }
            if (currentBoard[nextRow][nextColumn] == 1) {
                conflictsDS++;
                break; // kill the while
            }

        }

        nextRow = row;

        nextColumn = column;

        while (bounds) { // this is for the right-down

            nextRow--;

            nextColumn--;
            // up row to the left column
            if (nextColumn == -1 || nextRow == -1) { // check for bounds
                break; // kill the while
            }

            if (currentBoard[nextRow][nextColumn] == 1) {
                break; // kill the while
            }

        }

    }

    public static void diagonalDifferent(int row, int column) {

        boolean bounds = true;

        int nextRow = row;

        int nextColumn = column;

        while (bounds) { // this is for the right-up

            nextRow--;

            nextColumn++;

            // up row and to the right column
            if (nextColumn == 8 || nextRow == -1) { // check for bounds
                break; // kill the while
            }

            if (currentBoard[nextRow][nextColumn] == 1) {
                conflictsDD++;
                break; // kill the while
            }

        }

        nextRow = row;

        nextColumn = column;

        while (bounds) { // this is for the right-down

            nextRow++;

            nextColumn--;

            // down row and to the right column
            if (nextColumn == -1 || nextRow == 8) { // check for bounds
                break; // kill the while
            }

            if (currentBoard[nextRow][nextColumn] == 1) {
                conflictsDD++;
                break; // kill the while
            }

        }
    }

    public static void leftRight(int row, int column) {

        boolean bounds = true;

        int nextColumn = column;

        while (bounds) { // right

            nextColumn++;

            //to the right column
            if (nextColumn == 8) { // check for bounds
                break; // kill the while
            }
            if (currentBoard[row][nextColumn] == 1) {
                conflictsLR++;
                break; // kill the while
            }

        }

        nextColumn = column;

        while (bounds) { // left

            nextColumn--;

            //to the left column
            if (nextColumn == -1) { // check for bounds
                break; // kill the while
            }
            if (currentBoard[row][nextColumn] == 1) {
                conflictsLR++;
                break; // kill the while
            }

        }

    }

    public static void neighborState() {

        numberBetterStates = 0;

        heuristic = currentHeuristic; // stores the heuristic before neighbor states

        lowestHeuristic = currentHeuristic; // lowest will be the current when the method is called

        boardCopy(board, currentBoard); // stores the current board into a new board

        int row = 0; // declares and intailizes int with value 0

        for (int i = 0; i < 8; i++) { // for loop
            row = queenFinder(i);// finds the row the queen at column i
            rowRunner(row, i);
        }

        if (lowestHeuristic == 0) {
            System.out.println("Neighbors found with lower h:" + numberBetterStates);
            System.out.println("Setting new current state");
            System.out.println("\n");
            solutionFound = true;
            statesChanged++;
        } else if (numberBetterStates != 0) { // if true
            boardCopy(currentBoard, lowestBoard); // stores the current board into a new board
            currentHeuristic = lowestHeuristic;
            System.out.println("Neighbors found with lower h:" + numberBetterStates);
            System.out.println("Setting new current state");
            System.out.println("\n");
            System.out.println("Current h:" + currentHeuristic);
            System.out.println("Current state");
            displayBoard(currentBoard);
            statesChanged++;

        } else if (numberBetterStates == 0) { //if the no better states are found
            System.out.println("Neighbors found with lower h:" + numberBetterStates);
            System.out.println("RESTART");
            restarts++;
            statesChanged++;
            clearboard(currentBoard);
            random(currentBoard); // generate random board
            conflictFinder();
            System.out.println("\n");
            System.out.println("Current h:" + currentHeuristic);
            System.out.println("Current state");
            displayBoard(currentBoard);
        }

    }// column is known 

    public static void rowRunner(int row, int column) {

        int downwardRow = row;

        while (true) {

            downwardRow = downwardRow + 1;

            if (downwardRow == 8) {
                break;
            } else {
                currentBoard[downwardRow - 1][column] = 0;
                currentBoard[downwardRow][column] = 1;
                conflictFinder();
                if (currentHeuristic < lowestHeuristic) {
                    lowestHeuristic = currentHeuristic;
                    numberBetterStates++;
                    boardCopy(lowestBoard, currentBoard);
                } else if (currentHeuristic < heuristic) {
                    numberBetterStates++;
                }
            }

        }

        boardCopy(currentBoard, board); // resets the board for the next column or direction for row

        int upwardRow = row;

        while (true) {

            upwardRow = upwardRow - 1;

            if (upwardRow == -1) {
                break;
            } else {
                currentBoard[upwardRow + 1][column] = 0;
                currentBoard[upwardRow][column] = 1;
                conflictFinder();
                if (currentHeuristic < lowestHeuristic) {
                    lowestHeuristic = currentHeuristic;
                    numberBetterStates++;
                    boardCopy(lowestBoard, currentBoard);
                } else if (currentHeuristic < heuristic) {
                    numberBetterStates++;
                }
            }

        }

        boardCopy(currentBoard, board); // resets the board for the next column or direction for row

    }

    public static void boardCopy(int[][] a, int[][] b) {
        for (int i = 0; i < 8; i++) { //prints the board
            for (int j = 0; j < 8; j++) {
                a[j][i] = b[j][i]; // stores copy of the current board
            }
        }
    }
}
