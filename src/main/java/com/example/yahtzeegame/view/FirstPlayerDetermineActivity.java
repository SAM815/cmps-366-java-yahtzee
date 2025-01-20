package com.example.yahtzeegame.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yahtzeegame.R;
import com.example.yahtzeegame.model.Computer;
import com.example.yahtzeegame.model.Dice;
import com.example.yahtzeegame.model.Game;
import com.example.yahtzeegame.model.SingletonGame;
import com.example.yahtzeegame.model.Human;
import com.example.yahtzeegame.model.Log;
import com.example.yahtzeegame.model.Player;

public class FirstPlayerDetermineActivity extends AppCompatActivity {

    private ImageView humanDieImage;
    private ImageView computerDieImage;
    private int humanDieValue;
    private int computerDieValue;
    private Button humanRollButton;
    private Button humanSetButton;
    private Button computerRollButton;
    private Button computerSetButton;
    private boolean humanRolled = false;
    private boolean computerRolled = false;
    private TextView winnerTextView;
    private Button startRoundButton;

    /**
     * *********************************************************************
     * Method Name: onCreate
     * Purpose: Initializes the activity, sets up listeners for buttons, and
     * handles the UI interactions for determining the first player.
     * Parameters:
     * - savedInstanceState: Bundle containing saved instance state.
     * Return Value: None
     * Algorithm:
     * 1. Set the content view for the activity.
     * 2. Find and initialize all necessary UI elements.
     * 3. Set up the onClickListeners for roll and set buttons for both players.
     * 4. Handle interactions and roll logic for human and computer dice.
     * 5. Launch a dialog for manual dice set if required.
     * Reference: None.
     *********************************************************************
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_player_determine_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        humanDieImage = findViewById(R.id.human_die_image);
        computerDieImage = findViewById(R.id.computer_die_image);

        humanRollButton = findViewById(R.id.human_roll_button);
        humanSetButton = findViewById(R.id.human_set_button);
        computerRollButton = findViewById(R.id.computer_roll_button);
        computerSetButton = findViewById(R.id.computer_set_button);
        winnerTextView = findViewById(R.id.winnerTextView);
        startRoundButton = findViewById(R.id.startRoundButton);

        humanRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humanDieValue = Dice.rollDie();
                updateDieImage(humanDieImage, humanDieValue);
                rotateDieImage(humanDieImage);
                disableButtons(humanRollButton, humanSetButton);
                humanRolled = true;
                checkWinner();
            }
        });

        humanSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetDiceDialog(humanDieImage, humanRollButton, humanSetButton);
            }
        });

        computerRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computerDieValue = Dice.rollDie();
                updateDieImage(computerDieImage, computerDieValue);
                rotateDieImage(computerDieImage);
                disableButtons(computerRollButton, computerSetButton);
                computerRolled = true;
                checkWinner();
            }
        });

        computerSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetDiceDialog(computerDieImage, computerRollButton, computerSetButton);
            }
        });

        startRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPlayerDetermineActivity.this, GameActivity.class);
                intent.putExtra("firstPlayer",
                        winnerTextView.getText().toString().contains("Human") ? "Human" : "Computer");
                startActivity(intent);
            }
        });
    }

    /**
     * *********************************************************************
     * Method Name: disableButtons
     * Purpose: Disables the roll and set buttons for a player after they roll
     * or set their dice.
     * Parameters:
     * - rollButton: Button to roll the die.
     * - setButton: Button to set the die manually.
     * Return Value: None
     * Algorithm:
     * 1. Set both the roll and set buttons as disabled.
     * Reference: None.
     *********************************************************************
     */
    private void disableButtons(Button rollButton, Button setButton) {
        rollButton.setEnabled(false);
        setButton.setEnabled(false);
    }

