package com.thinklearn.tide.grida

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException

class TokenPro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_token_pro)
        var caller_ident = ""
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            caller_ident = intent.getStringExtra(Intent.EXTRA_TEXT)
        }
        var credentialFilename = ""
        var UID = ""
        if("rfgt\$%UJ" == caller_ident) {
            credentialFilename = "icgrid-6540f-859a57f482d3.json"
            UID = "ICGridActiv"
        } else if("slwk-ga" == caller_ident) {
            credentialFilename = "gagrid-e25d0-4c1353152fa6.json"
            UID = "GAGridActiv"
        } else {
            Toast.makeText(this, "Caller project unknown!", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        if(intent.action == Intent.ACTION_SEND && "text/plain" == intent.type) {
            // Initialize FirebaseApp only when not already initialized.
            try {
                FirebaseApp.getInstance()
                Toast.makeText(this, "Got instance", Toast.LENGTH_SHORT).show()
            } catch (ex: IllegalStateException) {
                try {
                    val inputStream = assets.open(credentialFilename)
                    Toast.makeText(this, "building...", Toast.LENGTH_SHORT).show()
                    val options = FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(inputStream)).
                            build()
                    FirebaseApp.initializeApp(options)
                    Toast.makeText(this, "built!", Toast.LENGTH_SHORT).show()
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            Toast.makeText(this, "creating custom token...", Toast.LENGTH_SHORT).show()
            val customToken: String = FirebaseAuth.getInstance().createCustomTokenAsync(UID).get()
            Toast.makeText(this, "created!", Toast.LENGTH_SHORT).show()

            val customTokenResult = Intent()
            customTokenResult.putExtra("CUSTOM_TOKEN", customToken)
            setResult(4, customTokenResult)
            finish()
        }
    }
}
