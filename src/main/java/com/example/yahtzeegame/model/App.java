package com.example.yahtzeegame.model;

import java.util.*;
import java.io.*;

// this is for cli to test the game

public class App {

    /**
     * *********************************************************************
     * Function Name: main
     * Purpose: This function serves as the entry point for the application. It
     * initializes the game, checks if the user wants to load a saved game, and runs
     * the game loop until the game is over.
     * Parameters:
     * - args: An array of String arguments passed to the program from the command
     * line (passed by reference, not modified).
     * Return Value: None.
     * Algorithm:
     * 1. Create instances of Human and Computer objects.
     * 2. Create a list of Player objects, containing the human and computer
     * players.
     * 3. Create a Game object, passing in a new ScoreCard, a starting round of 1,
     * and the list of players.
     * 4. Print a welcome message.
     * 5. Check if the user wants to load a previously saved game.
     * 6. If the user wants to load a game, get the serialized game data,
     * deserialize it, and reinitialize the game object.
     * 7. Enter the game loop, playing rounds until the game is over.
     * 8. After each round, save the current state of the game by serializing the
     * game data.
     * Reference: None.
     * *********************************************************************
     */
    public static void main(String[] args) {
        // Create instances of Human and Computer objects.
        Human human = new Human();
        Computer computer = new Computer();

        // Create a list of Player objects, containing the human and computer players.
        List<Player> players = new ArrayList<>();
        players.add(human);
        players.add(computer);

        // Create a Game object, passing in a new ScoreCard, a starting round of 1, and
        // the list of players.
        Game game = new Game(new ScoreCard(), 1, players);

        System.out.println("Welcome to the Yahtzee Game:\n");

        // Check if the user wants to load a previously saved game.
        if (ioFunctions.userWantsToLoadGame()) {
            // Get the serialized string game data from the user.
            // The file contents we got from getSerial is stored in serial
            String serial = ioFunctions.getSerial();

            // Deserialize the game data and reinitialize the game object to restore the
            // previous state.
            game = Game.deserialize(serial);
        }

        // Game loop: continue playing rounds until the game is over.
        while (!game.isOver()) {
            // Play a single round and update the game object.
            game = game.playRound();

            // Save the current state of the game after each round by serializing the game
            // data.
            ioFunctions.saveGameProcedure(game.serialize());
        }

        // Once the game is over, display the final scores.
        game.showScores();

        // Check if the game is a draw (if no winner).
        if (game.isDraw()) {
            System.out.println("It's a draw!");
        } else {
            // If there is a winner, retrieve the winner from the scorecard.
            Optional<Player> winner = game.getScoreCard().getWinner();
            System.out.println("The winner is " + winner.get().getName() + "!");
        }
    }
}