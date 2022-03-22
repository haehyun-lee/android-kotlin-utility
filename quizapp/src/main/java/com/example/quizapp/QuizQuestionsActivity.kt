package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityQuizQuestionsBinding

    private var mCurrentPosition: Int = 1                       // 현재 순번
    private var mQuestionsList: ArrayList<Question>? = null     // 문제 리스트
    private var mSelectedOptionPosition: Int = 0                // 선택한 보기 옵션

    private var mUserName: String? = null                       // 사용자이름
    private var mCorrectAnswers: Int = 0                                 // 맞힌 개수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 문제 리스트 가져오기
        mQuestionsList = Constants.getQuestions()
        // 문제 출력
        setQuestions()
        // 문제 보기 뷰 초기화
        defaultOptionsView()

        // 클릭 리스터 등록
        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)

        // 사용자 이름
        mUserName = intent.getStringExtra(Constants.USER_NAME).toString()
    }

    private fun setQuestions() {
        // 문제 보기 뷰 초기화
        defaultOptionsView()

        val question: Question = mQuestionsList!![mCurrentPosition- 1]
        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition/${binding.progressBar.max}"
        binding.tvQuestion.text = question.question
        binding.ivImage.setImageResource(question.image)
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour

        // 제출 버튼 텍스트 설정
        if (mCurrentPosition == mQuestionsList!!.size) {
            binding.btnSubmit.text = "FINISH"
        } else {
            binding.btnSubmit.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        binding.tvOptionOne.let {
            options.add(0, it)
        }
        binding.tvOptionTwo.let {
            options.add(1, it)
        }
        binding.tvOptionThree.let {
            options.add(2, it)
        }
        binding.tvOptionFour.let {
            options.add(3, it)
        }

        // 중복 코드 최소화를 위해 리스트에 담아서 반복문으로 실행
        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
            option.isClickable = true
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_border_bg
        )
    }

    // 화면 내 뷰 클릭 리스너
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tvOptionOne -> {
                selectedOptionView(binding.tvOptionOne, 1)
            }

            R.id.tvOptionTwo -> {
                selectedOptionView(binding.tvOptionTwo, 2)
            }

            R.id.tvOptionThree -> {
                selectedOptionView(binding.tvOptionThree, 3)
            }

            R.id.tvOptionFour -> {
                selectedOptionView(binding.tvOptionFour, 4)
            }

            R.id.btnSubmit -> {
                if (mSelectedOptionPosition == 0) {
                    // 답 체크 안하면 패스 or 채점 후 다음 문제로 이동할 때
                    mCurrentPosition++

                    when {
                        // 마지막 문제가 아닐 경우, 증가한 순번 문제를 출력
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestions()
                        }
                        // 마지막 문제일 경우, 결과 출력
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    // 채점
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (mSelectedOptionPosition != question!!.correctAnswer) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)  // 오답 표시
                    }else{
                        mCorrectAnswers++   // 맞힌 개수 증가
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)   // 정답 표시

                    binding.tvOptionOne.isClickable = false
                    binding.tvOptionTwo.isClickable = false
                    binding.tvOptionThree.isClickable = false
                    binding.tvOptionFour.isClickable = false

                    // 다음 문제로
                    if (mCurrentPosition == mQuestionsList!!.size) {
                        binding.btnSubmit.text = "FINISH"
                    } else {
                        binding.btnSubmit.text = "GO TO NEXT QUESTION"
                    }

                    // 다음 문제를 위해 초기화
                    mSelectedOptionPosition = 0
               }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        // 지정한 보기의 배경을 지정한 Drawable 리소스로 변경
        when (answer) {
            1 -> {
                binding.tvOptionOne.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            2 -> {
                binding.tvOptionTwo.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            3 -> {
                binding.tvOptionThree.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            4 -> {
                binding.tvOptionFour.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }
}