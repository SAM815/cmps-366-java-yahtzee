package com.example.yahtzeegame.model;

import static com.example.yahtzeegame.model.Category.CATEGORY_NAMES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

public class Game {
    private ScoreCard scoreCard;
    private int currentRound;
    private List<Player> players;
    private List<Die> dice = Arrays.asList(new Die().roll(), new Die().roll(), new Die().roll(), new Die().roll(),
            new Die().roll());
    private Queue<Player> playerQueue = new LinkedList<>();
    private int rollCount = 1;

    public Game(ScoreCard scoreCard, int currentRound, List<Player> players) {
        this.scoreCard = scoreCard;
        this.currentRound = currentRound;
        this.players = players;
        this.playerQueue = calculatePlayerQueue();
    }

    public Game(ScoreCard scoreCard, int currentRound, List<Player> players, Queue<Player> playerQueue) {
        this.scoreCard = scoreCard;
        this.currentRound = currentRound;
        this.players = players;
        this.playerQueue = playerQueue;
    }

    public Game(Game other) {
        this.scoreCard = other.scoreCard;
        this.players = other.players;
        this.currentRound = other.currentRound;
        this.playerQueue = other.playerQueue;
        this.rollCount = other.rollCount;
        this.dice = other.dice;
    }

    /**
     * *********************************************************************
     * Function Name: deserialize
     * Purpose: Deserializes a string representation of a game and returns a Game
     * object.
     * Parameters:
     * String serial - the string representation of a game state.
     * Return Value: Game - the deserialized game object.
     * Algorithm:
     * 1. Split the serialized string into lines.
     * 2. Extract the round number and scorecard data from the string.
     * 3. Create a new Game object using the parsed information.
     * Reference: None.
     *********************************************************************
     */
    public static Game deserialize(String serial) {
        Human human = new Human();
        Computer computer = new Computer();
        List<Player> players = Arrays.asList(human, computer);

        List<String> lines = Arrays.asList(serial.split("\n"));

        int roundNumber = 1;
        for (String line : lines) {
            if (line.startsWith("Round: ")) {
                roundNumber = Integer.parseInt(line.substring(7).trim());
                System.out.println("Round number: " + roundNumber);
                break;
            }
        }

        int scorecardStart = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("Scorecard:")) {
                scorecardStart = i + 1;
                break;
            }
        }

        List<String> scorecardLines = lines.subList(scorecardStart, lines.size());
        String scorecardSerial = String.join("\n", scorecardLines);
        System.out.println("Scorecard serial: " + scorecardSerial);
        ScoreCard scoreCard = ScoreCard.deserialize(scorecardSerial, human, computer);

        return new Game(scoreCard, roundNumber, players);
    }

    /**
     * *********************************************************************
     * Function Name: getScoreCard
     * Purpose: Returns the current scorecard of the game.
     * Parameters: None
     * Return Value: ScoreCard - the current scorecard.
     * Algorithm:
     * 1. Simply return the current scorecard instance.
     * Reference: None.
     *********************************************************************
     */
    public ScoreCard getScoreCard() {
        return scoreCard;
    }

    /**
     * *********************************************************************
     * Function Name: setScoreCard
     * Purpose: Sets the scorecard to a new value.
     * Parameters:
     * ScoreCard scoreCard - the new scorecard to set.
     * Return Value: Game - the current Game instance with updated scorecard.
     * Algorithm:
     * 1. Assign the provided scorecard to the current scorecard.
     * 2. Return the updated Game object.
     * Reference: None.
     *********************************************************************
     */
    public Game setScoreCard(ScoreCard scoreCard) {
        this.scoreCard = scoreCard;
        return this;
    }

    /**
     * *********************************************************************
     * Function Name: serialize
     * Purpose: Converts the current game state to a string for storage.
     * Parameters: None
     * Return Value: String - the serialized game state.
     * Algorithm:
     * 1. Generate a string representation of the round and scorecard.
     * 2. Return the generated string.
     * Reference: None.
     *********************************************************************
     */
    public String serialize() {
        return "Round: " + currentRound + "\n" + "Scorecard:\n" + scoreCard.serialize() + "\n";
    }

    /**
     * *********************************************************************
     * Function Name: isOver
     * Purpose: Determines whether the game is over based on the scorecard.
     * Parameters: None
     * Return Value: boolean - true if the game is over, false otherwise.
     * Algorithm:
     * 1. Check if the scorecard is full to determine if the game has ended.
     * Reference: None.
     *********************************************************************
     */
    public boolean isOver() {
        return scoreCard.isFull();
    }

    /**
     * *********************************************************************
     * Function Name: isDraw
     * Purpose: Checks if the game ended in a draw.
     * Parameters: None
     * Return Value: boolean - true if the game is a draw, false otherwise.
     * Algorithm:
     * 1. Check if the scorecard indicates a draw.
     * Reference: None.
     *********************************************************************
     */
    public boolean isDraw() {
        return scoreCard.isDraw();
    }

    /**
     * *********************************************************************
     * Function Name: getPlayerScores
     * Purpose: Returns the scores of all players in the game.
     * Parameters: None
     * Return Value: Map<Player, Integer> - a map of players and their respective
     * scores.
     * Algorithm:
     * 1. Use the scorecard to retrieve the scores for each player.
     * 2. Return the player-score map.
     * Reference: None.
     *********************************************************************
     */
    public Map<Player, Integer> getPlayerScores() {
        return scoreCard.getPlayerScores(players);
    }

    /**
     * *********************************************************************
     * Function Name: getScoreText
     * Purpose: Returns a formatted string of the current player scores.
     * Parameters: None
     * Return Value: String - a formatted string displaying player scores.
     * Algorithm:
     * 1. Iterate through player scores and append them to a StringBuilder.
     * 2. Return the formatted string with player scores.
     * Reference: None.
     *********************************************************************
     */
    public String getScoreText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scores:\n");

        Map<Player, Integer> playerScores = getPlayerScores();

        for (Map.Entry<Player, Integer> playerScore : playerScores.entrySet()) {
            sb.append(playerScore.getKey().getName()).append(": ").append(playerScore.getValue()).append("\n");
        }
        sb.append("\n");

        return sb.toString();
    }

    /**
     * *********************************************************************
     * Function Name: setPlayerOrder
     * Purpose: Sets the order of players, optionally based on a tiebreaker.
     * Parameters:
     * Player player1 - the first player.
     * Player player2 - the second player.
     * Return Value: Game - the current Game instance with updated player order.
     * Algorithm:
     * 1. If a tiebreaker is needed, add players to the player queue.
     * 2. Return the updated Game object with the new player order.
     * Reference: None.
     *********************************************************************
     */
    public Game setPlayerOrder(Player player1, Player player2) {
        if (needsTieBreaker()) {
            playerQueue.add(player1);
            playerQueue.add(player2);
        }

        return this;
    }

    /**
     * *********************************************************************
     * Function Name: reRollDice
     * Purpose: Re-rolls the dice for the current player based on their decisions.
     * Parameters: None
     * Return Value: Game - the updated game state after re-rolling the dice.
     * Algorithm:
     * 1. Identify which dice are marked to be re-rolled.
     * 2. For the computer player, process its roll.
     * 3. Otherwise, process the player's roll based on the dice they wish to keep.
     * Reference: None.
     *********************************************************************
     */
    public Game reRollDice() {
        List<Die> rolledDice = dice.stream().filter(die -> !die.isLocked()).collect(Collectors.toList());
        if (rollCount < 3) {
            Log.getInstance().log(
                    getCurrentPlayer().map(Player::getName).orElse("Player")
                            + " rolls "
                            + rolledDice.stream().map(Die::getValue).collect(Collectors.toList()));
        }

        if (currentPlayerIsComputer()) {
            return processComputerRoll();
        }

        if (noDieToReRoll()) {
            return this;
        }

        return processRoll();
    }

    /**
     * *********************************************************************
     * Function Name: processRoll
     * Purpose: Processes the player's dice roll, keeping and re-rolling dice as
     * needed.
     * Parameters: None
     * Return Value: Game - the updated game state after processing the roll.
     * Algorithm:
     * 1. Separate the dice into locked, marked-for-lock, and unkept dice.
     * 2. Keep the marked dice and re-roll the unkept ones.
     * 3. Return the updated game state with the new dice values.
     * Reference: None.
     *********************************************************************
     */
    private Game processRoll() {

        List<Die> lockedDice = dice.stream().filter(Die::isLocked).collect(Collectors.toList());
        List<Die> markedForLock = dice.stream().filter(Die::isMarkedForLock).collect(Collectors.toList());
        List<Die> unkeptDice = dice.stream().filter(die -> !die.isMarkedForLock() && !die.isLocked())
                .collect(Collectors.toList());

        Log.getInstance().log(getCurrentPlayer()
                .map(Player::getName)
                .orElse("Player")
                + " keeps " + markedForLock.stream().map(Die::getValue).collect(Collectors.toList()) + " and re-rolls "
                + unkeptDice.stream().map(Die::getValue).collect(Collectors.toList()));

        dice = new LinkedList<>();
        dice.addAll(lockedDice);
        for (Die die : markedForLock) {
            dice.add(die.lock());
        }
        Log.getInstance().log(getCurrentPlayer().map(Player::getName).orElse("Player") + "'s dice so far: "
                + dice.stream().map(Die::getValue).collect(Collectors.toList()) + "\n");

        for (int i = 0; i < unkeptDice.size(); i++) {
            dice.add(new Die().roll());
        }

        rollCount++;

        return this;
    }

    /**
     * *********************************************************************
     * Function Name: processComputerRoll
     * Purpose: Handles the dice roll and decision-making for the computer player.
     * Parameters: None
     * Return Value: Game - the updated game state after the computer's roll.
     * Algorithm:
     * 1. Get the computer's strategy for keeping and re-rolling dice.
     * 2. Based on the computer's decision, either proceed with the roll or select a
     * category.
     * 3. Return the updated game state.
     * Reference: None.
     *********************************************************************
     */
    private Game processComputerRoll() {
        Computer computer = (Computer) playerQueue.peek();
        List<Integer> keptDice = getKeptDice();
        List<Integer> unkeptDice = getUnkeptDice();

        assert computer != null;

        Help help = computer.getHelp(scoreCard, keptDice, unkeptDice);
        if (rollCount < 3) {
            Log.getInstance().log("Computer's target category: " + CATEGORY_NAMES.get(help.getCategory()));
        }

        if (computer.wantsToStand(scoreCard, keptDice, unkeptDice) || rollCount >= 3) {
            List<Integer> diceValues = dice.stream().map(Die::getValue).collect(Collectors.toList());
            Category selectedCategory = computer.getCategorySelection(scoreCard, diceValues);
            return selectCategory(selectedCategory);
        } else {
            List<Integer> diceToKeep = computer.getDiceToKeep(scoreCard, unkeptDice, keptDice);

            int[] diceKeepValues = new int[7];
            for (int dieValue : diceToKeep) {
                diceKeepValues[dieValue]++;
            }

            List<Die> newDice = dice.stream().filter(Die::isLocked).collect(Collectors.toList());
            List<Die> unkeptDiceObjects = dice.stream().filter(die -> !die.isLocked()).collect(Collectors.toList());

            List<Die> diceToReRoll = new ArrayList<>();

            for (Die die : unkeptDiceObjects) {
                if (diceKeepValues[die.getValue()] > 0) {
                    newDice.add(die.markForLock());
                    diceKeepValues[die.getValue()]--;
                } else {
                    diceToReRoll.add(die);
                }
            }

            for (Die die : diceToReRoll) {
                newDice.add(die);
            }

            dice = newDice;
            return processRoll();
        }

    }

    /**
     * *********************************************************************
     * Function Name: selectCategory
     * Purpose: Selects the category for the current player's dice roll.
     * Parameters:
     * Category category - the category to be selected.
     * Return Value: Game - the updated game state after selecting the category.
     * Algorithm:
     * 1. If the category is valid, add the entry to the scorecard and update the
     * score.
     * 2. Return the updated game state.
     * Reference: None.
     *********************************************************************
     */
    public Game selectCategory(Category category) {
        if (playerQueue.isEmpty()) {
            return this;
        }
        List<Integer> rolledDiceValues = dice.stream().filter(die -> !die.isLocked()).map(Die::getValue)
                .collect(Collectors.toList());
        Log.getInstance().log(getCurrentPlayer().map(Player::getName).orElse("Player") + " rolls " + rolledDiceValues);

        List<Integer> diceValues = dice.stream().map(Die::getValue).collect(Collectors.toList());
        return playTurn(diceValues, category);
    }

    /**
     * *********************************************************************
     * Function Name: skipSelection
     * Purpose: Skips the selection of a category for the current turn.
     * Parameters: None
     * Return Value: Game - the updated game state after skipping the selection.
     * Algorithm:
     * 1. Skip the category selection and proceed to the next phase of the game.
     * 2. Return the updated game state.
     * Reference: None.
     *********************************************************************
     */
    public Game skipSelection() {
        if (playerQueue.isEmpty()) {
            return this;
        }

        List<Integer> rolledDiceValues = dice.stream().filter(die -> !die.isLocked()).map(Die::getValue)
                .collect(Collectors.toList());
        Log.getInstance().log(getCurrentPlayer().map(Player::getName).orElse("Player") + " rolls " + rolledDiceValues);

        List<Integer> diceValues = dice.stream().map(Die::getValue).collect(Collectors.toList());
        return playTurn(diceValues, null);
    }

    /**
     * *********************************************************************
     * Function Name: playTurn
     * Purpose: Plays a turn for the current player, including selecting a category.
     * Parameters:
     * List<Integer> dice - the list of dice values rolled.
     * Category category - the category to be selected.
     * Return Value: Game - the updated game state after the turn.
     * Algorithm:
     * 1. Validate the category selection and add the entry to the scorecard.
     * 2. Update the score and move to the next player's turn.
     * Reference: None.
     *********************************************************************
     */
    public Game playTurn(List<Integer> dice, Category category) {
        if (playerQueue.isEmpty()) {
            return this;
        }

        Player currentPlayer = playerQueue.poll();
        List<Category> applicableCategories = scoreCard.getApplicableCategories(dice);
        if (category != null && applicableCategories.contains(category)) {
            String categoryString = CATEGORY_NAMES.get(category);
            Log.getInstance().log(currentPlayer.getName() + " selects " + categoryString + "\n");
            scoreCard = scoreCard.addEntry(category, currentRound, currentPlayer, dice);
            int points = scoreCard.getEntry(category).get().getPoints();
            Log.getInstance()
                    .log(currentPlayer.getName() + " scores " + points + " points for " + categoryString + "\n");
        } else {
            Log.getInstance().log(currentPlayer.getName() + " skips selection\n");
        }

        Log.getInstance().log("End of " + currentPlayer.getName() + "'s turn\n");

        if (playerQueue.isEmpty() && !isOver()) {
            currentRound++;
            playerQueue = calculatePlayerQueue();
            Log.getInstance().log("Starting round " + currentRound);
            if (needsTieBreaker()) {
                Log.getInstance().log("Tiebreaker needed as two or more players have the same score" + "\n");
            } else {
                Log.getInstance().log("Player order: "
                        + playerQueue.stream().map(Player::getName).collect(Collectors.joining(", ")) + "\n");
            }

        }

        if (isOver()) {
            Log.getInstance().log(getResult());
        }

        this.dice = getNewDice();
        this.rollCount = 1;

        return this;
    }

    /**
     * *********************************************************************
     * Function Name: getPlayerQueue
     * Purpose: Retrieves the player queue, which determines the order of turns.
     * Parameters: None
     * Return Value: Queue<Player> - the player queue.
     * Algorithm:
     * 1. Return the player queue.
     * Reference: None.
     *********************************************************************
     */
    public Queue<Player> getPlayerQueue() {
        return playerQueue;
    }

    /**
     * *********************************************************************
     * Function Name: getCurrentRound
     * Purpose: Retrieves the current round number.
     * Parameters: None
     * Return Value: int - the current round number.
     * Algorithm:
     * 1. Return the current round number.
     * Reference: None.
     *********************************************************************
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * *********************************************************************
     * Function Name: needsTieBreaker
     * Purpose: Checks if a tie breaker is needed based on the scores of the
     * players.
     * Parameters: None
     * Return Value: boolean - true if a tie breaker is needed, false otherwise.
     * Algorithm:
     * 1. If the player queue is not empty, return false.
     * 2. Retrieve the list of player scores from the scorecard.
     * 3. Check if there are any duplicate scores, indicating the need for a tie
     * breaker.
     * 4. Return true if a tie breaker is needed, false otherwise.
     * Reference: None.
     *********************************************************************
     */
    public boolean needsTieBreaker() {
        if (!playerQueue.isEmpty()) {
            return false;
        }

        // If any two players have the same score, a tiebreaker is needed
        List<Integer> playerScores = players.stream().map(scoreCard::getPlayerScore).collect(Collectors.toList());
        return playerScores.stream().distinct().count() < playerScores.size();
    }

    /**
     * *********************************************************************
     * Function Name: calculatePlayerQueue
     * Purpose: Calculates and returns the player queue based on player scores and
     * any tie breakers.
     * Parameters: None
     * Return Value: Queue<Player> - the calculated player queue.
     * Algorithm:
     * 1. If the player queue is not empty, return it as is.
     * 2. If a tie breaker is needed, return an empty queue.
     * 3. Otherwise, sort the players by their scores in ascending order.
     * 4. Return the sorted player queue.
     * Reference: None.
     *********************************************************************
     */
    private Queue<Player> calculatePlayerQueue() {
        if (!playerQueue.isEmpty()) {
            return playerQueue;
        }

        if (needsTieBreaker()) {
            return new LinkedList<>();
        }

        return players.stream().sorted((p1, p2) -> scoreCard.getPlayerScore(p1) - scoreCard.getPlayerScore(p2))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * *********************************************************************
     * Function Name: getResult
     * Purpose: Returns the result of the game, including the winner and scores.
     * Parameters: None
     * Return Value: String - a string indicating the game result (winner or draw).
     * Algorithm:
     * 1. Check if the game is over.
     * 2. If there is a winner, return the winner's name and scores.
     * 3. If there is a draw, indicate the draw.
     * Reference: None.
     *********************************************************************
     */
    public String getResult() {
        if (!isOver()) {
            return "Game not over";
        }

        StringBuilder sb = new StringBuilder();
        if (isDraw()) {
            sb.append("It's a draw!\n");
        } else {
            Player winner = scoreCard.getWinner().get();
            sb.append(winner.getName()).append(" wins!\n");
        }

        sb.append("Final scores:\n");
        Map<Player, Integer> playerScores = getPlayerScores();
        for (Map.Entry<Player, Integer> playerScore : playerScores.entrySet()) {
            sb.append(playerScore.getKey().getName()).append(": ").append(playerScore.getValue()).append("\n");
        }

        return sb.toString();
    }

    /**
     * *********************************************************************
     * Function Name: getDice
     * Purpose: Retrieves the list of dice used in the current game.
     * Parameters: None
     * Return Value: List<Die> - the current list of dice.
     * Algorithm:
     * 1. Return the list of dice.
     * Reference: None.
     *********************************************************************
     */
    public List<Die> getDice() {
        return dice;
    }

    /**
     * *********************************************************************
     * Function Name: setDice
     * Purpose: Sets the dice for the current game round, applying any necessary
     * updates.
     * Parameters: List<Die> dice - the new list of dice to set.
     * Return Value: Game - the updated game state after setting the dice.
     * Algorithm:
     * 1. For each die in the current dice list, update it based on the new dice.
     * 2. If the current die is locked, preserve its state.
     * 3. If the current player is the computer or if the roll count exceeds 3, mark
     * the die as not locked.
     * 4. Return the updated game state.
     * Reference: None.
     *********************************************************************
     */
    public Game setDice(List<Die> dice) {
        // If current player is computer set markForLock as false
        List<Die> newDice = new ArrayList<>();
        for (int i = 0; i < this.dice.size(); i++) {

            Die currentDie = this.dice.get(i);
            Die newDie = dice.get(i);

            if (currentDie.isLocked() && !newDie.isLocked()) {
                newDie = currentDie;
            }

            if (currentPlayerIsComputer() || rollCount >= 3) {
                newDie = newDie.setMarkedForLock(false);
            }

            newDice.add(newDie);
        }
        this.dice = newDice;
        return this;
    }

    /**
     * *********************************************************************
     * Function Name: currentPlayerIsComputer
     * Purpose: Checks if the current player is the computer.
     * Parameters: None
     * Return Value: boolean - true if the current player is the computer, false
     * otherwise.
     * Algorithm:
     * 1. Check if the current player is an instance of the Computer class.
     * 2. Return true if the current player is the computer, false otherwise.
     * Reference: None.
     *********************************************************************
     */
    private boolean currentPlayerIsComputer() {
        return getCurrentPlayer().map(player -> player instanceof Computer).orElse(false);
    }

    /**
     * *********************************************************************
     * Function Name: getRollCount
     * Purpose: Retrieves the current number of rolls made in the game.
     * Parameters: None
     * Return Value: int - the number of rolls made.
     * Algorithm:
     * 1. Return the roll count.
     * Reference: None.
     *********************************************************************
     */
    public int getRollCount() {
        return rollCount;
    }

    /**
     * *********************************************************************
     * Function Name: getPlayers
     * Purpose: Retrieves the list of players in the game.
     * Parameters: None
     * Return Value: List<Player> - the list of players.
     * Algorithm:
     * 1. Return the list of players.
     * Reference: None.
     *********************************************************************
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * *********************************************************************
     * Function Name: getCurrentPlayer
     * Purpose: Retrieves the current player in the game.
     * Parameters: None
     * Return Value: Optional<Player> - the current player if available, otherwise
     * empty.
     * Algorithm:
     * 1. Return the current player wrapped in an Optional.
     * Reference: None.
     *********************************************************************
     */
    public Optional<Player> getCurrentPlayer() {
        return Optional.ofNullable(playerQueue.peek());
    }

    /**
     * *********************************************************************
     * Function Name: getKeptDice
     * Purpose: Retrieves the list of dice that are locked (kept) during the current
     * round.
     * Parameters: None
     * Return Value: List<Integer> - the list of values of the locked dice.
     * Algorithm:
     * 1. Filter the dice list to keep only the locked dice.
     * 2. Return a list of the values of the locked dice.
     * Reference: None.
     *********************************************************************
     */
    public List<Integer> getKeptDice() {
        return dice.stream().filter(Die::isLocked).map(Die::getValue).collect(Collectors.toList());
    }

    /**
     * *********************************************************************
     * Function Name: getUnkeptDice
     * Purpose: Retrieves the list of dice that are not locked or marked for keeping
     * during the current round.
     * Parameters: None
     * Return Value: List<Integer> - the list of values of the unkept dice.
     * Algorithm:
     * 1. Filter the dice list to exclude locked or marked-for-keep dice.
     * 2. Return a list of the values of the unkept dice.
     * Reference: None.
     *********************************************************************
     */
    public List<Integer> getUnkeptDice() {
        return dice.stream().filter(die -> !die.isMarkedForLock() && !die.isLocked()).map(Die::getValue)
                .collect(Collectors.toList());
    }

    /**
     * *********************************************************************
     * Function Name: getMarkedForKeepDice
     * Purpose: Retrieves the list of dice that are marked for keeping during the
     * current round.
     * Parameters: None
     * Return Value: List<Integer> - the list of values of the dice marked for
     * keeping.
     * Algorithm:
     * 1. Filter the dice list to include only the marked-for-keep dice.
     * 2. Return a list of the values of the marked-for-keep dice.
     * Reference: None.
     *********************************************************************
     */
    public List<Integer> getMarkedForKeepDice() {
        return dice.stream().filter(Die::isMarkedForLock).map(Die::getValue).collect(Collectors.toList());
    }

    /**
     * *********************************************************************
     * Function Name: getMarkedAndLockedDice
     * Purpose: Retrieves the list of dice that are either marked for keeping or
     * locked during the current round.
     * Parameters: None
     * Return Value: List<Integer> - the list of values of the marked or locked
     * dice.
     * Algorithm:
     * 1. Filter the dice list to include dice that are either marked for keeping or
     * locked.
     * 2. Return a list of the values of the marked or locked dice.
     * Reference: None.
     *********************************************************************
     */
    public List<Integer> getMarkedAndLockedDice() {
        return dice.stream().filter(die -> die.isMarkedForLock() || die.isLocked()).map(Die::getValue)
                .collect(Collectors.toList());
    }

    /**
     * *********************************************************************
     * Function Name: noDieToReRoll
     * Purpose: Checks if there are any dice left to reroll in the current round.
     * Parameters: None
     * Return Value: boolean - true if there are no dice left to reroll, false
     * otherwise.
     * Algorithm:
     * 1. Check if all dice are either locked or marked for keeping, or if the roll
     * count has exceeded 3.
     * 2. Return true if no dice can be rerolled, otherwise return false.
     * Reference: None.
     *********************************************************************
     */
    public boolean noDieToReRoll() {
        boolean allDiceKept = dice.stream().allMatch(die -> die.isMarkedForLock() || die.isLocked());
        boolean turnOver = rollCount >= 3;
        return allDiceKept || turnOver;
    }

    /**
     * *********************************************************************
     * Function Name: startNewGame
     * Purpose: Starts a new game by resetting relevant game state.
     * Parameters: None
     * Return Value: Game - the updated game state after starting a new game.
     * Algorithm:
     * 1. Log the start of the new game.
     * 2. Reset the scorecard, current round, and players.
     * 3. Initialize the player queue and roll count.
     * 4. Set the dice to their initial values.
     * 5. Return the updated game state.
     * Reference: None.
     *********************************************************************
     */
    public Game startNewGame() {
        Log.getInstance().log("Starting new game\n");
        scoreCard = new ScoreCard();
        currentRound = 1;
        players = Arrays.asList(new Human(), new Computer());
        playerQueue = new LinkedList<>();
        rollCount = 1;
        dice = getNewDice();
        return this;
    }

    /**
     * *********************************************************************
     * Function Name: getNewDice
     * Purpose: Generates and returns a list of new dice for the start of the game.
     * Parameters: None
     * Return Value: List<Die> - a list of newly rolled dice.
     * Algorithm:
     * 1. Create and roll 5 new dice.
     * 2. Return the list of rolled dice.
     * Reference: None.
     *********************************************************************
     */
    private List<Die> getNewDice() {
        return Arrays.asList(new Die().roll(), new Die().roll(), new Die().roll(), new Die().roll(), new Die().roll());
    }

    /**
     * *********************************************************************
     * Function Name: getHelp
     * Purpose: Returns the help for the computer player's current situation.
     * Parameters: None
     * Return Value: Help - the help object containing guidance for the computer's
     * turn.
     * Algorithm:
     * 1. If it's the third roll, provide help based on the current dice values.
     * 2. Otherwise, provide help based on the kept and unkept dice.
     * Reference: None.
     *********************************************************************
     */
    public Help getHelp() {
        Computer computer = new Computer();
        if (rollCount == 3) {
            return computer.getHelp(scoreCard, dice.stream().map(Die::getValue).collect(Collectors.toList()),
                    new ArrayList<>());
        }
        return computer.getHelp(scoreCard, getKeptDice(), getUnkeptDice());
    }

    /**
     * *********************************************************************
     * Function Name: markDiceForHelp
     * Purpose: Marks dice based on the help provided for the computer player's
     * strategy.
     * Parameters: None
     * Return Value: Game - the updated game state after marking the dice for help.
     * Algorithm:
     * 1. Apply the computer's strategy to mark dice for keeping.
     * 2. Return the updated game state.
     * Reference: None.
     *********************************************************************
     */
    public Game markDiceForHelp() {
        Help help = getHelp();

        List<Integer> diceToKeep = help.getDiceToKeep();
        int[] numberFrequency = new int[7];
        for (int die : diceToKeep) {
            numberFrequency[die]++;
        }

        List<Die> newDice = new ArrayList<>(dice.stream().filter(Die::isLocked).collect(Collectors.toList()));
        for (Die die : dice.stream().filter(die -> !die.isLocked()).collect(Collectors.toList())) {
            if (numberFrequency[die.getValue()] > 0) {
                newDice.add(die.markForHelp());
                numberFrequency[die.getValue()]--;
            } else {
                newDice.add(die);
            }
        }

        dice = newDice;

        return this;

    }

    /**
     * *********************************************************************
     * Function Name: playRound
     * Purpose: Plays a round of the game, updating the scorecard and displaying
     * results.
     * Parameters: None
     * Return Value: Game - the updated game state after the round is played.
     * Algorithm:
     * 1. Play the round and update the scorecard.
     * 2. Display the scores after the round.
     * 3. Return the updated game state.
     * Reference: None.
     *********************************************************************
     */
    public Game playRound() {
        if (isOver()) {
            System.out.println("The game is over!");
            return this;
        }

        System.out.println("Round " + currentRound);
        showScores();

        ScoreCard newScoreCard = Round.playRound(currentRound, scoreCard, players);

        System.out.println(newScoreCard.getString());

        Game result = new Game(newScoreCard, currentRound + 1, players);

        result.showScores();

        return result;
    }

    /**
     * *********************************************************************
     * Function Name: showScores
     * Purpose: Displays the scores of all players in the game.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Retrieve the player scores using the getPlayerScores method.
     * 2. Iterate through each player and print their name and score.
     * 3. Print a newline after displaying all scores.
     * Reference: None.
     *********************************************************************
     */
    public void showScores() {
        System.out.println("Scores:");

        Map<Player, Integer> playerScores = getPlayerScores();

        for (Map.Entry<Player, Integer> playerScore : playerScores.entrySet()) {
            System.out.println(playerScore.getKey().getName() + ": " + playerScore.getValue());
        }
        System.out.println();
    }

}