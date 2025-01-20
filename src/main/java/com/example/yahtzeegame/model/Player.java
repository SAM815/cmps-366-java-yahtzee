package com.example.yahtzeegame.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Player implements Serializable {
    private final String name;

    /**
     * *********************************************************************
     * Function Name: Player
     * Purpose: Default constructor for Player class, initializes an empty name.
     * Parameters: None.
     * Return Value: None.
     * Algorithm:
     * 1. Set the name to an empty string.
     * Reference: None.
     *********************************************************************
     */
    public Player() {
        this.name = "";
    }

    /**
     * *********************************************************************
     * Function Name: Player
     * Purpose: Constructor for Player class, initializes player with a given name.
     * Parameters: String name - the name of the player.
     * Return Value: None.
     * Algorithm:
     * 1. Assign the given name to the player's name.
     * Reference: None.
     *********************************************************************
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * *********************************************************************
     * Function Name: Player
     * Purpose: Copy constructor for Player class, initializes a new player as a
     * copy of another player.
     * Parameters: Player other - the player to copy.
     * Return Value: None.
     * Algorithm:
     * 1. Assign the name of the other player to this player's name.
     * Reference: None.
     *********************************************************************
     */
    public Player(Player other) {
        this.name = other.name;
    }

    /**
     * *********************************************************************
     * Function Name: tossDie
     * Purpose: Prompts the user to roll a die and returns the rolled value.
     * Parameters: String prompt - message to display before rolling the die.
     * Return Value: int - the result of the die roll.
     * Algorithm:
     * 1. Display the provided prompt using the ioFunctions.showMessage method.
     * 2. Get the die roll from the ioFunctions.getDieRoll method.
     * 3. Return the rolled die value.
     * Reference: None.
     *********************************************************************
     */
    public static int tossDie(String prompt) {
        ioFunctions.showMessage(prompt);
        return ioFunctions.getDieRoll();
    }

    /**
     * *********************************************************************
     * Function Name: equals
     * Purpose: Checks if two players are equal based on their names.
     * Parameters: Player other - the player to compare against.
     * Return Value: boolean - true if players are equal, false otherwise.
     * Algorithm:
     * 1. Compare the name of this player with the name of the other player.
     * 2. Return true if they are equal, false otherwise.
     * Reference: None.
     *********************************************************************
     */
    public boolean equals(Player other) {
        return this.name.equals(other.name);
    }

    /**
     * *********************************************************************
     * Function Name: notEquals
     * Purpose: Checks if two players are not equal based on their names.
     * Parameters: Player other - the player to compare against.
     * Return Value: boolean - true if players are not equal, false otherwise.
     * Algorithm:
     * 1. Return the negation of the result from the equals method.
     * Reference: None.
     *********************************************************************
     */
    public boolean notEquals(Player other) {
        return !this.equals(other);
    }

    /**
     * *********************************************************************
     * Function Name: lessThan
     * Purpose: Compares two players lexicographically based on their names.
     * Parameters: Player other - the player to compare against.
     * Return Value: boolean - true if this player's name is lexicographically less
     * than the other player's name.
     * Algorithm:
     * 1. Compare this player's name with the other player's name using String's
     * compareTo method.
     * 2. Return true if this player's name comes before the other player's name,
     * false otherwise.
     * Reference: None.
     *********************************************************************
     */
    public boolean lessThan(Player other) {
        return this.name.compareTo(other.name) < 0;
    }

    /**
     * *********************************************************************
     * Function Name: getName
     * Purpose: Retrieves the name of the player.
     * Parameters: None.
     * Return Value: String - the name of the player.
     * Algorithm:
     * 1. Return the name of this player.
     * Reference: None.
     *********************************************************************
     */
    public String getName() {
        return name;
    }

    /**
     * *********************************************************************
     * Function Name: getDiceRoll
     * Purpose: Gets the dice roll for the player.
     * Parameters: int numDice - the number of dice to roll.
     * Return Value: List<Integer> - a list of rolled dice values.
     * Algorithm:
     * 1. Call ioFunctions.getDiceRoll to roll the dice and return the result.
     * Reference: None.
     *********************************************************************
     */
    public List<Integer> getDiceRoll(int numDice) {
        return ioFunctions.getDiceRoll(numDice);
    }

    /**
     * *********************************************************************
     * Function Name: getDiceToKeep
     * Purpose: Prompts the player to select dice to keep after a roll.
     * Parameters: ScoreCard scoreCard - the current score card.
     * List<Integer> diceRolls - the current dice roll values.
     * List<Integer> keptDice - the dice already kept.
     * Return Value: List<Integer> - a list of dice to keep.
     * Algorithm:
     * 1. Display the kept dice and current dice rolls using
     * ioFunctions.showMessage.
     * 2. Prompt the player to choose which dice to keep using
     * ioFunctions.getDiceToKeep.
     * 3. Return the selected dice to keep.
     * Reference: None.
     *********************************************************************
     */
    public List<Integer> getDiceToKeep(ScoreCard scoreCard, List<Integer> diceRolls, List<Integer> keptDice) {
        ioFunctions.showMessage("Kept dice: " + ioFunctions.toStringVector(keptDice));
        ioFunctions.showMessage("Current dice rolls: " + ioFunctions.toStringVector(diceRolls));
        return ioFunctions.getDiceToKeep(diceRolls);
    }

    /**
     * *********************************************************************
     * Function Name: getCategoryPursuits
     * Purpose: Retrieves the category pursuits for the player based on the score
     * card and kept dice.
     * Parameters: ScoreCard scoreCard - the current score card.
     * List<Integer> keptDice - the dice the player is keeping.
     * Return Value: Optional<Map<Category, Reason>> - an optional map of categories
     * the player is pursuing.
     * Algorithm:
     * 1. This method currently returns an empty optional, to be extended as needed.
     * Reference: None.
     *********************************************************************
     */
    public Optional<Map<Category, Reason>> getCategoryPursuits(ScoreCard scoreCard, List<Integer> keptDice) {
        return Optional.empty();
    }

    /**
     * *********************************************************************
     * Function Name: getTarget
     * Purpose: Retrieves the target category and dice to pursue based on the score
     * card and kept dice.
     * Parameters: ScoreCard scoreCard - the current score card.
     * List<Integer> keptDice - the dice the player is keeping.
     * Return Value: Optional<Map.Entry<Category, List<Integer>>> - an optional
     * entry with target category and dice.
     * Algorithm:
     * 1. This method currently returns an empty optional, to be extended as needed.
     * Reference: None.
     *********************************************************************
     */
    public Optional<Map.Entry<Category, List<Integer>>> getTarget(ScoreCard scoreCard, List<Integer> keptDice) {
        return Optional.empty();
    }

    /**
     * *********************************************************************
     * Function Name: wantsToStand
     * Purpose: Prompts the player to decide if they want to stand or keep rolling.
     * Parameters: ScoreCard scoreCard - the current score card.
     * List<Integer> keptDice - the dice the player is keeping.
     * List<Integer> diceRolls - the current dice roll values.
     * Return Value: boolean - true if the player wants to stand, false otherwise.
     * Algorithm:
     * 1. Display the kept dice and current dice rolls using
     * ioFunctions.showMessage.
     * 2. Prompt the player to decide if they want to stand using
     * ioFunctions.wantsToStand.
     * 3. Return the player's decision.
     * Reference: None.
     *********************************************************************
     */
    public boolean wantsToStand(ScoreCard scoreCard, List<Integer> keptDice, List<Integer> diceRolls) {
        ioFunctions.showMessage("Kept dice: " + ioFunctions.toStringVector(keptDice));
        ioFunctions.showMessage("Current dice rolls: " + ioFunctions.toStringVector(diceRolls));
        return ioFunctions.wantsToStand();
    }

    /**
     * *********************************************************************
     * Function Name: wantsHelp
     * Purpose: Checks if the player wants help.
     * Parameters: None.
     * Return Value: boolean - true if the player wants help, false otherwise.
     * Algorithm:
     * 1. Call ioFunctions.wantsHelp to check if the player wants help.
     * 2. Return the result of the help request.
     * Reference: None.
     *********************************************************************
     */
    public boolean wantsHelp() {
        return ioFunctions.wantsHelp();
    }

    /**
     * *********************************************************************
     * Function Name: inform
     * Purpose: Informs the player with a given message.
     * Parameters: String message - the message to inform the player.
     * Return Value: None.
     * Algorithm:
     * 1. Print the player's name followed by the message to the console.
     * Reference: None.
     *********************************************************************
     */
    public void inform(String message) {
        System.out.println(name + ": " + message);
    }

    /**
     * *********************************************************************
     * Function Name: equals
     * Purpose: Checks if two players are equal based on their names.
     * Parameters: Object obj - the object to compare with.
     * Return Value: boolean - true if players are equal, false otherwise.
     * Algorithm:
     * 1. If the object is this instance, return true.
     * 2. If the object is null or not an instance of Player, return false.
     * 3. Compare the names of this player and the other player.
     * 4. Return true if they are equal, false otherwise.
     * Reference: None.
     *********************************************************************
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Player player = (Player) obj;
        return Objects.equals(name, player.name);
    }

    /**
     * *********************************************************************
     * Function Name: hashCode
     * Purpose: Generates a hash code for the Player object based on the player's
     * name.
     * Parameters: None.
     * Return Value: int - the hash code generated from the player's name.
     * Algorithm:
     * 1. Use the name of the player to generate a hash code with Objects.hash.
     * 2. Return the generated hash code.
     * Reference: None.
     *********************************************************************
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}