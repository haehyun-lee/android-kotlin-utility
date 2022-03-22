package com.example.quizapp

// SingleTon 패턴
object Constants {
    // ExtraData Key
    const val USER_NAME : String = "user_name"
    const val TOTAL_QUESTIONS : String = "total_questions"
    const val CORRECT_ANSWERS : String = "correct_answers"

    fun getQuestions(): ArrayList<Question> {

        val questionsList = ArrayList<Question>()
        
        // 1
        val que1 = Question(
            1, "What country does this flag belong to?",
            R.drawable.ic_flag_of_argentina,
            "Argentina", "Australia",
            "Armenia", "Austria", 1
        )
        questionsList.add(que1)

        // 2
        val que2 = Question(
            2, "What country does this flag belong to?",
            R.drawable.ic_flag_of_australia,
            "Argentina", "Australia",
            "Armenia", "Austria", 2
        )
        questionsList.add(que2)

        // 3
        val que3= Question(
            3, "What country does this flag belong to?",
            R.drawable.ic_flag_of_brazil,
            "Belarus", "Belize",
            "Brunei", "Brazil", 4
        )
        questionsList.add(que3)

        // 4
        val que4 = Question(
            4, "What country does this flag belong to?",
            R.drawable.ic_flag_of_belgium,
            "Bahamas", "Belgium",
            "Barbados", "Belize", 2
        )
        questionsList.add(que4)

        // 5
        val que5 = Question(
            5, "What country does this flag belong to?",
            R.drawable.ic_flag_of_fiji,
            "Gabon", "France",
            "Fiji", "Finland", 3
        )
        questionsList.add(que5)

        // 6
        val que6 = Question(
            6, "What country does this flag belong to?",
            R.drawable.ic_flag_of_germany,
            "Germany", "Georgia",
            "Greece", "none of these", 1
        )
        questionsList.add(que6)

        // 7
        val que7 = Question(
            7, "What country does this flag belong to?",
            R.drawable.ic_flag_of_denmark,
            "Dominica", "Egypt",
            "Denmark", "Ethiopia", 3
        )
        questionsList.add(que7)

        // 8
        val que8 = Question(
            8, "What country does this flag belong to?",
            R.drawable.ic_flag_of_india,
            "Ireland", "Iran",
            "Hungary", "India", 4
        )
        questionsList.add(que8)

        // 9
        val que9 = Question(
            9, "What country does this flag belong to?",
            R.drawable.ic_flag_of_new_zealand,
            "New Zealand", "Jordan",
            "Sudan", "Palestine", 1
        )
        questionsList.add(que9)

        /*
         문제 추가해서 랜덤하게 출제되도록 변경하기
         XML, JSON 파일에서 문제 읽어와 각 문제를 Question 객체로 변환한 뒤 리스트에 담기
         아이들 교육용으로 보호자가 직접 퀴즈를 커스터마이징 해서 아이들이 풀 수 있도록? 사용자간에 공유도 가능하게?
         ex : 새학기 반 친구들 얼굴-이름 외우기 퀴즈 등. 개인적인 퀴즈 문제들
         */


        return questionsList
    }
}