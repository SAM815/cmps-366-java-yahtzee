package com.example.yahtzeegame.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public class Round {
    /**
     * *********************************************************************
     * Function Name: playRound
     * Purpose: Plays a round of Yahtzee, where each player takes a turn, scores
     * points,
     * and updates their scorecard.
     * Parameters:
     * - roundNumber (int): The number of the current round.
     * - scoreCard (ScoreCard): The scorecard to track the players' scores.
     * - players (List<Player>): A list of players participating in the round.
     * Return Value: The updated ScoreCard after the round is complete.
     * Algorithm:
     * 1. Get the current player scores from the scorecard.
     * 2. Create a player queue based on the scores using getPlayerQueue().
     * 3. Loop through each player's turn until the round is over or the scorecard
     * is full.
     * 4. For each player's turn, display the scorecard and prompt the player's
     * action.
     * 5. Calculate the score for the player's dice roll and update the scorecard.
     * 6. Return the updated scorecard after the round ends.
     * Reference: None.
     *********************************************************************
     */
    public static ScoreCard playRound(int roundNumber, ScoreCard scoreCard, List<Player> players) {
        Map<Player, Integer> playerScores = scoreCard.getPlayerScores(players);
        Queue<Player> playerQueue = getPlayerQueue(playerScores);
        System.out.println("Player Queue: " + playerQueue);

        ScoreCard currentScoreCard = scoreCard;

        boolean roundOver = playerQueue.isEmpty() || scoreCard.isFull();
        while (!roundOver) {
            Player player = playerQueue.poll();

            System.out.println(currentScoreCard.getString());

            System.out.println("It's " + player.getName() + "'s turn.");

            List<Integer> dice = Turn.playTurn(player, scoreCard);

            Optional<Category> scoredCategory = scoreCard.getMaxScoringCategory(dice);

            if (scoredCategory.isPresent()) {
                String categoryName = Category.CATEGORY_NAMES.get(scoredCategory.get());
                int score = ScoreCard.getScore(dice, scoredCategory.get());

                System.out.println(player.getName() + " scored " + score +
                        " points in the " + categoryName + " category.\n\n");
            }

            currentScoreCard = currentScoreCard.addEntry(roundNumber, player, dice);

            roundOver = playerQueue.isEmpty() || currentScoreCard.isFull();
        }

        System.out.println("Round ends");

        return currentScoreCard;
    }

    /**
     * *********************************************************************
     * Function Name: getPlayerQueue
     * Purpose: Creates a queue of players based on their current scores, resolving
     * ties if necessary.
     * Parameters:
     * - playerScores (Map<Player, Integer>): A map of players and their respective
     * scores.
     * Return Value: A Queue of players ordered based on their scores, considering
     * tie-breakers.
     * Algorithm:
     * 1. Retrieve the scores of the first two players from the map.
     * 2. If the players have equal scores, determine who goes first by resolving
     * the tie.
     * 3. If no tie exists, return a queue of players sorted by their scores.
     * 4. If a tie exists, return a queue ordered based on the tie-breaker result.
     * Reference: None.
     *********************************************************************
     */
    private static Queue<Player> getPlayerQueue(Map<Player, Integer> playerScores) {
        Queue<Player> playerQueue = new LinkedList<>();

        Iterator<Map.Entry<Player, Integer>> iterator = playerScores.entrySet().iterator();
        Player player1 = iterator.next().getKey();
        Player player2 = iterator.next().getKey();

        int player1Score = playerScores.get(player1);
        int player2Score = playerScores.get(player2);

        if (player1Score == player2Score) {
            if (player1Score == 0) {
                System.out.println("Determining who goes first by rolling a die.");
            } else {
                System.out.println("Both players have a score of " + player1Score + ". Conducting a tie breaker.");
            }
            return queueFromTieBreaker(player1, player2);
        }

        return enqueuePlayersByScore(playerScores);
    }

    /**
     * *********************************************************************
     * Function Name: queueFromTieBreaker
     * Purpose: Resolves a tie-breaker between two players by determining the order
     * of play.
     * Parameters:
     * - player1 (Player): The first player to compare in the tie-breaker.
     * - player2 (Player): The second player to compare in the tie-breaker.
     * Return Value: A Queue of players, ordered by the tie-breaker result.
     * Algorithm:
     * 1. Check which player is the human or computer.
     * 2. Use the tie-breaker logic to determine the player order.
     * 3. Return a queue with the players ordered based on the tie-breaker result.
     * Reference: None.
     *********************************************************************
     */
    private static Queue<Player> queueFromTieBreaker(Player player1, Player player2) {
        Queue<Player> playerQueue = new LinkedList<>();

        Player humanPlayer = player1.getName().equals("Human") ? player1 : player2;
        Player computerPlayer = player1.getName().equals("Computer") ? player1 : player2;

        if (ioFunctions.humanWonTieBreaker()) {
            playerQueue.add(humanPlayer);
            playerQueue.add(computerPlayer);
        } else {
            playerQueue.add(computerPlayer);
            playerQueue.add(humanPlayer);
        }

        return playerQueue;
    }

    /**
     * *********************************************************************
     * Function Name: enqueuePlayersByScore
     * Purpose: Sorts and creates a queue of players based on their scores in
     * ascending order.
     * Parameters:
     * - playerScores (Map<Player, Integer>): A map of players and their respective
     * scores.
     * Return Value: A Queue of players ordered by score, from lowest to highest.
     * Algorithm:
     * 1. Convert the map to a list of map entries.
     * 2. Sort the list by score using Map.Entry's comparingByValue method.
     * 3. Enqueue the players based on their sorted scores.
     * 4. Return the player queue.
     * Reference: None.
     *********************************************************************
     */
    private static Queue<Player> enqueuePlayersByScore(Map<Player, Integer> playerScores) {
        List<Map.Entry<Player, Integer>> playerScoreList = new ArrayList<>(playerScores.entrySet());

        playerScoreList.sort(Map.Entry.comparingByValue());

        Queue<Player> playerQueue = new LinkedList<>();
        for (Map.Entry<Player, Integer> entry : playerScoreList) {
            playerQueue.add(entry.getKey());
        }

        return playerQueue;
    }
}