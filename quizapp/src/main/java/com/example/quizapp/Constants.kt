package com.example.quizapp

import java.util.*
import kotlin.collections.ArrayList

// SingleTon 패턴
object Constants {
    // ExtraData Key
    const val USER_NAME : String = "user_name"
    const val TOTAL_QUESTIONS : String = "total_questions"
    const val CORRECT_ANSWERS : String = "correct_answers"

    fun getQuestions(): ArrayList<Question> {

        val questionsList = ArrayList<Question>()

        val que1 = Question(
            1, "What country does this flag belong to?",
            R.drawable.ic_flag_of_argentina,
            "Argentina", "Australia",
            "Armenia", "Austria", 1
        )
        questionsList.add(que1)

        val que2 = Question(
            2, "What country does this flag belong to?",
            R.drawable.ic_flag_of_australia,
            "Argentina", "Australia",
            "Armenia", "Austria", 2
        )
        questionsList.add(que2)

        val que3= Question(
            3, "What country does this flag belong to?",
            R.drawable.ic_flag_of_brazil,
            "Belarus", "Belize",
            "Brunei", "Brazil", 4
        )
        questionsList.add(que3)

        val que4 = Question(
            4, "What country does this flag belong to?",
            R.drawable.ic_flag_of_belgium,
            "Bahamas", "Belgium",
            "Barbados", "Belize", 2
        )
        questionsList.add(que4)

        val que5 = Question(
            5, "What country does this flag belong to?",
            R.drawable.ic_flag_of_fiji,
            "Gabon", "France",
            "Fiji", "Finland", 3
        )
        questionsList.add(que5)

        val que6 = Question(
            6, "What country does this flag belong to?",
            R.drawable.ic_flag_of_germany,
            "Germany", "Georgia",
            "Greece", "none of these", 1
        )
        questionsList.add(que6)

        val que7 = Question(
            7, "What country does this flag belong to?",
            R.drawable.ic_flag_of_denmark,
            "Dominica", "Egypt",
            "Denmark", "Ethiopia", 3
        )
        questionsList.add(que7)

        val que8 = Question(
            8, "What country does this flag belong to?",
            R.drawable.ic_flag_of_india,
            "Ireland", "Iran",
            "Hungary", "India", 4
        )
        questionsList.add(que8)

        val que9 = Question(
            9, "What country does this flag belong to?",
            R.drawable.ic_flag_of_new_zealand,
            "New Zealand", "Jordan",
            "Sudan", "Palestine", 1
        )
        questionsList.add(que9)

        val que10 = Question(
            10, "What country does this flag belong to?",
            R.drawable.ic_flag_of_bangladesh,
            "Bangladesh", "Belgium",
            "Germany", "India", 1
        )
        questionsList.add(que10)

        val que11 = Question(
            11, "What country does this flag belong to?",
            R.drawable.ic_flag_of_russia,
            "Romania", "Russia",
            "Philippines", "France", 2
        )
        questionsList.add(que11)

        val que12 = Question(
            12, "What country does this flag belong to?",
            R.drawable.ic_flag_of_peru,
            "Austria", "Canada",
            "Peru", "Denmark", 3
        )
        questionsList.add(que12)

        val que13 = Question(
            13, "What country does this flag belong to?",
            R.drawable.ic_flag_of_finland,
            "Greece", "England",
            "Iceland", "Finland", 4
        )
        questionsList.add(que13)

        val que14 = Question(
            14, "What country does this flag belong to?",
            R.drawable.ic_flag_of_greece,
            "Finland", "Greece",
            "Israel", "Scotland", 2
        )
        questionsList.add(que14)

        val que15 = Question(
            15, "What country does this flag belong to?",
            R.drawable.ic_flag_of_iraq,
            "Sudan", "Tajikistan",
            "Iraq", "Syria", 3
        )
        questionsList.add(que15)

        val que16 = Question(
            16, "What country does this flag belong to?",
            R.drawable.ic_flag_of_philippines,
            "Philippines", "Russia",
            "Poland", "Cuba", 1
        )
        questionsList.add(que16)

        val que17 = Question(
            17, "What country does this flag belong to?",
            R.drawable.ic_flag_of_israel,
            "Salvador", "Argentina",
            "Greece", "Israel", 4
        )

        val que18 = Question(
            18, "What country does this flag belong to?",
            R.drawable.ic_flag_of_iceland,
            "Finland", "Norway",
            "Iceland", "England", 3
        )
        questionsList.add(que18)


        /*
        TODO
         문제 추가해서 랜덤하게 출제되도록 변경하기
         XML, JSON 파일에서 문제 읽어와 각 문제를 Question 객체로 변환한 뒤 리스트에 담기
         JSON 파일에서 image 값으로 저장소에서의 이미지 절대경로를 지정
         아이들 교육용으로 보호자가 직접 퀴즈를 커스터마이징 해서 아이들이 풀 수 있도록? 사용자간에 공유도 가능하게?
         */

        // 문제 섞기
        questionsList.shuffle()

        // 10개만 추출
        return ArrayList(questionsList.take(10))
    }
}