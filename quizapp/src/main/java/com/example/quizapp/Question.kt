package com.example.quizapp

/**
 * 퀴즈
 *
 * @property id 식별자
 * @property question 문제
 * @property image 이미지
 * @property optionOne 보기1
 * @property optionTwo 보기2
 * @property optionThree 보기3
 * @property optionFour 보기4
 * @property correctAnswer 정답 번호
 */
data class Question(
    val id: Int,
    val question: String,
    val image: Int,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: Int
)
