package com.example.kidsdrawingapp

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {

    private var drawingView: DrawingView? = null
    private var mImageButtonCurrentPaint: ImageButton? = null

    // 갤러리 선택 결과 받기
    val openGallery:ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                // 갤러리에서 선택한 이미지를 배경으로 설정, URI는 이미지 파일의 경로
                val imageBackground = findViewById<ImageView>(R.id.iv_background)
                imageBackground.setImageURI(result.data?.data)
            }
            
        }

    // 퍼미션 요청 결과 받기
    val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value
                
                if (isGranted) {
                    // 퍼미션 허용시
                    Toast.makeText(
                        this@MainActivity,
                        "Permission granted now you can read the storage files.",
                        Toast.LENGTH_LONG
                    ).show()

                    // 갤러리 실행
                    val pickIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                    openGallery.launch(pickIntent)
                    
                } else {
                    // 퍼미션 거부시
                    if (permissionName == Manifest.permission.READ_EXTERNAL_STORAGE) {
                        Toast.makeText(
                            this@MainActivity,
                            "Oops you just denied the permission.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 그림판
        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(20.toFloat())

        // 브러시 색상 변경
        var linearLayoutPaintColors = findViewById<LinearLayout>(R.id.ll_paint_colors)
        // LinearLayout 내 요소를 인덱스로 접근
        mImageButtonCurrentPaint = linearLayoutPaintColors[1] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
        )

        // 브러시 크기 변경
        val ibBrush = findViewById<ImageButton>(R.id.ib_brush)
        ibBrush.setOnClickListener {
            showBushSizeChooserDialog()
        }

        // Undo
        val ibUndo = findViewById<ImageButton>(R.id.ib_undo)
        ibUndo.setOnClickListener {
            drawingView?.onClickUndo()
        }

        // Redo
        val ibRedo = findViewById<ImageButton>(R.id.ib_redo)
        ibRedo.setOnClickListener {
            drawingView?.onClickRedo()
        }

        // 갤러리
        val ibGallery = findViewById<ImageButton>(R.id.ib_gallery)
        ibGallery.setOnClickListener {
            requestStoragePermission()
        }

    }

    // 브러시 크기 선택 창
    private fun showBushSizeChooserDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush size: ")
        // MainActivity에서 바로 findViewById<>()로 뷰 객체를 찾을 수 있는 건, onCreate()에서 setContentView(R.layout.activity_main.xml) 했기 때문
        val smallBtn = brushDialog.findViewById<ImageButton>(R.id.ib_small_brush)
        smallBtn.setOnClickListener {
            drawingView?.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }
        val mediumBtn = brushDialog.findViewById<ImageButton>(R.id.ib_medium_brush)
        mediumBtn.setOnClickListener {
            drawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }
        val largeBtn = brushDialog.findViewById<ImageButton>(R.id.ib_large_brush)
        largeBtn.setOnClickListener {
            drawingView?.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }

        brushDialog.show()
    }

    // 브러시 색상 선택
    fun paintClicked(view: View) {
        // 현재 색과 다른 색을 선택할 경우
        if (view !== mImageButtonCurrentPaint) {
            val imageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            drawingView?.setColor(colorTag)

            // 선택
            imageButton!!.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
            )
            // 선택 해제
            mImageButtonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_normal)
            )

            mImageButtonCurrentPaint = view
        }
    }

    // 퍼미션 확인
    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            // 사용자가 이전에 퍼미션 요청을 거부한 경우
            showRationaleDialog(
                "Kids Drawing App",
                "Kids Drawing App " + "needs to Access Your External Storage"
            )
        } else {
            // 사용자가 이전에 퍼미션 요청을 받은 적이 없는 경우
            requestPermission.launch(arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
                // TODO - Add writing external storage permission
            ))

        }
    }

    // 지정한 타이틀, 메시지의 AlertDialog 생성 후 표시
    private fun showRationaleDialog(
        title: String,
        message: String
    ) {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ -> 
                dialog.dismiss()
            }
        builder.create().show()
    }
}