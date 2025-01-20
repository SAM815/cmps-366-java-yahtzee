package com.example.yahtzeegame.model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Computer extends Player {

    // Constructor: Initialize the player with the name "Computer"
    /**
     * *********************************************************************
     * Function Name: Computer
     * Purpose: Initializes a new instance of the Computer class with the name
     * "Computer".
     * Parameters: None
     * Return Value: None
     * Algorithm: This constructor calls the superclass constructor with the string
     * "Computer" to set the name of the computer player.
     * Reference: None
     *********************************************************************
     */
    public Computer() {
        super("Computer");
    }

    /**
     * *********************************************************************
     * Function Name: generatePossibleFinalRolls
     * Purpose: Generates all possible final dice rolls by combining kept dice with
     * possible new rolls.
     * Parameters:
     * - keptDice (List<Integer>): List of dice values the player wants to keep.
     * Return Value: List of all possible final rolls (List<List<Integer>>).
     * Algorithm:
     * 1. Calculate all possible combinations of dice for the remaining slots.
     * 2. Concatenate kept dice with each possible roll.
     * 3. Sort and add the combined result to the list of possible rolls.
     * Reference: None
     *********************************************************************
     */
    public static List<List<Integer>> generatePossibleFinalRolls(List<Integer> keptDice) {
        List<List<Integer>> possibleRolls = helperFunctions.diceCombinations(5 - keptDice.size());
        List<List<Integer>> possibleFinalRolls = new ArrayList<>();

        for (List<Integer> roll : possibleRolls) {
            List<Integer> finalRoll = helperFunctions.concatenate(keptDice, roll);
            Collections.sort(finalRoll);
            possibleFinalRolls.add(finalRoll);
        }

        return possibleFinalRolls;
    }

    /**
     * *********************************************************************
     * Function Name: calculateScores
     * Purpose: Calculates potential scores for all possible final rolls based on
     * open categories in the scorecard.
     * Parameters:
     * - finalRolls (List<List<Integer>>): All possible final rolls.
     * - scoreCard (ScoreCard): The player's scorecard, which contains open and
     * filled categories.
     * Return Value: List of scores associated with each roll
     * (List<Map.Entry<List<Integer>, Integer>>).
     * Algorithm:
     * 1. Retrieve all open categories from the scorecard.
     * 2. For each roll, calculate the score for each open category.
     * 3. Sort the scores in descending order.
     * Reference: None
     *********************************************************************
     */
    public static List<Map.Entry<List<Integer>, Integer>> calculateScores(List<List<Integer>> finalRolls,
            ScoreCard scoreCard) {
        List<Map.Entry<List<Integer>, Integer>> scores = new ArrayList<>();
        List<Category> openCategories = scoreCard.getOpenCategories();

        for (List<Integer> finalRoll : finalRolls) {
            for (Category category : openCategories) {
                int score = ScoreCard.getScore(finalRoll, category);
                scores.add(new AbstractMap.SimpleEntry<>(finalRoll, score));
            }
        }

        scores.sort((a, b) -> b.getValue() - a.getValue());
        return scores;
    }

    /**
     * *********************************************************************
     * Function Name: findBestRoll
     * Purpose: Identifies the best roll that maximizes the score while minimizing
     * the difference from the current roll.
     * Parameters:
     * - scores (List<Map.Entry<List<Integer>, Integer>>): Potential scores for each
     * roll.
     * - diceRolls (List<Integer>): Current dice rolls.
     * Return Value: List of integers representing the best roll.
     * Algorithm:
     * 1. Identify the roll with the highest score.
     * 2. Among rolls with the same score, choose the one with the least difference
     * from the current roll.
     * Reference: None
     *********************************************************************
     */
    public static List<Integer> findBestRoll(List<Map.Entry<List<Integer>, Integer>> scores, List<Integer> diceRolls) {
        int maxScore = scores.get(0).getValue();
        List<Integer> bestRoll = scores.get(0).getKey();
        int bestRollDiffSize = helperFunctions.difference(bestRoll, diceRolls).size();

        for (Map.Entry<List<Integer>, Integer> entry : scores) {
            if (entry.getValue() < maxScore)
                break;

            List<Integer> rollDiff = helperFunctions.difference(entry.getKey(), diceRolls);
            if (rollDiff.size() < bestRollDiffSize) {
                bestRoll = entry.getKey();
                bestRollDiffSize = rollDiff.size();
            }
        }

        return bestRoll;
    }

    /**
     * *********************************************************************
     * Function Name: determineDiceToKeep
     * Purpose: Determines which dice should be kept based on the best roll
     * and the current state of kept dice and rolled dice.
     * Parameters:
     * - bestRoll (List<Integer>): The ideal roll the computer aims for.
     * - diceRolls (List<Integer>): The current dice rolls.
     * - keptDice (List<Integer>): Dice already kept from previous rolls.
     * Return Value: List of integers representing dice to keep from the current
     * roll.
     * Algorithm:
     * 1. Calculate the difference between the best roll and kept dice.
     * 2. Identify which dice from the current roll match this difference.
     * 3. Return the intersection of the needed dice and the current roll.
     * Reference: None
     *********************************************************************
     */
    public static List<Integer> determineDiceToKeep(List<Integer> bestRoll, List<Integer> diceRolls,
            List<Integer> keptDice) {
        List<Integer> diceNeeded = helperFunctions.difference(bestRoll, keptDice);
        return helperFunctions.intersection(diceNeeded, diceRolls);
    }

    /**
     * *********************************************************************
     * Function Name: getBestRoll
     * Purpose: Identifies the best possible roll based on the scorecard and kept
     * dice.
     * Parameters:
     * - scoreCard (ScoreCard): The current scorecard for the game.
     * - keptDice (List<Integer>): Dice already kept from previous rolls.
     * Return Value: List of integers representing the best roll to pursue.
     * Algorithm:
     * 1. Generate all possible final rolls using the kept dice.
     * 2. Calculate scores for all possible rolls.
     * 3. Find the roll that maximizes the score, considering the kept dice.
     * Reference: None
     *********************************************************************
     */

    public static List<Integer> getBestRoll(ScoreCard scoreCard, List<Integer> keptDice) {
        List<List<Integer>> possibleFinalRolls = generatePossibleFinalRolls(keptDice);
        List<Map.Entry<List<Integer>, Integer>> scores = calculateScores(possibleFinalRolls, scoreCard);
        return findBestRoll(scores, keptDice);
    }

    /**
     * *********************************************************************
     * Function Name: getDiceRoll
     * Purpose: Handles the dice roll for the computer's turn, with an option
     * for manual input by the human for testing.
     * Parameters:
     * - numDice (int): Number of dice to roll.
     * Return Value: List of integers representing the rolled dice.
     * Algorithm:
     * 1. Check if the human wants to roll for the computer.
     * 2. If yes, use the base class to handle the roll; otherwise, roll
     * automatically.
     * Reference: None
     *********************************************************************
     */
    @Override
    public List<Integer> getDiceRoll(int numDice) {
        boolean humanWantsToRoll = ioFunctions.humanWantsToRollForComputer();
        if (humanWantsToRoll) {
            return super.getDiceRoll(numDice);
        }
        return Dice.rollDice(numDice);
    }

    /**
     * *********************************************************************
     * Function Name: getDiceToKeep
     * Purpose: Determines which dice the computer should keep for the next roll.
     * Parameters:
     * - scoreCard (ScoreCard): The current scorecard for the game.
     * - diceRolls (List<Integer>): The current dice rolls.
     * - keptDice (List<Integer>): Dice already kept from previous rolls.
     * Return Value: List of integers representing the dice to keep.
     * Algorithm:
     * 1. Get assistance using the help function.
     * 2. Extract and return the dice to keep based on the provided help.
     * Reference: None
     *********************************************************************
     */
    @Override
    public List<Integer> getDiceToKeep(ScoreCard scoreCard, List<Integer> diceRolls, List<Integer> keptDice) {

        Help help = getHelp(scoreCard, keptDice, diceRolls);
        return help.getDiceToKeep();
    }

    /**
     * *********************************************************************
     * Function Name: getCategoryPursuits
     * Purpose: Identifies potential categories for scoring based on the current
     * dice and scorecard.
     * Parameters:
     * - scoreCard (ScoreCard): The current scorecard for the game.
     * - keptDice (List<Integer>): Dice already kept from previous rolls.
     * Return Value: Optional map of categories to reasons for pursuing them.
     * Algorithm:
     * 1. Generate all possible final rolls using the kept dice.
     * 2. For each open category, evaluate the potential score and dice needed.
     * 3. Populate a map with categories and associated reasons for pursuit.
     * Reference: None
     *********************************************************************
     */
    @Override
    public Optional<Map<Category, Reason>> getCategoryPursuits(ScoreCard scoreCard, List<Integer> keptDice) {
        List<List<Integer>> possibleFinalRolls = generatePossibleFinalRolls(keptDice);
        List<Category> possibleCategories = scoreCard.getPossibleCategories(keptDice);
        // write code to print possible categories and possible final rolls

        Map<Category, Reason> categoryPursuits = new HashMap<>();

        for (Category category : possibleCategories) {

            int minScore = Integer.MAX_VALUE;
            int maxScore = Integer.MIN_VALUE;
            int minDiceDiff = Integer.MAX_VALUE;
            int maxDiceDiff = Integer.MAX_VALUE;
            List<Integer> rollToGetMax = new ArrayList<>();
            List<Integer> rollToGetMin = new ArrayList<>();

            for (List<Integer> roll : possibleFinalRolls) {

                int diceDiff = helperFunctions.difference(roll, keptDice).size();
                int score = Category.getScore(roll, category);

                if (score >= maxScore && diceDiff <= maxDiceDiff) {
                    maxScore = score;
                    rollToGetMax = helperFunctions.difference(roll, keptDice);
                    maxDiceDiff = diceDiff;
                }

                if (score < minScore && diceDiff <= minDiceDiff) {
                    if (minScore == 0)
                        continue;
                    minScore = score;
                    rollToGetMin = helperFunctions.difference(roll, keptDice);
                    minDiceDiff = diceDiff;
                }
            }

            categoryPursuits.put(category,
                    new Reason(keptDice, category, maxScore, rollToGetMax, minScore, rollToGetMin));
        }
        return Optional.of(categoryPursuits);
    }

    /**
     * *********************************************************************
     * Function Name: getTarget
     * Purpose: Identifies the best category and strategy based on current dice
     * and the scorecard.
     * Parameters:
     * - scoreCard (ScoreCard): The current scorecard for the game.
     * - keptDice (List<Integer>): Dice already kept from previous rolls.
     * Return Value: Optional entry of the best category and the dice needed to
     * achieve it.
     * Algorithm:
     * 1. Determine the best roll to pursue using the scorecard and kept dice.
     * 2. Identify the category with the maximum score for the best roll.
     * 3. Return the target category and required dice.
     * Reference: None
     *********************************************************************
     */
    @Override
    public Optional<Map.Entry<Category, List<Integer>>> getTarget(ScoreCard scoreCard, List<Integer> keptDice) {
        List<Integer> bestRoll = getBestRoll(scoreCard, keptDice);
        Optional<Category> category = scoreCard.getMaxScoringCategory(bestRoll);
        if (!category.isPresent()) {
            return Optional.empty();
        }
        return Optional
                .of(new AbstractMap.SimpleEntry<>(category.get(), helperFunctions.difference(bestRoll, keptDice)));
    }

    /**
     * *********************************************************************
     * Function Name: wantsToStand
     * Purpose: Decides whether the computer should stop rolling based on
     * the current and kept dice.
     * Parameters:
     * - scoreCard (ScoreCard): The current scorecard for the game.
     * - keptDice (List<Integer>): Dice already kept from previous rolls.
     * - diceRolls (List<Integer>): The current dice rolls.
     * Return Value: Boolean indicating if the computer wants to stop rolling.
     * Algorithm:
     * 1. Determine dice to keep using the scorecard and current dice rolls.
     * 2. Compare the dice to keep with the current dice rolls.
     * 3. If they match, decide to stand; otherwise, continue rolling.
     * Reference: None
     *********************************************************************
     */
    @Override
    public boolean wantsToStand(ScoreCard scoreCard, List<Integer> keptDice, List<Integer> diceRolls) {
        List<Integer> diceToKeep = getDiceToKeep(scoreCard, diceRolls, keptDice);
        return helperFunctions.unorderedEqual(diceToKeep, diceRolls);
    }

    /**
     * *********************************************************************
     * Function Name: getCategorySelection
     * Purpose: Determines the category for scoring based on the current dice.
     * Parameters:
     * - scoreCard (ScoreCard): The current scorecard for the game.
     * - dice (List<Integer>): The dice to evaluate for scoring.
     * Return Value: The best category for scoring or null if none is applicable.
     * Algorithm:
     * 1. Retrieve the category with the maximum score for the given dice.
     * 2. Return the category or null if no valid category exists.
     * Reference: None
     *********************************************************************
     */
    public Category getCategorySelection(ScoreCard scoreCard, List<Integer> dice) {
        Optional<Category> category = scoreCard.getMaxScoringCategory(dice);
        return category.orElse(null);
    }

    /**
     * *********************************************************************
     * Function Name: wantsHelp
     * Purpose: Determines if the computer needs assistance. Always returns false
     * as the computer operates autonomously.
     * Parameters: None
     * Return Value: Boolean indicating if help is needed (always false).
     * Algorithm:
     * 1. Return false since the computer does not request help.
     * Reference: None
     *********************************************************************
     */
    @Override
    public boolean wantsHelp() {
        return false;
    }

    /**
     * *********************************************************************
     * Function Name: getHelpString
     * Purpose: Provides a detailed explanation of suggested actions based on the
     * current game state.
     * Parameters:
     * - scoreCard (ScoreCard): The current scorecard for the game.
     * - keptDice (List<Integer>): Dice already kept from previous rolls.
     * - diceRolls (List<Integer>): The current dice rolls.
     * Return Value: String with a comprehensive recommendation message.
     * Algorithm:
     * 1. Determine dice to keep using the scorecard and dice rolls.
     * 2. Analyze potential categories and suggest actions for maximizing score.
     * 3. Provide a clear explanation of whether to stand or continue rolling.
     * Reference: None
     *********************************************************************
     */
    public String getHelpString(ScoreCard scoreCard, List<Integer> keptDice, List<Integer> diceRolls) {
        List<Integer> diceToKeep = getDiceToKeep(scoreCard, diceRolls, keptDice);
        List<Integer> helpDice = helperFunctions.concatenate(keptDice, diceToKeep);
        Optional<Map<Category, Reason>> categoryPursuits = getCategoryPursuits(scoreCard, helpDice);
        Optional<Map.Entry<Category, List<Integer>>> target = getTarget(scoreCard, helpDice);

        StringBuilder helpMessage = new StringBuilder(
                "You should keep: " + ioFunctions.toStringVector(diceToKeep) + " because:\n");

        for (Map.Entry<Category, Reason> entry : categoryPursuits.get().entrySet()) {
            Reason reason = entry.getValue();

            if (reason.getMinScore() == 0) {
                helpMessage.append(" - You can get ").append(Category.CATEGORY_NAMES.get(reason.getPursuedCategory()))
                        .append(" with a score of ").append(reason.getMaxScore()).append(". For example, by rolling ")
                        .append(ioFunctions.toStringVector(reason.getRollToGetMax())).append("\n");
                continue;
            }
            helpMessage.append(" - You can get ").append(Category.CATEGORY_NAMES.get(reason.getPursuedCategory()))
                    .append(" with a minimum score of ").append(reason.getMinScore()).append(" by getting ")
                    .append(ioFunctions.toStringVector(reason.getRollToGetMin())).append(" and a maximum score of ")
                    .append(reason.getMaxScore()).append(" by rolling ")
                    .append(ioFunctions.toStringVector(reason.getRollToGetMax())).append("\n");
        }

        helpMessage.append("\nConsidering this, your target should be to get ");
        if (target.isPresent()) {
            helpMessage.append(Category.CATEGORY_NAMES.get(target.get().getKey()))
                    .append(". A way to do this would be to roll ")
                    .append(ioFunctions.toStringVector(target.get().getValue())).append(" in your subsequent rolls.\n");
        } else {
            helpMessage.append("None\n");
        }

        if (wantsToStand(scoreCard, keptDice, diceRolls)) {
            helpMessage.append("You should stand.\n");
        } else {
            helpMessage.append("Do not stand. You should keep rolling.\n");
        }

        if (diceToKeep.isEmpty()) {
            helpMessage.append("Do not keep any dice. You should roll all the dice.\n");
        } else {
            helpMessage.append("You should keep the following dice before you roll: ")
                    .append(ioFunctions.toStringVector(diceToKeep));
        }

        return helpMessage.toString();
    }

    /**
     * *********************************************************************
     * Function Name: getHelp
     * Purpose: Provides detailed advice on which dice to keep and the optimal
     * strategy to pursue.
     * Parameters:
     * - scoreCard (ScoreCard): The current scorecard for the game.
     * - keptDice (List<Integer>): Dice already kept from previous rolls.
     * - diceRolls (List<Integer>): The current dice rolls.
     * Return Value: Help object containing dice to keep, target category, and
     * whether to stand.
     * Algorithm:
     * 1. Evaluate possible combinations of remaining dice with the kept dice.
     * 2. Analyze open categories and determine applicable ones.
     * 3. Prioritize categories and dice combinations based on score and
     * feasibility.
     * 4. Return a Help object with suggestions for the next move.
     * Reference: None
     *********************************************************************
     */
    public Help getHelp(ScoreCard scoreCard, List<Integer> keptDice, List<Integer> diceRolls) {
        List<List<Integer>> remainingDiceCombinations = helperFunctions.diceCombinations(5 - keptDice.size());
        List<Category> openCategories = scoreCard.getOpenCategories();

        List<Integer> finalRoll = helperFunctions.concatenate(keptDice, diceRolls);

        if (openCategories.contains(Category.YAHTZEE) && Category.isApplicableCategory(finalRoll, Category.YAHTZEE)) {
            List<Integer> diceToKeep = diceRolls.stream().sorted().collect(Collectors.toList());
            return new Help(diceToKeep, Category.YAHTZEE, true);

        }
        if (openCategories.contains(Category.FIVE_STRAIGHT)) {
            if (Category.isApplicableCategory(finalRoll, Category.FIVE_STRAIGHT)) {
                List<Integer> diceToKeep = new ArrayList<>(diceRolls.stream().distinct().collect(Collectors.toList()));

                if (keptDice.contains(1) && diceRolls.contains(6)) {
                    diceToKeep = diceRolls.stream().filter(dice -> dice != 6).collect(Collectors.toList());
                }

                if (keptDice.contains(6) && diceRolls.contains(1)) {
                    diceToKeep = diceRolls.stream().filter(dice -> dice != 1).collect(Collectors.toList());
                }

                for (int keptDie : keptDice) {
                    if (diceToKeep.contains(keptDie)) {
                        diceToKeep.remove(Integer.valueOf(keptDie));
                    }
                }
                return new Help(diceToKeep, Category.FIVE_STRAIGHT, true);
            }
        }
        if (openCategories.contains(Category.FOUR_STRAIGHT)) {
            List<Integer> diceToKeep = new ArrayList<>(diceRolls.stream().distinct().collect(Collectors.toList()));

            if (Category.isApplicableCategory(finalRoll, Category.FOUR_STRAIGHT)) {
                if (keptDice.contains(1) && diceRolls.contains(6)) {
                    diceToKeep = diceRolls.stream().filter(dice -> dice != 6).collect(Collectors.toList());
                }

                if (keptDice.contains(6) && diceRolls.contains(1)) {
                    diceToKeep = diceRolls.stream().filter(dice -> dice != 1).collect(Collectors.toList());
                }

                for (int keptDie : keptDice) {
                    if (diceToKeep.contains(keptDie)) {
                        diceToKeep.remove(Integer.valueOf(keptDie));
                    }
                }

                List<Integer> finalDiceToKeep = diceToKeep;
                finalRoll = helperFunctions.concatenate(keptDice, finalDiceToKeep);
                if (Category.isPossibleCategory(finalRoll, Category.FIVE_STRAIGHT)
                        && openCategories.contains(Category.FIVE_STRAIGHT)) {
                    return new Help(finalDiceToKeep, Category.FIVE_STRAIGHT, false);
                }
                return new Help(diceToKeep, Category.FOUR_STRAIGHT, true);
            }
        }

        if (openCategories.contains(Category.FULL_HOUSE)) {
            finalRoll = helperFunctions.concatenate(keptDice, diceRolls);
            if (Category.isApplicableCategory(finalRoll, Category.FULL_HOUSE)) {
                return new Help(diceRolls.stream().sorted().collect(Collectors.toList()), Category.FULL_HOUSE, true);
            }
        }

        List<DiceAnalysis> diceAnalyses = new ArrayList<>();

        for (List<Integer> remainingDice : remainingDiceCombinations) {
            finalRoll = helperFunctions.concatenate(keptDice, remainingDice);
            for (Category category : openCategories) {
                if (Category.isApplicableCategory(finalRoll, category)) {

                    List<Integer> diceThatCanBeKept = helperFunctions.intersection(remainingDice, diceRolls);

                    int score = Category.getScore(finalRoll, category);
                    DiceAnalysis diceAnalysis = new DiceAnalysis(remainingDice, category, score, diceThatCanBeKept);
                    diceAnalyses.add(diceAnalysis);
                }
            }
        }

        if (diceAnalyses.isEmpty()) {
            List<Integer> diceToKeep = diceRolls.stream().sorted().collect(Collectors.toList());
            return new Help(diceToKeep, null, true);
        }

        // Sort diceAnalyses by DiceAnalysis.score first in descending order, then
        // length of diceThatCanBeKept
        List<DiceAnalysis> sortedAnalyses = diceAnalyses.stream()
                .sorted((a, b) -> b.diceThatCanBeKept.size() - a.diceThatCanBeKept.size())
                .sorted((a, b) -> b.score - a.score)
                .collect(Collectors.toList());

        List<Category> maxScoringCategories = sortedAnalyses.stream()
                .filter(diceAnalysis -> diceAnalysis.score == sortedAnalyses.get(0).score)
                .map(diceAnalysis -> diceAnalysis.category).collect(Collectors.toList());

        Category category = sortedAnalyses.get(0).category;
        List<Integer> diceToKeep = sortedAnalyses.get(0).diceThatCanBeKept;

        if (category == Category.FIVE_STRAIGHT || category == Category.FOUR_STRAIGHT) {
            diceToKeep = diceRolls.stream().distinct().collect(Collectors.toList());
        }

        if (maxScoringCategories.contains(Category.FIVE_STRAIGHT)
                && maxScoringCategories.contains(Category.FOUR_STRAIGHT)) {
            category = Category.FIVE_STRAIGHT;
        }

        if (maxScoringCategories.contains(Category.THREE_OF_A_KIND)
                && maxScoringCategories.contains(Category.FOUR_OF_A_KIND)) {
            category = Category.FOUR_OF_A_KIND;
        }

        if (category == Category.FULL_HOUSE) {
            List<Integer> finalDice = helperFunctions.concatenate(keptDice, diceToKeep);
            diceToKeep = diceRolls.stream().filter(dice -> Collections.frequency(finalDice, dice) >= 2)
                    .collect(Collectors.toList());
        }

        boolean wantsToStand = helperFunctions.unorderedEqual(diceToKeep, diceRolls);

        return new Help(diceToKeep, category, wantsToStand);

    }
}