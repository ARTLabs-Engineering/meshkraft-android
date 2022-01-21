package com.mertgolcu.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artlabs.meshkraft.IMeshkraftState
import com.artlabs.meshkraft.Meshkraft
import com.artlabs.meshkraft.data.model.Mode

//KOTLIN
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Meshkraft.setApiKey("API_KEY")

        Meshkraft.startArSession(
            context = this,
            sku = "TST00078SKU41",
            mode = Mode.AR_ONLY,
            listener = object : IMeshkraftState {
                override fun onLoading() {
                    //loading state
                }

                override fun onFail(message: String) {
                    // any error or fail
                }

                override fun onFinish() {
                    // on finish all
                }

            }
        )

        Meshkraft.startBasicArSession(this, "TST00078SKU41")


    }

}