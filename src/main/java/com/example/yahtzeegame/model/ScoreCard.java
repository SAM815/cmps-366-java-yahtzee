package com.example.yahtzeegame.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ScoreCard {
    private final Map<Category, Optional<ScoreCardEntry>> scoreCard;

    /**
     * *********************************************************************
     * Function Name: ScoreCard
     * Purpose: Initializes a new ScoreCard object with an empty scorecard.
     * Parameters: None.
     * Return Value: None. The constructor initializes the scoreCard attribute.
     * Algorithm:
     * 1. Initialize the scoreCard attribute by calling the initializeScoreCard
     * method.
     * Reference: None.
     *********************************************************************
     */
    public ScoreCard() {
        this.scoreCard = initializeScoreCard();
    }

    /**
     * *********************************************************************
     * Function Name: ScoreCard
     * Purpose: Initializes a new ScoreCard object with a provided scorecard map.
     * Parameters:
     * - scoreCard (Map<Category, Optional<ScoreCardEntry>>): A map representing the
     * scorecard entries.
     * Return Value: None. The constructor initializes the scoreCard attribute with
     * the provided map.
     * Algorithm:
     * 1. Initialize the scoreCard attribute with the provided map.
     * Reference: None.
     *********************************************************************
     */
    public ScoreCard(Map<Category, Optional<ScoreCardEntry>> scoreCard) {
        this.scoreCard = scoreCard;
    }

    /**
     * *********************************************************************
     * Function Name: ScoreCard
     * Purpose: Creates a new ScoreCard object as a copy of an existing one.
     * Parameters:
     * - other (ScoreCard): Another ScoreCard object to copy from.
     * Return Value: None. The constructor initializes the scoreCard attribute by
     * copying from another ScoreCard.
     * Algorithm:
     * 1. Assign the scoreCard attribute by copying the provided ScoreCard object.
     * Reference: None.
     *********************************************************************
     */
    public ScoreCard(ScoreCard other) {
        this.scoreCard = other.scoreCard;
    }

    /**
     * *********************************************************************
     * Function Name: deserialize
     * Purpose: Converts a serialized string representation of a scorecard into a
     * ScoreCard object.
     * Parameters:
     * - serial (String): A serialized string representation of the scorecard.
     * - human (Player): The human player.
     * - computer (Player): The computer player.
     * Return Value: A new ScoreCard object created from the serialized data.
     * Algorithm:
     * 1. Split the serialized string into lines.
     * 2. Iterate through each line, parsing the data to extract category, points,
     * winner, and round.
     * 3. Populate a map with the deserialized entries and return a new ScoreCard
     * object.
     * Reference: None.
     *********************************************************************
     */
    public static ScoreCard deserialize(String serial, Player human, Player computer) {
        String[] lines = serial.split("\n");

        int categoryIndex = 0;
        Map<Category, Optional<ScoreCardEntry>> scoreCard = new LinkedHashMap<>();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.equals("0")) {
                scoreCard.put(Category.values()[categoryIndex], Optional.empty());
                categoryIndex++;
                continue;
            }

            List<String> parts = Arrays.asList(line.split(" "));

            if (parts.size() != 3) {
                continue;
            }

            Category category = Category.values()[categoryIndex];

            int points = Integer.parseInt(parts.get(0));
            Player winner = parts.get(1).equals("Human") ? human : computer;
            int round = Integer.parseInt(parts.get(2));

            scoreCard.put(category, Optional.of(new ScoreCardEntry(points, winner, round)));
            categoryIndex++;
        }

        return new ScoreCard(scoreCard);
    }

    /**
     * *********************************************************************
     * Function Name: initializeScoreCard
     * Purpose: Initializes an empty scorecard with all categories set to
     * Optional.empty().
     * Parameters: None.
     * Return Value: A map representing an empty scorecard with all categories
     * initialized to Optional.empty().
     * Algorithm:
     * 1. Create a new map of categories.
     * 2. Iterate over all Category values and initialize them with
     * Optional.empty().
     * 3. Return the initialized scoreCard map.
     * Reference: None.
     *********************************************************************
     */
    private static Map<Category, Optional<ScoreCardEntry>> initializeScoreCard() {
        Map<Category, Optional<ScoreCardEntry>> tempScoreCard = new LinkedHashMap<>();
        for (Category category : Category.values()) {
            tempScoreCard.put(category, Optional.empty());
        }
        return tempScoreCard;
    }

    /**
     * *********************************************************************
     * Function Name: getScore
     * Purpose: Retrieves the score for a given category based on a list of dice
     * values.
     * Parameters:
     * - dice (List<Integer>): The list of dice values.
     * - category (Category): The category for which the score needs to be
     * calculated.
     * Return Value: The score (int) for the specified category based on the dice
     * values.
     * Algorithm:
     * 1. Call the Category.getScore method with the given dice and category.
     * 2. Return the resulting score.
     * Reference: None.
     *********************************************************************
     */
    public static int getScore(List<Integer> dice, Category category) {
        return Category.getScore(dice, category);
    }

    /**
     * *********************************************************************
     * Function Name: isPossibleCategory
     * Purpose: Checks if a category is possible based on a list of dice values.
     * Parameters:
     * - dice (List<Integer>): The list of dice values.
     * - category (Category): The category to check for possibility.
     * Return Value: A boolean indicating whether the category is possible.
     * Algorithm:
     * 1. Call Category.isPossibleCategory with the dice and category.
     * 2. Return the result indicating if the category is possible.
     * Reference: None.
     *********************************************************************
     */
    public static boolean isPossibleCategory(List<Integer> dice, Category category) {
        return Category.isPossibleCategory(dice, category);
    }

    /**
     * *********************************************************************
     * Function Name: getEntry
     * Purpose: Retrieves the scorecard entry for a specific category.
     * Parameters:
     * - category (Category): The category whose entry needs to be retrieved.
     * Return Value: An Optional containing the ScoreCardEntry for the given
     * category, or Optional.empty() if no entry exists.
     * Algorithm:
     * 1. Look up the entry for the specified category in the scoreCard map.
     * 2. Return the entry if present, or Optional.empty() if not.
     * Reference: None.
     *********************************************************************
     */
    public Optional<ScoreCardEntry> getEntry(Category category) {
        return scoreCard.get(category);
    }

    /**
     * *********************************************************************
     * Function Name: serialize
     * Purpose: Converts the scorecard into a serialized string representation.
     * Parameters: None.
     * Return Value: A string representing the serialized scorecard.
     * Algorithm:
     * 1. Iterate through all categories in the scorecard map.
     * 2. For each entry, append the points, winner, and round information if
     * present, otherwise append "0".
     * 3. Return the concatenated string.
     * Reference: None.
     *********************************************************************
     */
    public String serialize() {
        StringBuilder serial = new StringBuilder();

        for (Map.Entry<Category, Optional<ScoreCardEntry>> entry : scoreCard.entrySet()) {
            if (entry.getValue().isPresent()) {
                ScoreCardEntry scoreCardEntry = entry.getValue().get();
                serial.append(scoreCardEntry.getPoints()).append(" ")
                        .append(scoreCardEntry.getWinner().getName()).append(" ")
                        .append(scoreCardEntry.getRound()).append("\n");
            } else {
                serial.append("0\n");
            }
        }

        return serial.toString();
    }

    /**
     * *********************************************************************
     * Function Name: assign
     * Purpose: Assigns a new scorecard if the current scorecard is different from
     * the provided one.
     * Parameters:
     * - other (ScoreCard): The other ScoreCard object to assign.
     * Return Value: A new ScoreCard object if the current one is different from the
     * provided, or the current ScoreCard if they are the same.
     * Algorithm:
     * 1. If the current object is not the same as the provided one, create a new
     * ScoreCard object based on the other.
     * 2. Return the current or new ScoreCard as appropriate.
     * Reference: None.
     *********************************************************************
     */
    public ScoreCard assign(ScoreCard other) {
        if (this != other) {
            return new ScoreCard(other);
        }
        return this;
    }

    /**
     * *********************************************************************
     * Function Name: equals
     * Purpose: Compares the current ScoreCard object to another for equality.
     * Parameters:
     * - obj (Object): The object to compare with the current ScoreCard.
     * Return Value: A boolean indicating whether the current ScoreCard and the
     * provided object are equal.
     * Algorithm:
     * 1. If the current object is the same as the provided one, return true.
     * 2. If the provided object is null or not of the same class, return false.
     * 3. Compare the scoreCard attribute for equality between the current object
     * and the provided one.
     * Reference: None.
     *********************************************************************
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ScoreCard scoreCard1 = (ScoreCard) obj;
        return Objects.equals(scoreCard, scoreCard1.scoreCard);
    }

    /**
     * *********************************************************************
     * Function Name: hashCode
     * Purpose: Generates a hash code for the ScoreCard object based on its
     * scorecard attribute.
     * Parameters: None.
     * Return Value: An integer hash code based on the scorecard.
     * Algorithm:
     * 1. Generate a hash code using the scorecard attribute.
     * 2. Return the generated hash code.
     * Reference: None.
     *********************************************************************
     */
    @Override
    public int hashCode() {
        return Objects.hash(scoreCard);
    }

    /**
     * *********************************************************************
     * Function Name: addEntry
     * Purpose: Adds a new entry to the scorecard for the specified category, round,
     * winner, and points.
     * Parameters:
     * - category: The category to which the entry is added.
     * - round: The round number for the entry.
     * - winner: The player who won the category.
     * - points: The score for the category.
     * Return Value: A new ScoreCard with the added entry.
     * Algorithm:
     * 1. Check if the category already has a scorecard entry. If it does, throw an
     * IllegalArgumentException.
     * 2. Create a new scorecard with the added entry.
     * 3. Return the new ScoreCard object with the added entry.
     * Reference: None.
     *********************************************************************
     */
    public ScoreCard addEntry(Category category, int round, Player winner, int points) {
        if (scoreCard.get(category).isPresent()) {
            throw new IllegalArgumentException("Category already has a score card entry");
        }

        Map<Category, Optional<ScoreCardEntry>> newScoreCard = new LinkedHashMap<>(scoreCard);
        newScoreCard.put(category, Optional.of(new ScoreCardEntry(points, winner, round)));
        return new ScoreCard(newScoreCard);
    }

    /**
     * *********************************************************************
     * Function Name: addEntry
     * Purpose: Adds an entry to the scorecard using dice values to calculate the
     * score.
     * Parameters:
     * - category: The category to which the entry is added.
     * - round: The round number for the entry.
     * - winner: The player who won the category.
     * - dice: A list of integers representing the dice values.
     * Return Value: A new ScoreCard with the added entry.
     * Algorithm:
     * 1. Check if the category is open.
     * 2. If open, calculate the score for the given category using the dice values
     * and add the entry.
     * 3. If the category is not open, return the current ScoreCard.
     * Reference: None.
     *********************************************************************
     */
    public ScoreCard addEntry(Category category, int round, Player winner, List<Integer> dice) {
        if (isCategoryOpen(category)) {
            return addEntry(category, round, winner, Category.getScore(dice, category));
        }
        return this;
    }

    /**
     * *********************************************************************
     * Function Name: addEntry
     * Purpose: Adds an entry to the scorecard based on the best possible category
     * from a dice roll.
     * Parameters:
     * - round: The round number for the entry.
     * - winner: The player who won the category.
     * - dice: A list of integers representing the dice values.
     * Return Value: A new ScoreCard with the best scoring category added.
     * Algorithm:
     * 1. Determine the category with the highest score based on the dice roll.
     * 2. If no scoring category is found, return the current ScoreCard.
     * 3. Add the best category to the scorecard.
     * Reference: None.
     *********************************************************************
     */
    public ScoreCard addEntry(int round, Player winner, List<Integer> dice) {
        Optional<Category> maxCategory = getMaxScoringCategory(dice);
        if (!maxCategory.isPresent()) {
            return this;
        }
        return addEntry(maxCategory.get(), round, winner, Category.getScore(dice, maxCategory.get()));
    }

    /**
     * *********************************************************************
     * Function Name: getMaxScoringCategory
     * Purpose: Determines the category with the highest score based on the given
     * dice values.
     * Parameters:
     * - dice: A list of integers representing the dice values.
     * Return Value: The category with the highest score, or empty if no applicable
     * category is found.
     * Algorithm:
     * 1. Get the list of open categories and applicable categories for the dice
     * roll.
     * 2. Find the intersection between open and applicable categories.
     * 3. For each assignable category, calculate the score.
     * 4. Select the category with the highest score. If multiple categories have
     * the same score, select the best one.
     * 5. Return the category with the highest score.
     * Reference: None.
     *********************************************************************
     */
    public Optional<Category> getMaxScoringCategory(List<Integer> dice) {
        List<Category> openCategories = getOpenCategories();
        List<Category> applicableCategories = Category.getApplicableCategories(dice);
        List<Category> assignableCategories = helperFunctions.intersection(openCategories, applicableCategories);

        if (assignableCategories.isEmpty()) {
            return Optional.empty();
        }

        Map<Category, Integer> categoryScores = new HashMap<>();
        for (Category category : assignableCategories) {
            int score = Category.getScore(dice, category);
            categoryScores.put(category, score);
        }

        List<Category> maxCategories = new ArrayList<>();
        int maxScore = 0;
        for (Map.Entry<Category, Integer> entry : categoryScores.entrySet()) {
            if (entry.getValue() == maxScore) {
                maxCategories.add(entry.getKey());
            } else if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                maxCategories.clear();
                maxCategories.add(entry.getKey());
            }
        }

        if (maxCategories.contains(Category.FIVE_STRAIGHT) && maxCategories.contains(Category.FOUR_STRAIGHT)) {
            return Optional.of(Category.FIVE_STRAIGHT);
        }

        Category maxCategory = maxCategories.get(0);
        return Optional.of(maxCategory);
    }

    /**
     * *********************************************************************
     * Function Name: isFull
     * Purpose: Checks if the scorecard is full, meaning all categories have a
     * score.
     * Parameters: None.
     * Return Value: True if the scorecard is full, false otherwise.
     * Algorithm:
     * 1. Iterate through all categories in the scorecard.
     * 2. Check if any category is missing a score. If so, return false.
     * 3. If all categories have scores, return true.
     * Reference: None.
     *********************************************************************
     */
    public boolean isFull() {
        for (Map.Entry<Category, Optional<ScoreCardEntry>> entry : scoreCard.entrySet()) {
            if (!entry.getValue().isPresent()) {
                return false;
            }
        }
        return true;
    }

    /**
     * *********************************************************************
     * Function Name: getOpenCategories
     * Purpose: Returns a list of categories that have not yet been filled in the
     * scorecard.
     * Parameters: None.
     * Return Value: A list of open categories that are not yet scored.
     * Algorithm:
     * 1. Iterate through the scorecard and collect categories with no entries.
     * 2. Reverse the list to prioritize categories from highest to lowest.
     * 3. Return the list of open categories.
     * Reference: None.
     *********************************************************************
     */
    public List<Category> getOpenCategories() {
        List<Category> openCategories = new ArrayList<>();
        for (Map.Entry<Category, Optional<ScoreCardEntry>> entry : scoreCard.entrySet()) {
            if (!entry.getValue().isPresent()) {
                openCategories.add(entry.getKey());
            }
        }

        return helperFunctions.reversed(openCategories);
    }

    /**
     * *********************************************************************
     * Function Name: isCategoryOpen
     * Purpose: Checks if a specific category is open (i.e., not yet scored).
     * Parameters:
     * - category: The category to check.
     * Return Value: True if the category is open, false otherwise.
     * Algorithm:
     * 1. Check if the category exists in the scorecard and if its value is empty
     * (not scored).
     * 2. Return the result of the check.
     * Reference: None.
     *********************************************************************
     */
    public boolean isCategoryOpen(Category category) {
        return scoreCard.containsKey(category) && !scoreCard.get(category).isPresent();
    }

    /**
     * *********************************************************************
     * Function Name: getPossibleCategories
     * Purpose: Returns a list of categories that are possible based on the dice
     * values.
     * Parameters:
     * - dice: A list of integers representing the dice values.
     * Return Value: A list of possible categories that can be scored based on the
     * dice values.
     * Algorithm:
     * 1. Get the open categories.
     * 2. Check which of these open categories can be scored based on the dice
     * values.
     * 3. Return the list of possible categories.
     * Reference: None.
     *********************************************************************
     */
    public List<Category> getPossibleCategories(List<Integer> dice) {
        List<Category> openCategories = getOpenCategories();

        List<Category> possibleCategories = new ArrayList<>();
        for (Category category : openCategories) {
            if (Category.isPossibleCategory(dice, category)) {
                possibleCategories.add(category);
            }
        }

        return possibleCategories;
    }

    /**
     * *********************************************************************
     * Function Name: getApplicableCategories
     * Purpose: Returns a list of categories that are applicable based on the dice
     * values.
     * Parameters:
     * - dice: A list of integers representing the dice values.
     * Return Value: A list of categories that can be assigned based on the dice
     * values.
     * Algorithm:
     * 1. Get the open categories.
     * 2. Check which of these open categories can be assigned based on the dice
     * values.
     * 3. Return the list of applicable categories.
     * Reference: None.
     *********************************************************************
     */
    public List<Category> getApplicableCategories(List<Integer> dice) {
        List<Category> openCategories = getOpenCategories();

        List<Category> applicableCategories = new ArrayList<>();
        for (Category category : openCategories) {
            if (Category.isApplicableCategory(dice, category)) {
                applicableCategories.add(category);
            }
        }

        return applicableCategories;
    }

    /**
     * *********************************************************************
     * Function Name: getPlayerScore
     * Purpose: Calculates the total score for a specified player based on their
     * entries in the scorecard.
     * Parameters:
     * - player: The player whose score is to be calculated.
     * Return Value: The total score for the specified player.
     * Algorithm:
     * 1. Iterate through the scorecard and sum the points for the entries where the
     * winner is the specified player.
     * 2. Return the total score.
     * Reference: None.
     *********************************************************************
     */
    public int getPlayerScore(Player player) {
        int totalScore = 0;
        for (Map.Entry<Category, Optional<ScoreCardEntry>> entry : scoreCard.entrySet()) {
            if (entry.getValue().isPresent() && entry.getValue().get().getWinner().equals(player)) {
                totalScore += entry.getValue().get().getPoints();
            }
        }
        return totalScore;
    }

    /**
     * *********************************************************************
     * Function Name: getPlayerScores
     * Purpose: Calculates the total scores for multiple players.
     * Parameters:
     * - players: A list of players whose scores are to be calculated.
     * Return Value: A map of players and their total scores.
     * Algorithm:
     * 1. Iterate through the list of players and calculate their individual scores
     * using getPlayerScore().
     * 2. Return a map of players and their total scores.
     * Reference: None.
     *********************************************************************
     */
    public Map<Player, Integer> getPlayerScores(List<Player> players) {
        Map<Player, Integer> playerScores = new HashMap<>();
        for (Player player : players) {
            int playerScore = getPlayerScore(player);
            playerScores.put(player, playerScore);
        }
        return playerScores;
    }

    /**
     * *********************************************************************
     * Function Name: getWinner
     * Purpose: Determines the winner of the game by comparing the scores of all
     * players.
     * Parameters: None.
     * Return Value: The player with the highest score, or an empty optional if the
     * game is not yet full.
     * Algorithm:
     * 1. Check if the scorecard is full.
     * 2. Calculate the scores for all players.
     * 3. Identify the player with the highest score and return them.
     * Reference: None.
     *********************************************************************
     */
    public Optional<Player> getWinner() {
        if (!isFull()) {
            return Optional.empty();
        }

        List<Player> players = getPlayers();
        Map<Player, Integer> playerScores = getPlayerScores(players);

        int maxScore = 0;
        Player winner = null;
        for (Map.Entry<Player, Integer> entry : playerScores.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                winner = entry.getKey();
            }
        }

        return Optional.ofNullable(winner);
    }

    /**
     * *********************************************************************
     * Function Name: isDraw
     * Purpose: Determines if the game is a draw (i.e., multiple players have the
     * same highest score).
     * Parameters: None.
     * Return Value: True if there is a draw, false otherwise.
     * Algorithm:
     * 1. Check if the scorecard is full.
     * 2. Calculate the scores for all players.
     * 3. Identify the maximum score.
     * 4. Check how many players have the maximum score.
     * 5. Return true if more than one player has the highest score, otherwise
     * false.
     * Reference: None.
     *********************************************************************
     */
    public boolean isDraw() {
        if (!isFull()) {
            return false;
        }

        List<Player> players = getPlayers();
        Map<Player, Integer> playerScores = getPlayerScores(players);

        int maxScore = 0;
        for (Map.Entry<Player, Integer> entry : playerScores.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
            }
        }

        int count = 0;
        for (Map.Entry<Player, Integer> entry : playerScores.entrySet()) {
            if (entry.getValue() == maxScore) {
                count++;
            }
        }

        return count > 1;
    }

    /**
     * *********************************************************************
     * Function Name: getPlayers
     * Purpose: Returns a list of players who have won at least one category in the
     * scorecard.
     * Parameters: None.
     * Return Value: A list of players.
     * Algorithm:
     * 1. Iterate through the scorecard and collect players who have won at least
     * one category.
     * 2. Return the list of players.
     * Reference: None.
     *********************************************************************
     */
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();

        for (Map.Entry<Category, Optional<ScoreCardEntry>> entry : scoreCard.entrySet()) {
            if (entry.getValue().isPresent() && !players.contains(entry.getValue().get().getWinner())) {
                players.add(entry.getValue().get().getWinner());
            }
        }
        return players;
    }

    /**
     * *********************************************************************
     * Function Name: getString
     * Purpose: Returns a string representation of the scorecard with detailed
     * information.
     * Parameters: None.
     * Return Value: A formatted string representing the scorecard.
     * Algorithm:
     * 1. Initialize a StringBuilder for the output.
     * 2. Append the header row with category, round, winner, and points.
     * 3. Iterate through the scorecard and format each entry.
     * 4. Return the formatted string.
     * Reference: None.
     *********************************************************************
     */
    public String getString() {
        StringBuilder scoreCardString = new StringBuilder();
        scoreCardString.append(String.format("%-20s%-10s%-15s%-10s%n", "Category", "Round", "Winner", "Points"));
        scoreCardString.append("-".repeat(50)).append("\n");
        for (Map.Entry<Category, Optional<ScoreCardEntry>> entry : scoreCard.entrySet()) {
            scoreCardString.append(String.format("%-20s", Category.CATEGORY_NAMES.get(entry.getKey())));
            if (entry.getValue().isPresent()) {
                ScoreCardEntry scoreCardEntry = entry.getValue().get();
                scoreCardString.append(String.format("%-10s%-15s%-10s%n",
                        scoreCardEntry.getRound(),
                        scoreCardEntry.getWinner().getName(),
                        scoreCardEntry.getPoints()));
            } else {
                scoreCardString.append(String.format("%-10s%-15s%-10s%n", "-", "-", "-"));
            }
        }
        return scoreCardString.toString();
    }
}