package com.example.yahtzeegame.model;

import java.util.List;

public class Reason {
    // The current dice values the player has at the moment
    private final List<Integer> currentDice;

    // The category that the player is currently pursuing based on their dice
    private final Category pursuedCategory;

    // The maximum possible score that can be achieved by pursuing this category
    private final int maxScore;

    // The dice values the player would need to roll in order to achieve the maximum score in this category
    private final List<Integer> rollToGetMax;

    // The minimum possible score that can be achieved by pursuing this category
    private final int minScore;

    // The dice values the player would need to roll in order to achieve the minimum score in this category
    private final List<Integer> rollToGetMin;

    // Constructor
    public Reason(List<Integer> currentDice, Category pursuedCategory, int maxScore, List<Integer> rollToGetMax, int minScore, List<Integer> rollToGetMin) {
        this.currentDice = currentDice;
        this.pursuedCategory = pursuedCategory;
        this.maxScore = maxScore;
        this.rollToGetMax = rollToGetMax;
        this.minScore = minScore;
        this.rollToGetMin = rollToGetMin;
    }

   /**
 * *********************************************************************
 * Function Name: getCurrentDice
 * Purpose: Retrieves the current list of dice values.
 * Parameters: None.
 * Return Value: A List of integers representing the current dice values.
 * Algorithm:
 * 1. Return the currentDice list, which holds the dice values.
 * Reference: None.
 *********************************************************************
 */
public List<Integer> getCurrentDice() {
    return currentDice;
}

/**
 * *********************************************************************
 * Function Name: getPursuedCategory
 * Purpose: Retrieves the category currently being pursued.
 * Parameters: None.
 * Return Value: The Category being pursued.
 * Algorithm:
 * 1. Return the pursuedCategory object, representing the current category.
 * Reference: None.
 *********************************************************************
 */
public Category getPursuedCategory() {
    return pursuedCategory;
}

/**
 * *********************************************************************
 * Function Name: getMaxScore
 * Purpose: Retrieves the maximum possible score for the current game state.
 * Parameters: None.
 * Return Value: The maximum score as an integer.
 * Algorithm:
 * 1. Return the maxScore value, representing the highest achievable score.
 * Reference: None.
 *********************************************************************
 */
public int getMaxScore() {
    return maxScore;
}

/**
 * *********************************************************************
 * Function Name: getRollToGetMax
 * Purpose: Retrieves the dice roll combination needed to achieve the maximum score.
 * Parameters: None.
 * Return Value: A List of integers representing the dice values needed for the max score.
 * Algorithm:
 * 1. Return the rollToGetMax list, which holds the required dice values.
 * Reference: None.
 *********************************************************************
 */
public List<Integer> getRollToGetMax() {
    return rollToGetMax;
}

/**
 * *********************************************************************
 * Function Name: getMinScore
 * Purpose: Retrieves the minimum possible score for the current game state.
 * Parameters: None.
 * Return Value: The minimum score as an integer.
 * Algorithm:
 * 1. Return the minScore value, representing the lowest achievable score.
 * Reference: None.
 *********************************************************************
 */
public int getMinScore() {
    return minScore;
}

/**
 * *********************************************************************
 * Function Name: getRollToGetMin
 * Purpose: Retrieves the dice roll combination needed to achieve the minimum score.
 * Parameters: None.
 * Return Value: A List of integers representing the dice values needed for the min score.
 * Algorithm:
 * 1. Return the rollToGetMin list, which holds the required dice values.
 * Reference: None.
 *********************************************************************
 */
public List<Integer> getRollToGetMin() {
    return rollToGetMin;
}
}