    /**
     * *********************************************************************
     * Method Name: showSetDiceDialog
     * Purpose: Displays a dialog that allows the user to manually set the die
     * value.
     * Parameters:
     * - dieImage: ImageView displaying the die.
     * - rollButton: Button to roll the die.
     * - setButton: Button to manually set the die value.
     * Return Value: None
     * Algorithm:
     * 1. Create and display a dialog to let the user manually set the die value.
     * 2. Update the die image when the user adjusts the value using the up and down
     * buttons.
     * 3. When the user confirms, update the die value and disable buttons.
     * 4. Check the winner after both players set their dice.
     * Reference: None.
     *********************************************************************
     */
    private void showSetDiceDialog(final ImageView dieImage, final Button rollButton, final Button setButton) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_set_die);

        final ImageView diceImage = dialog.findViewById(R.id.diceImage);
        Button buttonUp = dialog.findViewById(R.id.buttonUp);
        Button buttonDown = dialog.findViewById(R.id.buttonDown);
        Button buttonSetFinal = dialog.findViewById(R.id.buttonSetFinal);

        final int[] diceValue = { 1 };
        updateDieImage(diceImage, diceValue[0]);

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (diceValue[0] < 6) {
                    diceValue[0]++;
                    updateDieImage(diceImage, diceValue[0]);
                }
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (diceValue[0] > 1) {
                    diceValue[0]--;
                    updateDieImage(diceImage, diceValue[0]);
                }
            }
        });

        buttonSetFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDieImage(dieImage, diceValue[0]);
                disableButtons(rollButton, setButton);
                if (dieImage == humanDieImage) {
                    humanDieValue = diceValue[0];
                    humanRolled = true;
                } else {
                    computerDieValue = diceValue[0];
                    computerRolled = true;
                }
                dialog.dismiss();
                checkWinner();
            }
        });

        dialog.show();
    }

    /**
     * *********************************************************************
     * Method Name: updateDieImage
     * Purpose: Updates the die image based on the given value.
     * Parameters:
     * - dieImage: ImageView displaying the die.
     * - result: Integer representing the die value.
     * Return Value: None
     * Algorithm:
     * 1. Retrieve the resource ID for the corresponding die image based on the
     * value.
     * 2. Set the image resource for the die ImageView.
     * Reference: None.
     *********************************************************************
     */
    private void updateDieImage(ImageView dieImage, int result) {
        int resId = getResources().getIdentifier("die_" + result, "drawable", getPackageName());
        dieImage.setImageResource(resId);
    }

    /**
     * *********************************************************************
     * Method Name: rotateDieImage
     * Purpose: Rotates the die image with an animation.
     * Parameters:
     * - dieImage: ImageView displaying the die to be rotated.
     * Return Value: None
     * Algorithm:
     * 1. Create a RotateAnimation with a full rotation (360 degrees).
     * 2. Set the duration of the animation.
     * 3. Start the animation on the die image.
     * Reference: None.
     *********************************************************************
     */
    private void rotateDieImage(ImageView dieImage) {
        RotateAnimation rotate = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        dieImage.startAnimation(rotate);
    }

    /**
     * *********************************************************************
     * Method Name: checkWinner
     * Purpose: Compares the die values of the human and computer to determine the
     * winner.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Check if both players have rolled or set their dice.
     * 2. Compare the die values and determine the winner or if it's a tie.
     * 3. If there is a winner, update the UI with the winner's message.
     * 4. If it's a tie, reset the game and allow another round of rolls.
     * 5. Set the player order for the game using SingletonGame.
     * Reference: None.
     *********************************************************************
     */
    private void checkWinner() {
        if (humanRolled && computerRolled) {
            String winnerMessage;
            Player firstPlayer;
            Player secondPlayer;

            if (humanDieValue > computerDieValue) {
                winnerMessage = "Human wins!";
                Log.getInstance().log("Human wins the tiebreaker.\n");
                firstPlayer = new Human();
                secondPlayer = new Computer();
            } else if (computerDieValue > humanDieValue) {
                winnerMessage = "Computer wins!";
                Log.getInstance().log("Computer wins the tiebreaker.\n");
                firstPlayer = new Computer();
                secondPlayer = new Human();
            } else {
                winnerMessage = "It's a tie!";
                Log.getInstance().log("The tiebreaker is a tie.\n");
                humanRollButton.setEnabled(true);
                humanSetButton.setEnabled(true);
                computerRollButton.setEnabled(true);
                computerSetButton.setEnabled(true);
                humanRolled = false;
                computerRolled = false;
                return;
            }

            Game game = SingletonGame.getGame();
            Game updatedGame = game.setPlayerOrder(firstPlayer, secondPlayer);
            SingletonGame.setGame(updatedGame);

            // Show the winner message in the TextView
            winnerTextView.setText(winnerMessage);
            startRoundButton.setVisibility(View.VISIBLE);
        }
    }
}