package com.thinklearn.tide.grida

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.Toast
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAndAskPermission()

        findViewById<Button>(R.id.RemoveSelection).setOnClickListener {
            askRemoveClassAndModeSelection()
        }
        findViewById<Button>(R.id.UpdateApp).setOnClickListener {
            Toast.makeText(this, "Feature under construction", Toast.LENGTH_LONG).show()
        }

        /* ** Test code ** *
        val authIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            `package` = "com.thinklearn.tide.grida"
            putExtra(Intent.EXTRA_TEXT, "rfgt$%UJ")
            type = "text/plain"
        }
        if (packageManager.queryIntentActivities(authIntent, PackageManager.MATCH_DEFAULT_ONLY).size > 0) {
            startActivityForResult(authIntent, 5)
        } else {
            Toast.makeText(this, "Token provider missing", Toast.LENGTH_LONG).show()
        }
        */
    }
    fun checkAndAskPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    4)
        }
    }
    fun askRemoveClassAndModeSelection() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Removing Class-selection. Tab needs to be connected to Internet to select again. Continue?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            removeClassAndModeSelection()
        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
        })
        builder.create().show()
    }
    fun removeClassAndModeSelection() {
        val base_dir = Environment.getExternalStorageDirectory().getPath() + "/LearningGrid/"
        val baseDirFile = File(base_dir)
        var deleted = true
        try {
            if (baseDirFile.exists()) {
                if (!File(base_dir + "selected_class.json").delete()) {
                    Toast.makeText(this, "Did not delete selected class", Toast.LENGTH_LONG).show()
                    deleted = false
                }
                if (!File(base_dir + "selected_mode.json").delete()) {
                    Toast.makeText(this, "Did not delete selected mode", Toast.LENGTH_LONG).show()
                    deleted = false
                }
            } else {
                Toast.makeText(this, "Nothing to remove", Toast.LENGTH_LONG).show()
                deleted = false
            }
            if (deleted) {
                Toast.makeText(this, "Removed.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Error accessing file: " + e.message, Toast.LENGTH_LONG).show()
        }
    }
}
