package com.example.yahtzeegame.model;

import static com.example.yahtzeegame.model.Category.CATEGORY_NAMES;

import java.util.List;

public class Help {
    private final List<Integer> diceToKeep;
    private final Category category;
    private final boolean stand;


   /**
     * *********************************************************************
     * Function Name: Help
     * Purpose: Constructor for the Help class, which provides suggestions 
     *          for dice to keep, the category to target, and whether to stand 
     *          or continue rolling.
     * Parameters:
     * - diceToKeep (List<Integer>): The dice that should be kept for the next move. 
     *   Passed by reference. This list is not modified by this function.
     * - category (Category): The category to target for scoring. Passed by reference.
     *   This category is not modified by this function.
     * - stand (boolean): A flag indicating whether the player should stand or 
     *   continue rolling. Passed by value.
     * Return Value: None. This constructor initializes the state of the Help object.
     * Algorithm:
     * 1. Initialize the diceToKeep, category, and stand attributes with the provided values.
     * Reference: None.
     *********************************************************************
     */
    public Help(List<Integer> diceToKeep, Category category, boolean stand) {
        this.diceToKeep = diceToKeep;
        this.category = category;
        this.stand = stand;
    }

    /**
     * *********************************************************************
     * Function Name: getDiceToKeep
     * Purpose: Retrieves the list of dice that should be kept for the next roll or move.
     * Parameters: None.
     * Return Value: The list of dice to keep, as a List<Integer>.
     * Algorithm:
     * 1. Return the diceToKeep attribute.
     * Reference: None.
     *********************************************************************
     */
    public List<Integer> getDiceToKeep() {
        return diceToKeep;
    }

    /**
     * *********************************************************************
     * Function Name: getCategory
     * Purpose: Retrieves the target category for the current move or roll.
     * Parameters: None.
     * Return Value: The category to target, as a Category object.
     * Algorithm:
     * 1. Return the category attribute.
     * Reference: None.
     *********************************************************************
     */
    public Category getCategory() {
        return category;
    }

    /**
     * *********************************************************************
     * Function Name: getStand
     * Purpose: Retrieves the stand flag that indicates whether to stand or continue rolling.
     * Parameters: None.
     * Return Value: The stand flag as a boolean value.
     * Algorithm:
     * 1. Return the stand attribute.
     * Reference: None.
     *********************************************************************
     */
    public boolean getStand() {
        return stand;
    }

    /**
     * *********************************************************************
     * Function Name: getMessage
     * Purpose: Generates a message with advice on what to do based on the current state.
     *          The message will either suggest which dice to keep or whether to roll all dice.
     * Parameters: None.
     * Return Value: A message string with advice for the player.
     * Algorithm:
     * 1. Retrieve the category and determine its string representation.
     * 2. Check if the player should stand or not.
     * 3. If standing, return a message suggesting the chosen category.
     * 4. If no dice are to be kept, suggest rolling all dice for the category.
     * 5. If some dice are to be kept, return a message suggesting which dice to keep.
     * Reference: None.
     *********************************************************************
     */
    public String getMessage() {
        Category category = getCategory();
        String categoryString = category != null ? CATEGORY_NAMES.get(category) : "none";
        if (stand) {
            return "Choose " + categoryString;
        }

        if (diceToKeep.isEmpty()) {
            return "Roll all dice for " + categoryString;
        }

        return "Keep " + diceToKeep + " for " + categoryString;
    }
}
