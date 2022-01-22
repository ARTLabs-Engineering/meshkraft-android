package com.artlabs.testapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.artlabs.meshkraft.IMeshkraftState;
import com.artlabs.meshkraft.Meshkraft;

/**
 * @author Mert Gölcü
 * @date 22.01.2022
 */
// JAVA
public class HomeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Meshkraft.INSTANCE.setApiKey("API_KEY");

        Meshkraft.INSTANCE.startArSession(
                this,
                "SKUTEST000041",
                null,
                new IMeshkraftState() {
                    @Override
                    public void onLoading() {
                        // Loading State
                    }

                    @Override
                    public void onFail(@NonNull String message) {
                        // onFail state
                    }

                    @Override
                    public void onFinish() {
                        // finish session
                    }
                }
        );

    }

}
