package com.example.yahtzeegame.model;

public class Die {
    private final int value;
    private final boolean locked;

    private final boolean markedForLock;
    private final boolean markedForHelp;

    /**
     * *********************************************************************
     * Function Name: Die (Default Constructor)
     * Purpose: Initializes a default Die object with a value of 1, unlocked 
     *          status, and no helper flags set.
     * Parameters: None.
     * Return Value: A new Die object with default settings.
     * Algorithm:
     * 1. Call the parameterized constructor with default values (1, false, 
     *    false, false).
     * Reference: None.
     *********************************************************************
     */
    public Die() {
        this(1, false, false, false);
    }

     /**
     * *********************************************************************
     * Function Name: Die (Parameterized Constructor)
     * Purpose: Creates a Die object with specified attributes, ensuring 
     *          markedForLock is false if the die is already locked.
     * Parameters:
     * - value (int): The face value of the die (1-6). Passed by value.
     * - locked (boolean): Indicates if the die is locked. Passed by value.
     * - markedForLock (boolean): Indicates if the die is marked for locking. 
     *                            Passed by value.
     * - markedForHelp (boolean): Indicates if the die is marked for strategic 
     *                            help. Passed by value.
     * Return Value: A new Die object with the specified attributes.
     * Algorithm:
     * 1. Set the attributes directly, ensuring markedForLock is false if the 
     *    die is locked.
     * Reference: None.
     *********************************************************************
     */
    public Die(int value, boolean locked, boolean markedForLock, boolean markedForHelp) {
        this.value = value;
        this.locked = locked;
        this.markedForLock = !locked && markedForLock;
        this.markedForHelp = markedForHelp;
    }
/**
     * *********************************************************************
     * Function Name: getValue
     * Purpose: Retrieves the current face value of the die.
     * Parameters: None.
     * Return Value: The current face value of the die (int).
     * Algorithm:
     * 1. Return the value attribute.
     * Reference: None.
     *********************************************************************
     */
    public int getValue() {
        return value;
    }
/**
     * *********************************************************************
     * Function Name: roll
     * Purpose: Rolls the die, generating a random value between 1 and 6, 
     *          unless the die is locked.
     * Parameters: None.
     * Return Value: A new Die object with the rolled value if unlocked; 
     *               otherwise, the same Die object.
     * Algorithm:
     * 1. If the die is locked, return the current Die object.
     * 2. Generate a random value between 1 and 6.
     * 3. Return a new Die object with the generated value.
     * Reference: None.
     *********************************************************************
     */
    public Die roll() {
        if (locked) {
            return this;
        }

        int newValue = (int) (Math.random() * 6) + 1;
        return new Die(newValue, locked, false, false);

    }

 /**
     * *********************************************************************
     * Function Name: increment
     * Purpose: Increments the die's value by 1, wrapping around to 1 if it 
     *          exceeds 6.
     * Parameters: None.
     * Return Value: A new Die object with the incremented value.
     * Algorithm:
     * 1. Calculate the new value as value + 1, wrapping from 6 to 1.
     * 2. Return a new Die object with the calculated value.
     * Reference: None.
     *********************************************************************
     */
    public Die increment() {
        return newDieWithValue(value == 6 ? 1 : value + 1);

    }
/**
     * *********************************************************************
     * Function Name: decrement
     * Purpose: Decrements the die's value by 1, wrapping around to 6 if it 
     *          goes below 1.
     * Parameters: None.
     * Return Value: A new Die object with the decremented value.
     * Algorithm:
     * 1. Calculate the new value as value - 1, wrapping from 1 to 6.
     * 2. Return a new Die object with the calculated value.
     * Reference: None.
     *********************************************************************
     */
    public Die decrement() {
        return newDieWithValue(value == 1 ? 6 : value - 1);
    }

   /**
     * *********************************************************************
     * Function Name: setValue
     * Purpose: Sets a new value for the die, creating a new Die object with 
     *          the specified value.
     * Parameters:
     * - value (int): The new value for the die. Passed by value.
     * Return Value: A new Die object with the specified value.
     * Algorithm:
     * 1. Call the private method newDieWithValue with the given value.
     * Reference: None.
     *********************************************************************
     */
    public Die setValue(int value) {
        return newDieWithValue(value);
    }

    /**
     * *********************************************************************
     * Function Name: newDieWithValue
     * Purpose: Creates a new Die object with a specific value, ensuring the 
     *          locked state is maintained.
     * Parameters:
     * - value (int): The value for the new Die object. Passed by value.
     * Return Value: A new Die object with the specified value if unlocked; 
     *               otherwise, the same Die object.
     * Algorithm:
     * 1. If the die is locked, return the current Die object.
     * 2. Create and return a new Die object with the given value.
     * Reference: None.
     *********************************************************************
     */
    private Die newDieWithValue(int value) {
        if (locked) return this;
        return new Die(value, false, false, false);
    }


