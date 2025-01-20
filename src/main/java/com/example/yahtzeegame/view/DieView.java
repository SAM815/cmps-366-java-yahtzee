package com.example.yahtzeegame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.yahtzeegame.R;
import com.example.yahtzeegame.model.Die;

public class DieView extends androidx.appcompat.widget.AppCompatImageView {

    private Die die;
    private GestureDetector gestureDetector;
    private Paint overlayPaint;
    private Paint textPaint;

    private OnDieChangeListener onDieChangedListener;

    public DieView(Context context) {
        this(context, null);
    }

    public DieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
        setClickable(true);
        init();
    }

    /**
     * *********************************************************************
     * Function Name: init
     * Purpose: Initializes the die, gesture detector, and paints for the view.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Creates a new Die object, sets its value, marks it for help, and locks it.
     * 2. Initializes a gesture detector to handle scroll, click, long press, and
     * fling gestures.
     * 3. Sets up paints for overlay and text, with specified colors and text size.
     * 4. Calls updateDieImage() to set the initial image for the die.
     * Reference: None.
     *********************************************************************
     */
    private void init() {
        die = new Die().setValue(6).markForHelp().lock();

        // Initialize gesture detector for scroll and click
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                final float SCROLL_THRESHOLD = 10;
                if (Math.abs(distanceY) > SCROLL_THRESHOLD) {
                    if (distanceY > 0) {
                        incrementDie();
                    } else {
                        decrementDie();
                    }
                }
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                float y = e.getY();
                float height = getHeight();

                // If clicked on top third of the view, increment the die
                if (y < height / 3) {
                    incrementDie();
                } else if (y > height * 2 / 3) {
                    decrementDie();
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                toggleMarkForLock();
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                final float FLING_THRESHOLD = 500;
                if (Math.abs(velocityY) > FLING_THRESHOLD) {
                    rollDie();
                }
                return true;
            }

        });

        // Paint for green overlay (isMarkedForLock status)
        overlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        overlayPaint.setColor(Color.argb(100, 144, 238, 144)); // Light green with transparency

        // Paint for text drawing (padlock and checkmark)
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(48); // Adjust text size for emojis
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.LEFT);

        // Set initial image
        updateDieImage();
    }

    /**
     * *********************************************************************
     * Function Name: setOnDieChangedListener
     * Purpose: Sets the listener to notify when the die changes.
     * Parameters: OnDieChangeListener onDieChangedListener - the listener to notify
     * on die change.
     * Return Value: None
     * Algorithm:
     * 1. Assign the provided listener to the onDieChangedListener variable.
     * Reference: None.
     *********************************************************************
     */
    public void setOnDieChangedListener(OnDieChangeListener onDieChangedListener) {
        this.onDieChangedListener = onDieChangedListener;
    }

    /**
     * *********************************************************************
     * Function Name: notifyDieChanged
     * Purpose: Notifies the listener when the die has changed.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. If the onDieChangedListener is not null, calls the onDieChanged method of
     * the listener.
     * Reference: None.
     *********************************************************************
     */
    private void notifyDieChanged() {
        if (onDieChangedListener != null) {
            onDieChangedListener.onDieChanged(die);
        }
    }

    /**
     * *********************************************************************
     * Interface Name: OnDieChangeListener
     * Purpose: Defines a listener interface to notify when a die changes.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. The interface defines a single method, onDieChanged, that is called
     * whenever a die changes.
     * 2. The onDieChanged method takes a Die object as a parameter, representing
     * the updated die.
     * Reference: None.
     *********************************************************************
     */
    public interface OnDieChangeListener {
        void onDieChanged(Die die);
    }

    /**
     * *********************************************************************
     * Function Name: onTouchEvent
     * Purpose: Handles touch events and passes them to the gesture detector.
     * Parameters: MotionEvent event - the touch event to handle.
     * Return Value: boolean - true if the event was handled, false otherwise.
     * Algorithm:
     * 1. Pass the event to the gesture detector to handle scrolling and clicks.
     * 2. If the gesture detector does not handle the event, calls the superclass
     * method.
     * Reference: None.
     *********************************************************************
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = gestureDetector.onTouchEvent(event);
        return handled || super.onTouchEvent(event);
    }

    /**
     * *********************************************************************
     * Function Name: incrementDie
     * Purpose: Increments the die value and updates the die image.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Calls the increment method on the die to increase its value.
     * 2. Updates the die image by calling updateDieImage().
     * 3. Notifies the listener about the die change by calling notifyDieChanged().
     * Reference: None.
     *********************************************************************
     */
    private void incrementDie() {
        die = die.increment();
        updateDieImage();
        notifyDieChanged();
    }

    /**
     * *********************************************************************
     * Function Name: decrementDie
     * Purpose: Decrements the die value and updates the die image.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Calls the decrement method on the die to decrease its value.
     * 2. Updates the die image by calling updateDieImage().
     * 3. Notifies the listener about the die change by calling notifyDieChanged().
     * Reference: None.
     *********************************************************************
     */
    private void decrementDie() {
        die = die.decrement();
        updateDieImage();
        notifyDieChanged();
    }

    /**
     * *********************************************************************
     * Function Name: rollDie
     * Purpose: Rolls the die and updates the die image.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Calls the roll method on the die to roll it.
     * 2. Updates the die image by calling updateDieImage().
     * 3. Notifies the listener about the die change by calling notifyDieChanged().
     * Reference: None.
     *********************************************************************
     */
    private void rollDie() {
        die = die.roll();
        updateDieImage();
        notifyDieChanged();
    }

    /**
     * *********************************************************************
     * Function Name: toggleMarkForLock
     * Purpose: Toggles the "marked for lock" state of the die and updates the
     * image.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Calls the toggleMarkForLock method on the die to toggle its state.
     * 2. Updates the die image by calling updateDieImage().
     * 3. Notifies the listener about the die change by calling notifyDieChanged().
     * Reference: None.
     *********************************************************************
     */

    private void toggleMarkForLock() {
        die = die.toggleMarkForLock();
        updateDieImage();
        notifyDieChanged();
    }

    /**
     * *********************************************************************
     * Function Name: updateDieImage
     * Purpose: Updates the image of the die based on its current value.
     * Parameters: None
     * Return Value: None
     * Algorithm:
     * 1. Retrieves the drawable resource corresponding to the current die value.
     * 2. Sets the image resource to the appropriate drawable for the die value.
     * 3. Invalidates the view to trigger a redraw for any overlays or text.
     * Reference: None.
     *********************************************************************
     */
    private void updateDieImage() {
        // Update die value as picture
        int drawableId = getDrawableForValue(die.getValue());
        setImageResource(drawableId);

        // Trigger a redraw to display the indicators
        invalidate();
    }

    /**
     * *********************************************************************
     * Function Name: getDrawableForValue
     * Purpose: Retrieves the drawable resource for a given die value.
     * Parameters: int value - the value of the die.
     * Return Value: int - the resource ID of the drawable corresponding to the die
     * value.
     * Algorithm:
     * 1. Switches based on the value and returns the corresponding drawable
     * resource ID.
     * 2. Throws an IllegalArgumentException if the die value is invalid.
     * Reference: None.
     *********************************************************************
     */
    private int getDrawableForValue(int value) {
        switch (value) {
            case 1:
                return R.drawable.die_1;
            case 2:
                return R.drawable.die_2;
            case 3:
                return R.drawable.die_3;
            case 4:
                return R.drawable.die_4;
            case 5:
                return R.drawable.die_5;
            case 6:
                return R.drawable.die_6;
            default:
                throw new IllegalArgumentException("Invalid die value: " + value);
        }
    }

    /**
     * *********************************************************************
     * Function Name: onDraw
     * Purpose: Draws the die, its borders, and overlays on the canvas.
     * Parameters: Canvas canvas - the canvas on which to draw.
     * Return Value: None
     * Algorithm:
     * 1. Calls the superclass onDraw method to draw the image of the die.
     * 2. If the die is marked for lock, draws a green overlay on the die.
     * 3. If the die is marked for lock, draws a dashed green border around the die.
     * 4. If the die is marked for help, draws a solid green border around the die.
     * 5. If the die is locked, sets its alpha value to make it appear faded.
     * Reference: None.
     *********************************************************************
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Paint for different states
        Paint dashedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dashedPaint.setColor(Color.GREEN); // Color for marked for lock (light green)
        dashedPaint.setStyle(Paint.Style.STROKE);
        dashedPaint.setStrokeWidth(5);
        dashedPaint.setPathEffect(new android.graphics.DashPathEffect(new float[] { 10, 5 }, 0));

        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(Color.GREEN); // For markedForHelp (green border)
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(8);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // Draw green overlay if the die is marked for lock
        if (die.isMarkedForLock()) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), overlayPaint);
        }

        textPaint.setTextSize(getWidth() * 0.9f); // Adjust text size for emojis

        if (die.isMarkedForLock()) {
            // Draw a dashed green border to indicate it's marked for lock
            canvas.drawRect(0, 0, getWidth(), getHeight(), dashedPaint);
        }

        // Draw a green border if the die is marked for help
        if (die.isMarkedForHelp()) {
            // Draw a solid green border around the die
            canvas.drawRect(0, 0, getWidth(), getHeight(), borderPaint);
        }

        if (die.isLocked()) {
            setAlpha(0.5f);
        }

    }

    /**
     * *********************************************************************
     * Function Name: setDie
     * Purpose: Sets the die and updates the die image.
     * Parameters: Die die - the new die to set.
     * Return Value: None
     * Algorithm:
     * 1. Sets the provided die as the current die.
     * 2. Updates the die image by calling updateDieImage().
     * Reference: None.
     *********************************************************************
     */

    public void setDie(Die die) {
        this.die = die;
        updateDieImage();
    }

    /**
     * *********************************************************************
     * Function Name: getDie
     * Purpose: Retrieves the current die.
     * Parameters: None
     * Return Value: Die - the current die object.
     * Algorithm:
     * 1. Returns the current die.
     * Reference: None.
     *********************************************************************
     */
    public Die getDie() {
        return die;
    }
}