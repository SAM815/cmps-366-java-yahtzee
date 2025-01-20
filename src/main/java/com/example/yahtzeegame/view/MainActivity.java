package com.example.yahtzeegame.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yahtzeegame.R;
import com.example.yahtzeegame.model.Game;
import com.example.yahtzeegame.model.SingletonGame;
import com.example.yahtzeegame.model.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    /**
     * *********************************************************************
     * Method Name: onCreate
     * Purpose: Initializes the MainActivity, sets up the window insets, and
     * configures
     * layout elements such as padding for system bars.
     * Parameters:
     * - savedInstanceState: Bundle containing the saved instance state.
     * Return Value: None
     * Algorithm:
     * 1. Enable edge-to-edge support for the activity's display.
     * 2. Set the content view with the main activity layout.
     * 3. Set a listener to apply window insets to the view, ensuring proper padding
     * for system bars (e.g., status bar and navigation bar).
     * Reference: None.
     *********************************************************************
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * *********************************************************************
     * Method Name: openFileLoadActivity
     * Purpose: Opens a file loading activity for the user to select a game file to
     * load.
     * Parameters:
     * - view: The view that was clicked to trigger this action.
     * Return Value: None
     * Algorithm:
     * 1. Clear the current game log.
     * 2. Create an intent to open a file from the file system.
     * 3. Launch the file loading activity and register the result with an
     * ActivityResultLauncher.
     * Reference: None.
     *********************************************************************
     */
    public void openFileLoadActivity(View view) {
        Log.getInstance().clear();
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        fileLoadActivityResultLauncher.launch(intent);
    }

    /**
     * *********************************************************************
     * Method Name: openNewGame
     * Purpose: Starts a new game and transitions to the game activity.
     * Parameters:
     * - view: The view that was clicked to trigger this action.
     * Return Value: None
     * Algorithm:
     * 1. Clear the current game log.
     * 2. Start a new game by calling the startNewGame method on the Game object.
     * 3. Create an intent to transition to the GameActivity.
     * 4. Start the GameActivity.
     * Reference: None.
     *********************************************************************
     */
    public void openNewGame(View view) {
        Log.getInstance().clear();
        SingletonGame.getGame().startNewGame();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    /**
     * *********************************************************************
     * Method Name: fileLoadActivityResultLauncher
     * Purpose: Handles the result of the file loading activity and loads the game
     * from the selected file.
     * Parameters:
     * - result: The result of the file loading activity.
     * Return Value: None
     * Algorithm:
     * 1. Check if the result code is RESULT_OK (successful file selection).
     * 2. Retrieve the URI from the result data.
     * 3. Call the loadGameFromUri method to deserialize and load the game from the
     * selected file.
     * Reference: None.
     *********************************************************************
     */
    private final ActivityResultLauncher<Intent> fileLoadActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent resultData = result.getData();
                    if (resultData != null) {
                        Uri uri = resultData.getData();
                        loadGameFromUri(uri);
                    }
                }
            });

    /**
     * *********************************************************************
     * Method Name: loadGameFromUri
     * Purpose: Loads a game from the provided URI and transitions to the game
     * activity.
     * Parameters:
     * - uri: The URI of the selected game file to load.
     * Return Value: None
     * Algorithm:
     * 1. Read the content of the selected file using an InputStream and
     * BufferedReader.
     * 2. Deserialize the game data from the file content into a Game object.
     * 3. Log the loading of the game.
     * 4. Set the loaded game into the SingletonGame instance.
     * 5. Transition to the GameActivity with the loaded game.
     * Reference: None.
     *********************************************************************
     */
    private void loadGameFromUri(Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String serialString = stringBuilder.toString();
        Game game = Game.deserialize(serialString);
        Log.getInstance().log("Game loaded from file: " + uri.getPath() + "\n");
        SingletonGame.setGame(game);

        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }
}
