package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission: Button = findViewById(R.id.btnCameraPermission)
        btnCameraPermission.setOnClickListener {
            val status = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            if (status == PackageManager.PERMISSION_GRANTED) {
                // 퍼미션이 허용되어 있는 경우
                Toast.makeText(this, "Permission granted for camera.", Toast.LENGTH_LONG).show()
            } else {
                // 퍼미션이 거부되어 있는 경우 (default)
                Toast.makeText(this, "Permission denied for camera.", Toast.LENGTH_LONG).show()
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(Manifest.permission.CAMERA),
                    100
                )
            }
        }
    }

    // 퍼미션 허용 요청에 대한 사용자 응답
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 사용자가 퍼미션을 허용함
            Toast.makeText(this, "Permission granted for camera.", Toast.LENGTH_LONG).show()
        } else {
            // 사용자가 퍼미션을 거부함
            Toast.makeText(this, "Permission denied for camera.", Toast.LENGTH_LONG).show()
        }
    }
}