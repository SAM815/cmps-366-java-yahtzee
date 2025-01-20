package com.example.yahtzeegame.model;

import java.util.ArrayList;
import java.util.List;

/**
 * *********************************************************************
 * Class Name: SingletonGame
 * Purpose: This class implements the Singleton design pattern to ensure that only one instance of the Game class exists.
 * It provides methods to access and modify the single instance of the Game.
 * *********************************************************************
 */
public class SingletonGame {

    /**
     * *********************************************************************
     * Function Name: getGame
     * Purpose: To provide access to the single instance of the Game class. If the instance does not exist, it creates one.
     * Parameters: None.
     * Return Value: The single instance of the Game class.
     * Algorithm: 
     * 1. Check if the game instance is null.
     * 2. If null, create a new ScoreCard, a list of players (including a Human and a Computer), and instantiate the Game.
     * 3. Return the game instance.
     * Reference: None.
     * *********************************************************************
     */
    public static Game getGame() {
        if (game == null) {
            ScoreCard scoreCard = new ScoreCard();
            List<Player> players = new ArrayList<>();
            players.add(new Human());
            players.add(new Computer());
            game = new Game(scoreCard, 1, players);
        }
        return game;
    }

    /**
     * *********************************************************************
     * Function Name: setGame
     * Purpose: To set the single instance of the Game class to a provided Game object.
     * Parameters: 
     * - game: The Game object to set as the single instance (passed by reference).
     * Return Value: None.
     * Reference: None.
     * *********************************************************************
     */
    public static void setGame(Game game) {
        SingletonGame.game = game;
    }

    /**
     * *********************************************************************
     * Function Name: getCurrentRound
     * Purpose: To get the current round number from the single instance of the Game class.
     * Parameters: None.
     * Return Value: The current round number as an integer.
     * Reference: None.
     * *********************************************************************
     */
    public static int getCurrentRound() {
        return game.getCurrentRound();
    }

    /**
     * *********************************************************************
     * Function Name: setScoreCard
     * Purpose: To set the ScoreCard of the single instance of the Game class.
     * Parameters: 
     * - scoreCard: The ScoreCard object to set in the Game instance (passed by reference).
     * Return Value: None.
     * Reference: None.
     * *********************************************************************
     */
    public static void setScoreCard(ScoreCard scoreCard) {
        game.setScoreCard(scoreCard);
    }
}
