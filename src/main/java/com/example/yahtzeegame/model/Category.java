package com.example.yahtzeegame.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Category {
    ONES,
    TWOS,
    THREES,
    FOURS,
    FIVES,
    SIXES,
    THREE_OF_A_KIND,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    FOUR_STRAIGHT,
    FIVE_STRAIGHT,
    YAHTZEE;

    public static final Map<Category, String> CATEGORY_NAMES = Map.ofEntries(
            Map.entry(ONES, "Ones"),
            Map.entry(TWOS, "Twos"),
            Map.entry(THREES, "Threes"),
            Map.entry(FOURS, "Fours"),
            Map.entry(FIVES, "Fives"),
            Map.entry(SIXES, "Sixes"),
            Map.entry(THREE_OF_A_KIND, "Three of a Kind"),
            Map.entry(FOUR_OF_A_KIND, "Four of a Kind"),
            Map.entry(FULL_HOUSE, "Full House"),
            Map.entry(FOUR_STRAIGHT, "Four Straight"),
            Map.entry(FIVE_STRAIGHT, "Five Straight"),
            Map.entry(YAHTZEE, "Yahtzee"));

    public static final Map<String, Category> DISPLAY_NAME_TO_ENUM = new HashMap<>();
    // List of all categories for easy iteration
    public static final List<Category> CATEGORIES = List.of(
            ONES,
            TWOS,
            THREES,
            FOURS,
            FIVES,
            SIXES,
            THREE_OF_A_KIND,
            FOUR_OF_A_KIND,
            FULL_HOUSE,
            FOUR_STRAIGHT,
            FIVE_STRAIGHT,
            YAHTZEE);

    static {
        for (Map.Entry<Category, String> entry : CATEGORY_NAMES.entrySet()) {
            DISPLAY_NAME_TO_ENUM.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * *********************************************************************
     * Function Name: getApplicableCategories
     * Purpose: To get a list of categories that are applicable based on the dice
     * rolled.
     * Parameters:
     * - dice: A list of integers representing the dice rolled (passed by reference,
     * not modified).
     * Return Value: A list of Category objects that are applicable based on the
     * dice rolled.
     * Algorithm:
     * 1. Stream through the list of all categories.
     * 2. Filter the categories to include only those that are applicable based on
     * the dice rolled.
     * 3. Collect the filtered categories into a list and return it.
     * Reference: None.
     * *********************************************************************
     */
    public static List<Category> getApplicableCategories(List<Integer> dice) {
        return CATEGORIES.stream()
                .filter(category -> isApplicableCategory(dice, category))
                .collect(Collectors.toList());
    }

    /**
     * *********************************************************************
     * Function Name: getScore
     * Purpose: To calculate the score based on the rolled dice and the selected
     * category.
     * Parameters:
     * - dice: A list of integers representing the dice rolled (passed by reference,
     * not modified).
     * - category: The Category object for which the score needs to be calculated
     * (passed by reference, not modified).
     * Return Value: An integer representing the score for the given dice and
     * category.
     * Algorithm:
     * 1. Check if the category is applicable based on the dice rolled.
     * 2. If not applicable, return a score of 0.
     * 3. If applicable, calculate the score based on the category using helper
     * functions.
     * 4. Return the calculated score.
     * Reference: None.
     * *********************************************************************
     */
    public static int getScore(List<Integer> dice, Category category) {
        if (!isApplicableCategory(dice, category)) {
            return 0;
        }

        switch (category) {
            case ONES:
                return helperFunctions.countN(dice, 1);
            case TWOS:
                return helperFunctions.countN(dice, 2) * 2;
            case THREES:
                return helperFunctions.countN(dice, 3) * 3;
            case FOURS:
                return helperFunctions.countN(dice, 4) * 4;
            case FIVES:
                return helperFunctions.countN(dice, 5) * 5;
            case SIXES:
                return helperFunctions.countN(dice, 6) * 6;
            case THREE_OF_A_KIND:
                return isApplicableCategory(dice, Category.THREE_OF_A_KIND) ? helperFunctions.sum(dice) : 0;
            case FOUR_OF_A_KIND:
                return isApplicableCategory(dice, Category.FOUR_OF_A_KIND) ? helperFunctions.sum(dice) : 0;
            case FULL_HOUSE:
                return isApplicableCategory(dice, Category.FULL_HOUSE) ? 25 : 0;
            case FOUR_STRAIGHT:
                return isApplicableCategory(dice, Category.FOUR_STRAIGHT) ? 30 : 0;
            case FIVE_STRAIGHT:
                return isApplicableCategory(dice, Category.FIVE_STRAIGHT) ? 40 : 0;
            case YAHTZEE:
                return isApplicableCategory(dice, Category.YAHTZEE) ? 50 : 0;
            default:
                return 0;
        }
    }

    /**
     * *********************************************************************
     * Function Name: isApplicableCategory
     * Purpose: To check if a given category is applicable based on the rolled dice.
     * Parameters:
     * - dice: A list of integers representing the dice rolled (passed by reference,
     * not modified).
     * - category: The Category object to check for applicability (passed by
     * reference, not modified).
     * Return Value: A boolean indicating whether the category is applicable based
     * on the dice rolled.
     * Algorithm:
     * 1. Use a switch statement to check the category.
     * 2. For each category, use helper functions to determine if the category is
     * applicable based on the dice rolled.
     * 3. Return the result of the helper function checks.
     * Reference: None.
     * *********************************************************************
     */
    public static boolean isApplicableCategory(List<Integer> dice, Category category) {
        switch (category) {
            case YAHTZEE:
                return helperFunctions.atleastNSame(dice, 5);
            case FIVE_STRAIGHT:
                return helperFunctions.fiveSequence(dice);
            case FOUR_STRAIGHT:
                return helperFunctions.fourSequence(dice);
            case FULL_HOUSE:
                return helperFunctions.fullHouse(dice);
            case FOUR_OF_A_KIND:
                return helperFunctions.atleastFourSame(dice);
            case THREE_OF_A_KIND:
                return helperFunctions.atleastThreeSame(dice);
            case SIXES:
                return helperFunctions.containsN(dice, 6);
            case FIVES:
                return helperFunctions.containsN(dice, 5);
            case FOURS:
                return helperFunctions.containsN(dice, 4);
            case THREES:
                return helperFunctions.containsN(dice, 3);
            case TWOS:
                return helperFunctions.containsN(dice, 2);
            case ONES:
                return helperFunctions.containsN(dice, 1);
            default:
                return false;
        }
    }

    /**
     * *********************************************************************
     * Function Name: isPossibleCategory
     * Purpose: To determine if a category is possible based on the rolled dice.
     * Parameters:
     * - dice: A list of integers representing the dice rolled (passed by reference,
     * not modified).
     * - category: The Category object to check for possibility (passed by
     * reference, not modified).
     * Return Value: A boolean indicating whether the category is possible based on
     * the dice rolled.
     * Algorithm:
     * 1. Check if the dice list is empty; if so, return true.
     * 2. Calculate the number of slots left to roll.
     * 3. Use a switch statement to check the category.
     * 4. For each category, use helper functions to determine if the category is
     * possible based on the dice rolled and slots left.
     * 5. Return the result of the helper function checks.
     * Reference: None.
     * *********************************************************************
     */
    public static boolean isPossibleCategory(List<Integer> dice, Category category) {
        if (dice.isEmpty())
            return true;
        int slotsLeft = 5 - dice.size();
        switch (category) {
            case YAHTZEE:
                return helperFunctions.allSame(dice);
            case FIVE_STRAIGHT:
                return helperFunctions.numRepeats(dice) < 1 &&
                        !(helperFunctions.containsN(dice, 1) && helperFunctions.containsN(dice, 6));
            case FOUR_STRAIGHT:
                return helperFunctions.numRepeats(dice) < 2;
            case FULL_HOUSE:
                return helperFunctions.countUnique(dice) <= 2 && helperFunctions.maxCount(dice) <= 3;
            case FOUR_OF_A_KIND:
                return slotsLeft + helperFunctions.maxCount(dice) >= 4;
            case THREE_OF_A_KIND:
                return slotsLeft + helperFunctions.maxCount(dice) >= 3;
            case SIXES:
                return helperFunctions.containsN(dice, 6) || dice.size() < 5;
            case FIVES:
                return helperFunctions.containsN(dice, 5) || dice.size() < 5;
            case FOURS:
                return helperFunctions.containsN(dice, 4) || dice.size() < 5;
            case THREES:
                return helperFunctions.containsN(dice, 3) || dice.size() < 5;
            case TWOS:
                return helperFunctions.containsN(dice, 2) || dice.size() < 5;
            case ONES:
                return helperFunctions.containsN(dice, 1) || dice.size() < 5;
            default:
                return true;
        }
    }

}