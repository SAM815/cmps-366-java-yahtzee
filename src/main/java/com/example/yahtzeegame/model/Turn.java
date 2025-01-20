package com.example.yahtzeegame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Turn {
    /*
     * *********************************************************************
     * Function Name: playTurn
     * Purpose: To simulate a player's turn in a Yahtzee game, allowing the player
     * to roll dice up to three times, decide which dice to keep, and determine the
     * potential scoring categories based on the kept dice.
     * Parameters:
     * - Player player: The player taking the turn. This parameter is passed by
     * reference and is used to interact with the player's decisions and actions
     * during the turn.
     * - ScoreCard scoreCard: The scorecard associated with the player, used to
     * determine potential scoring categories. This parameter is passed by reference
     * and is used to evaluate the player's dice rolls.
     * Return Value: A list of integers representing the final set of dice that the
     * player ended with after their turn.
     * Algorithm:
     * 1. Initialize an empty list to keep track of the dice the player decides to
     * keep.
     * 2. Loop through up to three rolls in the player's turn.
     * 3. For each roll:
     * a. Display the current roll number and available categories based on kept
     * dice.
     * b. Display the player's current kept dice.
     * c. Get new dice rolls from the player for the dice not kept.
     * d. Display the newly rolled dice and recalculate potential categories.
     * e. If it's the third roll, end the turn automatically.
     * f. If the player wants help, provide assistance based on the current state.
     * g. If the player decides to stand, end the turn early.
     * h. Determine which dice the player wants to keep and add them to the kept
     * dice list.
     * i. If all five dice are kept, end the turn.
     * j. Optionally, display the player's pursuit strategy and target category.
     * 4. Output the final set of dice that the player ended with after their turn.
     * 5. Return the kept dice as the result of the player's turn.
     * Reference: None.
     */
    public static List<Integer> playTurn(Player player, ScoreCard scoreCard) {
        List<Integer> keptDice = new ArrayList<>();
        int currentRoll = 1;

        // Loop through up to 3 rolls in the player's turn
        while (currentRoll <= 3) {
            System.out.println("\nRoll " + currentRoll + " of 3\n");

            System.out.println("\nAvailable Categories:");
            // Show potential categories based on kept dice
            List<Category> potentialCategories = scoreCard.getPossibleCategories(keptDice);
            // ioFunctions.showCategories(potentialCategories);

            // Display the player's current kept dice
            System.out.println(player.getName() + "'s current dice: " + ioFunctions.toStringVector(keptDice) + '\n');

            // Get new dice rolls from the player (roll only the dice not kept)
            List<Integer> diceRolls = player.getDiceRoll(5 - keptDice.size());
            System.out.println(player.getName() + " rolled: " + ioFunctions.toStringVector(diceRolls) + '\n');

            // Recalculate potential categories with the newly rolled dice and show
            // potential categories based on kept dice
            potentialCategories = scoreCard.getPossibleCategories(keptDice);
            System.out.println("Potential categories:");
            ioFunctions.showCategories(potentialCategories);

            // If this is the third roll, the turn automatically ends
            if (currentRoll == 3) {
                System.out.println("\nEnd of turn.");
                keptDice.addAll(diceRolls);
                break;
            }

            // Check if the player wants help (only applies to the computer, where the help
            // system is triggered)
            if (player.wantsHelp()) {
                String help = new Computer().getHelpString(scoreCard, keptDice, diceRolls);
                System.out.println("Help: \n" + help + '\n');
            }

            // Check if the player decides to "stand" and keep their dice (ending the turn
            // early)
            if (player.wantsToStand(scoreCard, keptDice, diceRolls)) {
                System.out.println(player.getName() + " chose to stand.");
                keptDice.addAll(diceRolls);
                break;
            }

            // Determine which dice the player wants to keep based on the dice rolled and
            // kept
            List<Integer> diceToKeep = player.getDiceToKeep(scoreCard, diceRolls, keptDice);
            System.out.println(player.getName() + " kept: " + ioFunctions.toStringVector(diceToKeep) + '\n');

            // Add the kept dice to the keptDice list
            keptDice.addAll(diceToKeep);

            // If all 5 dice are kept, the turn ends
            if (keptDice.size() == 5) {
                System.out.println("All dice kept. End of turn.\n");
                break;
            }

            // Optionally, show the player's pursuit strategy (what category they are aiming
            // for)
            Optional<Map<Category, Reason>> userPursuit = player.getCategoryPursuits(scoreCard, keptDice);
            if (userPursuit.isPresent()) {
                System.out.println(player.getName() + "'s pursuit:");
                ioFunctions.showCategoryPursuits(userPursuit.get());
            }

            // Show the specific dice the player is aiming to roll for their target category
            Optional<Map.Entry<Category, List<Integer>>> userTarget = player.getTarget(scoreCard, keptDice);
            if (userTarget.isPresent()) {
                System.out.println(
                        player.getName() + "'s target: " + Category.CATEGORY_NAMES.get(userTarget.get().getKey()) +
                                " by rolling " + ioFunctions.toStringVector(userTarget.get().getValue()) + '\n');
            }

            currentRoll++;
        }

        // Output the final set of dice that the player ended with after their turn
        System.out.println(player.getName() + "'s final dice for round " + player.getName() + ": " +
                ioFunctions.toStringVector(keptDice) + '\n');
        // Return the kept dice (the result of the player's turn)
        return keptDice;
    }
}