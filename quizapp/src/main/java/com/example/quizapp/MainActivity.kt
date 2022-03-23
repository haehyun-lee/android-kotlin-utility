package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // JSON 테스트
        /*
        // 1. JSON 파일 열어서 String 취득
        val jsonString = assets.open("file.json").reader().readText()
        Log.d("jsonString", jsonString)

        // 2. JSONArray 로 파싱
        val jsonArray = JSONArray(jsonString)
        Log.d("jsonArray", jsonArray.toString())

        // 3. JSONArray 순회 : 인덱스별 JSONObject 취득 후 key에 해당하는 value 확인
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            Log.d("jsonObject", jsonObject.toString())

            val id = jsonObject.get("id")
            val image = jsonObject.get("image")

            Log.d("jsonData", "$id $image")
        }
        */

        val etName: AppCompatEditText = findViewById(R.id.etName)
        val btnStart: Button = findViewById<Button>(R.id.btnStart)
        btnStart.setOnClickListener {
            if (!etName.text.isNullOrEmpty()) {
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, etName.text.toString())
                startActivity(intent)
                // 액티비티 종료, 뒤로가기 했을 때 이전 액티비티가 나오는 것을 방지
                finish()
            } else {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}