   /**
     * *********************************************************************
     * Function Name: lock
     * Purpose: Locks the die, preventing it from being rolled in subsequent actions.
     * Parameters: None.
     * Return Value: A new Die object with the same value but with the locked 
     *               attribute set to true.
     * Algorithm:
     * 1. Create and return a new Die object with the locked attribute set to true.
     * Reference: None.
     *********************************************************************
     */
    public Die lock() {
        return new Die(value, true, false, false);
    }

    /**
     * *********************************************************************
     * Function Name: isLocked
     * Purpose: Checks whether the die is currently locked.
     * Parameters: None.
     * Return Value: A boolean indicating whether the die is locked (true) or not (false).
     * Algorithm:
     * 1. Return the locked attribute of the Die object.
     * Reference: None.
     *********************************************************************
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * *********************************************************************
     * Function Name: markForLock
     * Purpose: Marks the die as a candidate for locking without actually locking it.
     * Parameters: None.
     * Return Value: A new Die object with the markedForLock attribute set to true.
     * Algorithm:
     * 1. Create and return a new Die object with the markedForLock attribute set to true.
     * Reference: None.
     *********************************************************************
     */
    public Die markForLock() {
        return new Die(value, locked, true, markedForHelp);
    }

    /**
     * *********************************************************************
     * Function Name: unmarkForLock
     * Purpose: Removes the mark indicating that the die should be locked.
     * Parameters: None.
     * Return Value: A new Die object with the markedForLock attribute set to false.
     * Algorithm:
     * 1. Create and return a new Die object with the markedForLock attribute set to false.
     * Reference: None.
     *********************************************************************
     */
    public Die unmarkForLock() {
        return new Die(value, locked, false, markedForHelp);
    }

    /**
     * *********************************************************************
     * Function Name: toggleMarkForLock
     * Purpose: Toggles the markedForLock status of the die, switching it 
     *          between true and false.
     * Parameters: None.
     * Return Value: A new Die object with the markedForLock attribute toggled.
     * Algorithm:
     * 1. Create and return a new Die object with the markedForLock attribute negated.
     * Reference: None.
     *********************************************************************
     */
    public Die toggleMarkForLock() {
        return new Die(value, locked, !markedForLock, markedForHelp);
    }

    /**
     * *********************************************************************
     * Function Name: setMarkedForLock
     * Purpose: Explicitly sets the markedForLock status of the die to a 
     *          specified value.
     * Parameters:
     * - markedForLock (boolean): The desired markedForLock status. Passed by value.
     * Return Value: A new Die object with the specified markedForLock status.
     * Algorithm:
     * 1. Create and return a new Die object with the markedForLock attribute set 
     *    to the specified value.
     * Reference: None.
     *********************************************************************
     */
    public Die setMarkedForLock(boolean markedForLock) {
        return new Die(value, locked, markedForLock, markedForHelp);
    }

    /**
     * *********************************************************************
     * Function Name: isMarkedForLock
     * Purpose: Checks whether the die is currently marked for locking.
     * Parameters: None.
     * Return Value: A boolean indicating the current markedForLock status of the die.
     * Algorithm:
     * 1. Return the markedForLock attribute of the Die object.
     * Reference: None.
     *********************************************************************
     */
    public boolean isMarkedForLock() {
        return markedForLock;
    }

    /**
     * *********************************************************************
     * Function Name: markForHelp
     * Purpose: Marks the die for strategic consideration during game decisions.
     * Parameters: None.
     * Return Value: A new Die object with the markedForHelp attribute set to true.
     * Algorithm:
     * 1. Create and return a new Die object with the markedForHelp attribute set to true.
     * Reference: None.
     *********************************************************************
     */
    public Die markForHelp() {
        return new Die(value, locked, markedForLock, true);
    }

    /**
     * *********************************************************************
     * Function Name: isMarkedForHelp
     * Purpose: Checks whether the die is currently marked for strategic help.
     * Parameters: None.
     * Return Value: A boolean indicating the current markedForHelp status of the die.
     * Algorithm:
     * 1. Return the markedForHelp attribute of the Die object.
     * Reference: None.
     *********************************************************************
     */
    public boolean isMarkedForHelp() {
        return markedForHelp;
    }

    /**
     * *********************************************************************
     * Function Name: toString
     * Purpose: Provides a string representation of the Die object, 
     *          including its value and locked status.
     * Parameters: None.
     * Return Value: A string describing the Die object.
     * Algorithm:
     * 1. Construct and return a string in the format "Die: <value> Locked: <locked>".
     * Reference: None.
     *********************************************************************
     */
    public String toString() {
        return "Die: " + value + " Locked: " + locked;
    }
}
