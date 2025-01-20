package com.example.yahtzeegame.model;

import androidx.annotation.NonNull;

public class Log {
    private static final Log INSTANCE = new Log();
    private static String log = "";

    private Log() {
    }

   /**
     * *********************************************************************
     * Function Name: getInstance
     * Purpose: Provides access to the singleton instance of the Log class.
     * Parameters: None.
     * Return Value: The singleton instance of the Log class.
     * Algorithm:
     * 1. Return the static INSTANCE of the Log class.
     * Reference: None.
     *********************************************************************
     */
    public static Log getInstance() {
        return INSTANCE;
    }

    /**
     * *********************************************************************
     * Function Name: log
     * Purpose: Adds a message to the log.
     * Parameters:
     * - message (String): The message to be logged. Passed by value.
     * Return Value: None. This method appends the message to the log.
     * Algorithm:
     * 1. Append the provided message followed by a newline to the log string.
     * Reference: None.
     *********************************************************************
     */
    public static void log(String message) {
        log += message + "\n";
    }

    /**
     * *********************************************************************
     * Function Name: toString
     * Purpose: Returns the current log as a string.
     * Parameters: None.
     * Return Value: The current log as a string.
     * Algorithm:
     * 1. Return the current log string.
     * Reference: None.
     *********************************************************************
     */
    @NonNull
    @Override
    public String toString() {
        return log;
    }

    /**
     * *********************************************************************
     * Function Name: clear
     * Purpose: Clears the current log, resetting it to an empty string.
     * Parameters: None.
     * Return Value: None. This method resets the log string.
     * Algorithm:
     * 1. Set the log string to an empty string, effectively clearing the log.
     * Reference: None.
     *********************************************************************
     */
    public void clear() {
        log = "";
    }
}
