package com.example.yahtzeegame.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.yahtzeegame.R;
import com.example.yahtzeegame.model.Category;
import com.example.yahtzeegame.model.Die;
import com.example.yahtzeegame.model.Game;
import com.example.yahtzeegame.model.Help;
import com.example.yahtzeegame.model.Log;
import com.example.yahtzeegame.model.Player;
import com.example.yahtzeegame.model.ScoreCard;
import com.example.yahtzeegame.model.ScoreCardEntry;
import com.example.yahtzeegame.model.SingletonGame;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameActivity extends AppCompatActivity implements DieView.OnDieChangeListener {

    private LinearLayout mainLayout;
    private ConstraintLayout diceRollLayout;

    private TableLayout scorecardTable, playerScoreTable;
    private Button reRollButton, helpButton, skipSelection, homeButton, logButton, saveButton;
    private LinearLayout diceContainer;
    private TextView currentPlayerTextView;
    private TextView roundNumberTextView;
    private TextView rollCountTextView;
    private TextView messageTextView;

    /**
     * *********************************************************************
     * Method Name: onCreate
     * Purpose: Initializes the activity, sets up UI components, and starts the game
     * logic.
     * Parameters:
     * - savedInstanceState: A Bundle containing the activity's previously saved
     * state.
     * Return Value: None
     * Algorithm:
     * 1. Call super.onCreate() to initialize the activity.
     * 2. Set the content view for the activity.
     * 3. Find and store references to all necessary UI components.
     * 4. Initialize the activity based on the current game state.
     * Reference: None.
     *********************************************************************
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findUIComponents();
        initializeActivity();
    }

    /**
     * *********************************************************************
     * Method Name: onResume
     * Purpose: Re-initializes the activity every time it becomes visible to the
     * user.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Call super.onResume() to handle the resume lifecycle method.
     * 2. Re-initialize the activity to reflect the current game state.
     * Reference: None.
     *********************************************************************
     */

    @Override
    protected void onResume() {
        super.onResume();
        initializeActivity();
    }

    /**
     * *********************************************************************
     * Method Name: initializeActivity
     * Purpose: Initializes the UI components and updates the display according to
     * the game state.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Check if the game is over or needs a tie-breaker.
     * 2. If the game is over, display the finish screen.
     * 3. If a tie-breaker is needed, launch the FirstPlayerDetermineActivity.
     * 4. Otherwise, initialize the UI components for the game in progress.
     * Reference: None.
     *********************************************************************
     */
    private void initializeActivity() {
        Game game = SingletonGame.getGame();

        if (game.isOver()) {
            initializeUIComponents();
            initializeFinishDisplay();
        }

        if (game.needsTieBreaker()) {
            Intent intent = new Intent(this, FirstPlayerDetermineActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        initializeUIComponents();
    }

    /**
     * *********************************************************************
     * Method Name: findUIComponents
     * Purpose: Finds and stores references to all the UI components used in the
     * activity.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Find the references to various UI components like layouts, buttons, and
     * text views.
     * 2. Store references to these components for later use.
     * Reference: None.
     *********************************************************************
     */
    private void findUIComponents() {
        mainLayout = findViewById(R.id.gameLayout);
        diceRollLayout = findViewById(R.id.diceLayout);

        scorecardTable = findViewById(R.id.scorecardTable);
        playerScoreTable = findViewById(R.id.playerScoreTable);

        rollCountTextView = findViewById(R.id.rollCountTextView);
        currentPlayerTextView = findViewById(R.id.currentPlayerTextView);
        roundNumberTextView = findViewById(R.id.roundNumber);
        messageTextView = findViewById(R.id.messageTextView);

        reRollButton = findViewById(R.id.reRollButton);
        helpButton = findViewById(R.id.helpbutton);
        skipSelection = findViewById(R.id.skipSelectionButton);
        homeButton = findViewById(R.id.homeButton);
        logButton = findViewById(R.id.logButton);
        saveButton = findViewById(R.id.saveButton);

        diceContainer = diceRollLayout.findViewById(R.id.diceContainer);
    }

    /**
     * *********************************************************************
     * Method Name: initializeUIComponents
     * Purpose: Initializes and sets up all UI components for the game screen.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Initialize the information display, scorecard display, player score table,
     * dice display, and other UI components.
     * Reference: None.
     *********************************************************************
     */
    private void initializeUIComponents() {
        initializeInformationDisplay();
        initializeScoreCardDisplay();
        initializePlayerScoreTable();
        initializeDiceDisplay();
        initializeRollAgainButton();
        initializeHelpDisplay();
        initializeSkipSelectionButton();
        initializeHomeButton();
        initializeLogButton();
        initializeSaveButton();
    }

    /**
     * *********************************************************************
     * Method Name: initializeFinishDisplay
     * Purpose: Sets up the UI display when the game is finished, showing the
     * result.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Hide dice roll layout, help button, re-roll button, and skip selection
     * button.
     * 2. Display the game result message on the screen.
     * Reference: None.
     *********************************************************************
     */
    private void initializeFinishDisplay() {

        diceRollLayout.setVisibility(View.GONE);
        helpButton.setVisibility(View.GONE);
        reRollButton.setVisibility(View.GONE);
        skipSelection.setVisibility(View.GONE);

        Game game = SingletonGame.getGame();

        messageTextView.setText(game.getResult());
    }

    /**
     * *********************************************************************
     * Method Name: initializeScoreCardDisplay
     * Purpose: Sets up the scorecard display on the screen.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Set up the scorecard table with the necessary headers.
     * 2. Populate the table with the current scorecard entries for all categories.
     * 3. For each category, display information such as the round, winner, points,
     * and selection status.
     * Reference: None.
     *********************************************************************
     */
    private void initializeScoreCardDisplay() {
        scorecardTable.setStretchAllColumns(true);
        scorecardTable.setBackgroundColor(Color.WHITE);
        scorecardTable.setDividerDrawable(getResources().getDrawable(android.R.color.darker_gray));
        scorecardTable.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);

        // Clear the table before adding new rows
        scorecardTable.removeAllViews();

        // Add table header
        TableRow headerRow = new TableRow(this);
        headerRow.setBackgroundColor(Color.parseColor("#800080"));
        headerRow.setPadding(2, 2, 2, 2);

        String[] headers = { "Category", "Round", "Winner", "Points", "Select" };
        for (String header : headers) {
            TextView textView = new TextView(this);
            textView.setText(header);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(4, 4, 4, 4);
            textView.setTextSize(14);
            headerRow.addView(textView);
        }
        scorecardTable.addView(headerRow);

        // Add table rows
        ScoreCard scoreCard = SingletonGame.getGame().getScoreCard();
        List<Integer> dice = SingletonGame.getGame().getDice().stream().map(Die::getValue).collect(Collectors.toList());
        List<Category> applicableCategories = scoreCard.getApplicableCategories(dice);

        Game game = SingletonGame.getGame();
        List<Integer> markedAndLockedDice = game.getMarkedAndLockedDice();
        List<Category> possibleCategories = scoreCard.getPossibleCategories(markedAndLockedDice);

        for (Category category : Category.values()) {
            TableRow row = new TableRow(this);
            row.setPadding(2, 2, 2, 2);
            row.setBackgroundColor(category.ordinal() % 2 == 0 ? Color.parseColor("#E0E0E0") : Color.WHITE);

            TextView categoryTextView = new TextView(this);
            categoryTextView.setText(Category.CATEGORY_NAMES.get(category));
            categoryTextView.setBackgroundColor(Color.parseColor("#800080"));
            categoryTextView.setTextColor(Color.WHITE);
            categoryTextView.setPadding(4, 4, 4, 4);
            categoryTextView.setTextSize(14);
            row.addView(categoryTextView);

            Optional<ScoreCardEntry> entry = scoreCard.getEntry(category);

            String round = entry.map(scoreCardEntry -> String.valueOf(scoreCardEntry.getRound())).orElse("-");
            String playerName = entry.map(scoreCardEntry -> scoreCardEntry.getWinner().getName()).orElse("-");
            String points = entry.map(scoreCardEntry -> String.valueOf(scoreCardEntry.getPoints())).orElse("-");

            row.addView(createTextView(round));
            row.addView(createTextView(playerName));

            if (possibleCategories.contains(category) && game.getRollCount() < 3) {
                row.setBackgroundColor(getResources().getColor(R.color.potential_category_background));
            }

            TextView pointsTextView = createTextView(points);
            if (applicableCategories.contains(category)) {
                int score = Category.getScore(dice, category);
                pointsTextView.setText(String.valueOf(score));
                pointsTextView.setTextColor(getResources().getColor(R.color.potential_points));
                pointsTextView.setTypeface(null, Typeface.BOLD);
            }
            row.addView(pointsTextView);

            // Add select text
            if (isCurrentPlayerHuman()) {
                TextView selectTextView = new TextView(this);
                selectTextView.setText("Select");
                selectTextView.setBackgroundColor(Color.parseColor("#28A745"));
                selectTextView.setTextColor(Color.WHITE);
                selectTextView.setTextSize(14);
                selectTextView.setPadding(4, 4, 4, 4);
                selectTextView.setTypeface(null, Typeface.BOLD);
                if (!applicableCategories.contains(category)) {
                    selectTextView.setBackgroundColor(Color.parseColor("#DC3545"));
                    selectTextView.setText("N/A");
                    selectTextView.setEnabled(false);
                }
                selectTextView.setOnClickListener(v -> {
                    handleSelectCategoryClick(category);
                });
                row.addView(selectTextView);
            }

            scorecardTable.addView(row);
        }
    }

    /**
     * *********************************************************************
     * Method Name: initializePlayerScoreTable
     * Purpose: Initializes and displays the player score table.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Remove existing views from the player score table.
     * 2. Add rows for each player showing their name and score.
     * Reference: None.
     *********************************************************************
     */
    private void initializePlayerScoreTable() {
        playerScoreTable.removeViews(1, playerScoreTable.getChildCount() - 1);

        Game game = SingletonGame.getGame();
        ScoreCard scoreCard = game.getScoreCard();
        List<Player> players = game.getPlayers();
        Map<Player, Integer> playerScores = scoreCard.getPlayerScores(players);

        for (Player player : players) {
            TableRow row = new TableRow(this);

            TextView playerTextView = new TextView(this);
            playerTextView.setText(player.getName());
            playerTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            row.addView(playerTextView);

            TextView scoreTextView = new TextView(this);
            scoreTextView.setText(String.valueOf(playerScores.get(player)));
            scoreTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            row.addView(scoreTextView);

            playerScoreTable.addView(row);
        }
    }

    /**
     * *********************************************************************
     * Method Name: initializeInformationDisplay
     * Purpose: Displays game-related information such as round, roll count, and
     * current player.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Retrieve the current game state and update text fields for round, roll
     * count, and current player.
     * Reference: None.
     *********************************************************************
     */
    private void initializeInformationDisplay() {
        Game game = SingletonGame.getGame();
        List<Die> dice = game.getDice();

        int currentRound = game.getCurrentRound();
        roundNumberTextView.setText("" + currentRound);

        int rollCount = game.getRollCount();
        rollCountTextView.setText("" + rollCount);

        Optional<Player> currentPlayer = game.getCurrentPlayer();
        if (currentPlayer.isPresent()) {
            currentPlayerTextView.setText(currentPlayer.get().getName());
        } else {
            currentPlayerTextView.setText("No players in the queue.");
        }

    }

    /**
     * *********************************************************************
     * Method Name: initializeDiceDisplay
     * Purpose: Displays the dice on the screen.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Clear any existing dice views.
     * 2. Create new DieView objects for each die and add them to the dice container
     * layout.
     * Reference: None.
     *********************************************************************
     */
    private void initializeDiceDisplay() {
        Game game = SingletonGame.getGame();
        List<Die> dice = game.getDice();

        diceContainer.removeAllViews();
        for (Die die : dice) {
            DieView dieView = new DieView(this);
            dieView.setDie(die);
            dieView.setOnDieChangedListener(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);
            params.setMargins(0, 0, 15, 0);
            dieView.setLayoutParams(params);
            diceContainer.addView(dieView);
        }
    }

    /**
     * *********************************************************************
     * Method Name: initializeRollAgainButton
     * Purpose: Initializes the roll again button, including its visibility and
     * functionality.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Set the button's text based on whether the current player is human or
     * computer.
     * 2. Enable or disable the button based on roll count and dice status.
     * 3. Set an onClickListener to handle the roll again action.
     * Reference: None.
     *********************************************************************
     */
    private void initializeRollAgainButton() {
        Game game = SingletonGame.getGame();

        reRollButton.setEnabled(true);
        if (isCurrentPlayerHuman()) {
            reRollButton.setText("Roll Again");
            if (game.getRollCount() == 3)
                reRollButton.setEnabled(false);
            if (game.noDieToReRoll())
                reRollButton.setEnabled(false);
        } else {
            reRollButton.setText("Set Computer Roll");
        }

        reRollButton.setOnClickListener(v -> {
            game.reRollDice();
            messageTextView.setText("");
            initializeActivity();
        });
    }

    /**
     * *********************************************************************
     * Method Name: initializeSkipSelectionButton
     * Purpose: Initializes the skip selection button, showing it if applicable.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. If the current round's roll count is 3 and the current player is human,
     * make the button visible.
     * 2. Set an onClickListener to skip the current selection when clicked.
     * Reference: None.
     *********************************************************************
     */
    private void initializeSkipSelectionButton() {
        Game game = SingletonGame.getGame();
        if (game.getRollCount() == 3 && isCurrentPlayerHuman()) {
            skipSelection.setVisibility(View.VISIBLE);
        } else {
            skipSelection.setVisibility(View.GONE);
        }

        skipSelection.setOnClickListener(v -> {
            game.skipSelection();
            initializeActivity();
        });
    }

    /**
     * *********************************************************************
     * Method Name: initializeHelpDisplay
     * Purpose: Displays the help button and enables it if the current player is
     * human and the game is ongoing.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. If the game is not over and the current player is human, show and enable
     * the help button.
     * 2. Set an onClickListener to display the help message and mark dice for help.
     * Reference: None.
     *********************************************************************
     */
    private void initializeHelpDisplay() {
        Game game = SingletonGame.getGame();
        if (!game.isOver() && isCurrentPlayerHuman()) {
            helpButton.setVisibility(View.VISIBLE);
            helpButton.setEnabled(true);
        } else {
            helpButton.setVisibility(View.GONE);
            helpButton.setEnabled(false);
        }

        helpButton.setOnClickListener(v -> {
            Help help = game.getHelp();

            Log.getInstance().log(help.getMessage());
            messageTextView.setText(help.getMessage());
            game.markDiceForHelp();
            initializeDiceDisplay();
        });

    }

    /**
     * *********************************************************************
     * Method Name: initializeHomeButton
     * Purpose: Initializes the home button and navigates to the main activity if
     * the game is over.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. If the game is over, make the home button visible.
     * 2. Set an onClickListener to navigate back to the MainActivity.
     * Reference: None.
     *********************************************************************
     */
    private void initializeHomeButton() {
        Game game = SingletonGame.getGame();

        if (game.isOver()) {
            homeButton.setVisibility(View.VISIBLE);
        } else {
            homeButton.setVisibility(View.GONE);
        }

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * *********************************************************************
     * Method Name: initializeLogButton
     * Purpose: Initializes the log button to navigate to the LogActivity.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Set an onClickListener to navigate to the LogActivity when the button is
     * clicked.
     * Reference: None.
     *********************************************************************
     */
    private void initializeLogButton() {
        logButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LogActivity.class);
            startActivity(intent);
        });
    }

    /**
     * *********************************************************************
     * Method Name: initializeSaveButton
     * Purpose: Initializes the save button to allow the user to save the game
     * state.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Set an onClickListener to trigger the game saving process.
     * 2. Serialize the game and start an intent to create a new document.
     * Reference: None.
     *********************************************************************
     */
    private void initializeSaveButton() {
        saveButton.setOnClickListener(v -> {
            Game game = SingletonGame.getGame();
            String serializedGame = game.serialize();
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TITLE, "yahtzee_game.txt");

            intent.putExtra(Intent.EXTRA_TEXT, serializedGame);
            startActivityForResult(intent, 1);
        });
    }

    /**
     * *********************************************************************
     * Method Name: onActivityResult
     * Purpose: Handles the result of the save game operation and stores the
     * serialized game data.
     * Parameters:
     * - requestCode: The request code passed to startActivityForResult.
     * - resultCode: The result code returned by the save game activity.
     * - data: The data returned by the save game activity.
     * Return Value: None
     * Algorithm:
     * 1. If the result is successful, write the serialized game data to the output
     * stream of the selected file.
     * 2. Return to the MainActivity once the game has been saved.
     * Reference: None.
     *********************************************************************
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                try {
                    OutputStream outputStream = getContentResolver().openOutputStream(data.getData());
                    if (outputStream != null) {
                        outputStream.write(SingletonGame.getGame().serialize().getBytes());
                        outputStream.close();
                    }
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * *********************************************************************
     * Method Name: handleSelectCategoryClick
     * Purpose: Handles the selection of a category by the current player.
     * Parameters:
     * - category: The category selected by the player.
     * Return Value: None
     * Algorithm:
     * 1. Select the given category in the game.
     * 2. Re-initialize the activity to reflect the updated game state.
     * Reference: None.
     *********************************************************************
     */
    private void handleSelectCategoryClick(Category category) {
        Game game = SingletonGame.getGame().selectCategory(category);
        initializeActivity();
    }

    /**
     * *********************************************************************
     * Method Name: createTextView
     * Purpose: Creates a new TextView with the provided text.
     * Parameters:
     * - text: The text to display in the TextView.
     * Return Value: TextView
     * Algorithm:
     * 1. Create a new TextView.
     * 2. Set its text and padding.
     * 3. Return the TextView.
     * Reference: None.
     *********************************************************************
     */
    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(4, 4, 4, 4);
        textView.setTextSize(14);
        return textView;
    }

    /**
     * *********************************************************************
     * Method Name: isCurrentPlayerHuman
     * Purpose: Determines whether the current player is the human player.
     * Parameters: None
     * Return Value: boolean
     * Algorithm:
     * 1. Retrieve the current player from the game.
     * 2. Check if the player's name is "Human".
     * 3. Return true if the current player is human, false otherwise.
     * Reference: None.
     *********************************************************************
     */
    private boolean isCurrentPlayerHuman() {

        Game game = SingletonGame.getGame();
        if (game.isOver())
            return false;
        Player currentPlayer = game.getCurrentPlayer().orElse(null);
        String name = currentPlayer.getName();
        return Objects.equals(name, "Human");
    }

    /**
     * *********************************************************************
     * Method Name: onDieChanged
     * Purpose: Handles changes to the dice, updating the game state accordingly.
     * Parameters:
     * - die: The die that was changed.
     * Return Value: None
     * Algorithm:
     * 1. Retrieve all dice from the dice container.
     * 2. Update the game state with the new dice values.
     * 3. Re-initialize the activity to reflect the updated dice.
     * Reference: None.
     *********************************************************************
     */
    @Override
    public void onDieChanged(Die die) {

        List<Die> dice = new ArrayList<>();
        for (int i = 0; i < diceContainer.getChildCount(); i++) {
            DieView dieView = (DieView) diceContainer.getChildAt(i);
            dice.add(dieView.getDie());
        }

        SingletonGame.getGame().setDice(dice);
        initializeActivity();
    }

}
