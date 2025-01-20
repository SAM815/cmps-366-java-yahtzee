package com.example.yahtzeegame.model;

import java.util.Objects;

public class ScoreCardEntry {
    private final int points;
    private final Player winner;
    private final int round;

    public ScoreCardEntry(int points, Player winner, int round) {
        this.points = points;
        this.winner = winner;
        this.round = round;
    }

    /**
     * *********************************************************************
     * Function Name: getPoints
     * Purpose: Retrieves the points scored in this round.
     * Parameters: None.
     * Return Value: The points scored in the round (int).
     * Algorithm:
     * 1. Return the points stored in the ScoreCardEntry object.
     * Reference: None.
     *********************************************************************
     */
    public int getPoints() {
        return points;
    }

    /**
     * *********************************************************************
     * Function Name: getWinner
     * Purpose: Retrieves the player who won the round.
     * Parameters: None.
     * Return Value: The winner of the round (Player).
     * Algorithm:
     * 1. Return the winner stored in the ScoreCardEntry object.
     * Reference: None.
     *********************************************************************
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * *********************************************************************
     * Function Name: getRound
     * Purpose: Retrieves the round number associated with the score.
     * Parameters: None.
     * Return Value: The round number (int).
     * Algorithm:
     * 1. Return the round number stored in the ScoreCardEntry object.
     * Reference: None.
     *********************************************************************
     */
    public int getRound() {
        return round;
    }

    /**
     * *********************************************************************
     * Function Name: equals
     * Purpose: Compares this ScoreCardEntry to another object for equality.
     * Parameters:
     * - obj (Object): The object to compare with this ScoreCardEntry.
     * Return Value: A boolean indicating whether the two objects are equal.
     * Algorithm:
     * 1. If the objects are the same reference, return true.
     * 2. If the object is null or of a different class, return false.
     * 3. Compare the points, winner, and round of both ScoreCardEntry objects for
     * equality.
     * Reference: None.
     *********************************************************************
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ScoreCardEntry that = (ScoreCardEntry) obj;
        return points == that.points && round == that.round && Objects.equals(winner, that.winner);
    }

    /**
     * *********************************************************************
     * Function Name: hashCode
     * Purpose: Generates a hash code for the ScoreCardEntry object based on its
     * attributes.
     * Parameters: None.
     * Return Value: An integer hash code based on the points, winner, and round.
     * Algorithm:
     * 1. Generate a hash code using the points, winner, and round values.
     * 2. Return the generated hash code.
     * Reference: None.
     *********************************************************************
     */
    @Override
    public int hashCode() {
        return Objects.hash(points, winner, round);
    }
}