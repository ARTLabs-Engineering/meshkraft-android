package com.artlabs.testapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.artlabs.meshkraft.IMeshkraftState
import com.artlabs.meshkraft.Meshkraft
import com.artlabs.meshkraft.data.model.Mode

//KOTLIN
class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text_view)
        button = findViewById(R.id.button)

        Meshkraft.setApiKey("TOKEN")

        button.setOnClickListener {

//            Meshkraft.startVTOSession(
//                context = this,
//                sku = "SKU",
//            )

//            Meshkraft.startArSession(
//                context = this,
//                sku = "SKU",
//                mode = Mode.PREFERRED_3D,
//                listener = object : IMeshkraftState {
//                    override fun onLoading() {
//                        textView.text = "Loading..."
//                    }
//
//                    override fun onFail(message: String) {
//                        textView.text = "Error : $message"
//                    }
//
//                    override fun onFinish() {
//                        textView.text = "Done."
//                    }
//
//                }
//            )
        }


        // Meshkraft.startBasicArSession(this, "TST00078SKU41")


    }

}