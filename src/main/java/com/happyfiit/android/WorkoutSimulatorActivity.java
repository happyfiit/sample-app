package com.happyfiit.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.happyfiit.android.sdk.Happyfiit;
import com.happyfiit.android.sdk.HappyfiitCallbackHandler;
import com.happyfiit.android.sdk.Opportunity;
import com.happyfiit.android.sdk.Reward;
import com.happyfiit.android.sdk.Workout;


public class WorkoutSimulatorActivity extends Activity implements View.OnClickListener {

    private static final String TAG = WorkoutSimulatorActivity.class.getSimpleName();

    private Happyfiit happyfiit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_simulator);

        // Capture our button from layout
        Button button = (Button) findViewById(R.id.simulatorButtonView);

        // Register the onClick listener with the implementation above
        button.setOnClickListener(this);

        happyfiit = Happyfiit.getInstance();
        happyfiit.start(this);

    }

    // Implement the OnClickListener callback
    @Override
    public void onClick(View view) {
        String seconds = ((EditText) findViewById(R.id.durationValueView)).getText().toString();
        String meters = ((EditText) findViewById(R.id.distanceValueView)).getText().toString();

        Log.d(TAG, "Seconds:" + seconds);
        Log.d(TAG, "Distance:" + meters);

        Opportunity op = new Opportunity.Builder()
                .addWorkout(new Workout.Builder()
                        .type("running")
                        .addWorkoutStatistic("distance", "meter", Double.parseDouble(meters))
                        .addWorkoutStatistic("duration", "sec", Double.parseDouble(seconds))
                        .addWorkoutStatistic("avg. speed", "km/h", new Double(Double.parseDouble(meters) / Double
                                .parseDouble(seconds)))
                        .build())
                .addParameter("pb", 10)
                .build();

        happyfiit.pushOpportunity(op, new HappyfiitCallbackHandler(this) {

            /**
             * Optionally the user can override the onAchievement() method of the HappyfiitCallbackHandler class to implement custom handling logic
             * like showing a native application notification (e.g. popup, present/gift icon etc.) prior to showing the Happyfiit Reward popup.
             * This will allow the application developer to maintain control over the user experience by customizing the look and feel of the notification.
             *
             * Important note: When you override this method you MUST make sure that at some point you call the showReward() method of the Happyfiit API
             * so that the user can redeem the reward.
             * @param reward
             */
            @Override
            public void onAchievement(Reward reward) {
                //1. Create a native to the application notification (optional)

                //2. Show Happyfiit reward popup so that user can redeem it (required)
                happyfiit.showReward(activityContext, reward);
            }

            @Override
            public void onFailure(Exception ex) {
                //handle exception
            }
        });

        Toast.makeText(getApplicationContext(), "Uploading Data..",
                Toast.LENGTH_SHORT).show();
    }
}
