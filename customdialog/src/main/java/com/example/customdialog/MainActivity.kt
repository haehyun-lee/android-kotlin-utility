package com.example.customdialog

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAlertDialog: Button = findViewById(R.id.btn_alert_dialog)
        btnAlertDialog.setOnClickListener { view ->
            alertDialogFunction()
        }

        val btnCustomDialog: Button = findViewById(R.id.btn_custom_dialog)
        btnCustomDialog.setOnClickListener { view ->
            customDialogFunction()
        }

        val btnCustomProgressDialog: Button = findViewById(R.id.btn_custom_progress_dialog)
        btnCustomProgressDialog.setOnClickListener { view ->
            customProgressDialogFunction()
        }
    }

    private fun alertDialogFunction(){
        // Builder 패턴 (AlertDialog 내 Builder 클래스 타입)
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
        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked cancel\n operation cancel", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }
        // Negative Button
        builder.setNegativeButton("Negative") { dialogInterface, width ->
            Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }
        
        // AlterDialog 생성
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)    // AlertDialog 영역 밖 클릭으로 Cancel 시킬 수 없음
        // AlertDialog 표시
        alertDialog.show()
    }

    private fun customDialogFunction() {
        val customDialog = Dialog(this)
        // setContentView(view: View)
        customDialog.setContentView(R.layout.dialog_custom)

    }

    private fun customProgressDialogFunction(){

    }

}