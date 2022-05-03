package com.example.permissionsdemo

import android.Manifest
import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    // 단일 퍼미션 요청, 응답
    private val cameraResultLauncher : ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Permission granted for camera.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission denied for camera.", Toast.LENGTH_LONG).show()
            }
        }

    // 다중 퍼미션 요청, 응답
    private val cameraAndLocationResultLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            // Entry<퍼미션, 허용 여부>
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                if (isGranted) {
                    when (permissionName) {
                        Manifest.permission.ACCESS_FINE_LOCATION -> {
                            Toast.makeText(this, "Permission granted for fine location.", Toast.LENGTH_LONG).show()
                        }
                        Manifest.permission.ACCESS_COARSE_LOCATION -> {
                            Toast.makeText(this, "Permission granted for coarse location.", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this, "Permission granted for camera.", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    when (permissionName) {
                        Manifest.permission.ACCESS_FINE_LOCATION -> {
                            Toast.makeText(this, "Permission denied for fine location.", Toast.LENGTH_LONG).show()
                        }
                        Manifest.permission.ACCESS_COARSE_LOCATION -> {
                            Toast.makeText(this, "Permission denied for coarse location.", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(this, "Permission denied for camera.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCameraPermission: Button = findViewById(R.id.btnCameraPermission)
        btnCameraPermission.setOnClickListener {
            // 카메라 퍼미션 확인
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                // API 23 이상이고, 이전에 퍼미션을 거부했을 경우
                showRationaleDialog("Permission Demo requires camera access", "Camera cannot be used because Camera access is denied")

            } else {
                // API 23 미만이거나 최초로 퍼미션 요청을 받았을 경우
                cameraAndLocationResultLauncher.launch(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    // 특정 퍼미션이 필요한 이유를 설명하는 다이얼로그, 사용자가 이전에 퍼미션 요청을 거부했을 경우에 표시
    private fun showRationaleDialog(title: String, message:String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        // 타이틀, 메시지, 버튼 1개 설정
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel"){ dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }
}