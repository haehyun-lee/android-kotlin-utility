package com.example.customdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun alertDialogFunction(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("This is Alert Dialog. Which is used to show alert")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        // Positive Button
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }
        // Cancel Button
        builder.setNeutralButton("No") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked cancel\n operation cancel", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }
    }
}