package com.gosty.tryoutapp.utils

import com.gosty.tryoutapp.core.domain.models.AnswerModel
import com.gosty.tryoutapp.core.domain.models.ScoreModel

object DataDummy {
    fun dummyAnswers(): List<AnswerModel> = listOf(
        AnswerModel(
            1,
            123,
            123,
            false,
            listOf("a", "b", "c", "d"),
            true
        ),
    )

    fun dummyScores(): List<ScoreModel> = listOf(
        ScoreModel(
            "score-123",
            5,
            5,
            0,
            123123123,
            123123123,
            50,
            "numeration",
            dummyAnswers(),
        )
    )
}