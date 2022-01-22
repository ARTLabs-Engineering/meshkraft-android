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

        Meshkraft.setApiKey("phTzrnocCq2bFfWAbtz8CFxuCAXvfVyaK3VrCeJpUD")

        button.setOnClickListener {
            Meshkraft.startArSession(
                context = this,
                sku = "HBV00000MML21",
                mode = Mode.PREFERRED_3D,
                listener = object : IMeshkraftState {
                    override fun onLoading() {
                        textView.text = "Yükleniyor..."
                    }

                    override fun onFail(message: String) {
                        textView.text = "Hata oluştu : $message"
                    }

                    override fun onFinish() {
                        textView.text = "Tamamlandı"
                    }

                }
            )
        }


        // Meshkraft.startBasicArSession(this, "TST00078SKU41")


    }

}