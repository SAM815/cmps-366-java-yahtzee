package com.example.yahtzeegame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dice {

    /**
     * *********************************************************************
     * Function Name: rollDie
     * Purpose: Generates a random number between 1 and 6 to simulate rolling a die.
     * Parameters: None
     * Return Value: An integer representing the value of the rolled die (1 to 6).
     * Algorithm:
     * 1. Instantiate a Random object.
     * 2. Use the Random object to generate a number between 1 and 6.
     * 3. Return the generated number.
     * Reference: None
     *********************************************************************
     */
    public static int rollDie() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    /**
     * *********************************************************************
     * Function Name: rollDice
     * Purpose: Rolls a specified number of dice and returns their values in a list.
     * Parameters:
     * - numDice (int): The number of dice to roll.
     * Return Value: A list of integers representing the values of the rolled dice.
     * Algorithm:
     * 1. Create an empty list to store the results of the dice rolls.
     * 2. Loop for the number of dice specified by numDice.
     * 3. For each iteration, call rollDie to generate a random die value.
     * 4. Add the generated value to the list.
     * 5. Return the list containing all rolled dice values.
     * Reference: None
     *********************************************************************
     */
    public static List<Integer> rollDice(int numDice) {
        List<Integer> diceRolls = new ArrayList<>(numDice);

        for (int i = 0; i < numDice; i++) {
            diceRolls.add(rollDie());
        }

        return diceRolls;
    }
}