package com.gosty.tryoutapp.data.repositories

import androidx.lifecycle.LiveData
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.models.SubjectModel
import com.gosty.tryoutapp.utils.Result

interface NumerationRepository {
    fun getAllNumerationTryouts(): LiveData<Result<List<SubjectModel>>>

    fun postUserAnswer(answerModel: AnswerModel)

    fun getAllUserAnswer(): LiveData<Result<List<AnswerModel>>>

    fun deleteAllUserAnswer()
}