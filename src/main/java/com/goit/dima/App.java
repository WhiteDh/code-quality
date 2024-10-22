package com.goit.dima;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.Random;



public class App {
    private static final char EMPTY_CELL = ' ';
    private static final char SYMBOL_PLAYER = 'X';
    private static final char SYMBOL_COMPUTER = 'O';
    private static final int SIZE = 9;
    private static final int[][] WINNING_COMBINATIONS = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}  // diagonales
    };

    private static char[] board = new char[SIZE];
    private static Scanner scanner = new Scanner(System.in);

    private static final Logger LOGGER = Logger.getLogger(App.class.getName()); //using log instead sout
    private static final Random random = new Random();


    public static void main(String[] args) {
        startGame();
    }

    // A method that starts the game and controls its flow
    private static void startGame() {
        LOGGER.info("Welcome to Tic-Tac-Toe! Enter a number from 1 to 9 to select a box.");
        initializeBoard();
        while (true) {
            printBoard();
            handlePlayerMove();
            if (checkGameState(SYMBOL_PLAYER) || checkGameState(SYMBOL_COMPUTER)) {
                break; //exit loop if game is over
            }
            handleComputerMove();
        }
        LOGGER.info("Thanks for playing! Created by Shreyas Saha.");
    }

    private static boolean checkGameState(char marker) {
        if (checkWinner(marker)) {
            printBoard();
            if (marker == SYMBOL_PLAYER) {
                LOGGER.info("You won the game!");
            } else {
                LOGGER.info("You lost the game!");
            }
            return true; //game is over
        }
        if (isBoardFull()) {
            printBoard();
            LOGGER.info("It's a draw!");
            return true; //draw
        }
        return false; //continue playing
    }


    //fill the board with empty cells
    private static void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            board[i] = EMPTY_CELL;
        }
    }


    private static void printBoard() {
        String boardString = createBoardString();
        LOGGER.info(boardString); //log the board string
    }

    private static String createBoardString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n ").append(board[0]).append(" | ").append(board[1]).append(" | ").append(board[2]);
        sb.append("\n-----------");
        sb.append("\n ").append(board[3]).append(" | ").append(board[4]).append(" | ").append(board[5]);
        sb.append("\n-----------");
        sb.append("\n ").append(board[6]).append(" | ").append(board[7]).append(" | ").append(board[8]).append("\n");
        return sb.toString(); //return the board string without logging it directly
    }


    private static void handlePlayerMove() {
        int input = getPlayerInput();
        makeMove(input, SYMBOL_PLAYER);
    }


    private static int getPlayerInput() {
        int input;
        while (true) {
            LOGGER.info("Enter a box number (1-9): ");
            input = scanner.nextInt(); //getting the number of cell
            if (isValidMove(input)) {
                return input - 1;
            }
            LOGGER.warning("Invalid input. Try again.");
        }
    }


    private static boolean isValidMove(int input) {
        return input > 0 && input <= SIZE && board[input - 1] == EMPTY_CELL;
    }


    private static void handleComputerMove() {
        int randomMove = getRandomMove();
        makeMove(randomMove, SYMBOL_COMPUTER);
    }


    private static int getRandomMove() {
        int randomMove;
        while (true) {
            //choose the random cell on board
            randomMove = random.nextInt(SIZE);
            if (board[randomMove] == EMPTY_CELL) {
                return randomMove;
            }
        }
    }


    private static void makeMove(int index, char marker) {
        board[index] = marker;
    }


    private static boolean checkWinner(char marker) {
        for (int[] combination : WINNING_COMBINATIONS) {
            if (board[combination[0]] == marker && board[combination[1]] == marker && board[combination[2]] == marker) {
                return true;
            }
        }
        return false;
    }


    private static boolean isBoardFull() {
        for (char cell : board) {
            if (cell == EMPTY_CELL) {
                return false;
            }
        }
        return true;
    }
}
