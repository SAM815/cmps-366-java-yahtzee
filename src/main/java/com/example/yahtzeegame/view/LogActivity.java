package com.example.yahtzeegame.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yahtzeegame.R;
import com.example.yahtzeegame.model.Log;

public class LogActivity extends AppCompatActivity {

    /**
     * *********************************************************************
     * Method Name: onCreate
     * Purpose: Initializes the LogActivity, displays the game log, and sets up
     * the back button functionality.
     * Parameters:
     * - savedInstanceState: Bundle containing the saved instance state.
     * Return Value: None
     * Algorithm:
     * 1. Enable edge-to-edge support for the activity's display.
     * 2. Set the content view with the log activity layout.
     * 3. Retrieve the TextView that will display the log and set its text from the
     * Log instance.
     * 4. Set up a back button that, when clicked, finishes the activity and returns
     * to the previous screen.
     * Reference: None.
     *********************************************************************
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_log);

        TextView logTextView = findViewById(R.id.logText);
        logTextView.setText(Log.getInstance().toString());

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

    }
}