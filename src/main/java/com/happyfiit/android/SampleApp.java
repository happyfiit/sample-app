package com.happyfiit.android;

import android.app.Application;


import com.happyfiit.android.sdk.ApplicationConfig;
import com.happyfiit.android.sdk.FirstRunHandler;
import com.happyfiit.android.sdk.Happyfiit;
import com.happyfiit.android.sdk.Opportunity;
import com.happyfiit.android.sdk.Workout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Map<String, String> initParams = new HashMap<>();
        initParams.put(ApplicationConfig.DEVELOPER_KEY, "test_user_key");
        initParams.put(ApplicationConfig.DEVELOPER_SECRET, "jDQpJea7klf7fT6y7jk821pHu2m2461U");
        initParams.put(ApplicationConfig.USER_EMAIL, "odybour@gmail.com");

        //Happyfiit.initialize(getApplicationContext(), initParams, firstRunHandler());
        Happyfiit.initialize(getApplicationContext(), initParams);
    }


    private FirstRunHandler firstRunHandler() {
        return new FirstRunHandler() {
            @Override
            public Opportunity getWorkoutHistory() {

                //create user's workout history

                Workout workout1 = new Workout.Builder()
                        .type("running")
                        .addWorkoutStatistic("distance", "meter", "100")
                        .addWorkoutStatistic("duration", "sec", "10")
                        .addWorkoutStatistic("avg. speed", "km/h", new Double(100 / 10).toString())
                        .build();

                Workout workout2 = new Workout.Builder()
                        .type("running")
                        .addWorkoutStatistic("distance", "meter", "100")
                        .addWorkoutStatistic("duration", "sec", "9")
                        .addWorkoutStatistic("avg. speed", "km/h", new Double(100 / 9).toString())
                        .build();

                List<Workout> workouts = new ArrayList<>(Arrays.asList(workout1, workout2));

                Opportunity op = new Opportunity.Builder()
                        .history(true)
                        .addWorkoutList(workouts)
                        .addParameter("pb", 10)
                        .build();

                return op;
            }
        };
    }
}