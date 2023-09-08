package com.gosty.tryoutapp.ui.tryout.problem.singlechoice

import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.data.models.AnswerModel
import com.gosty.tryoutapp.data.repositories.NumerationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleChoiceViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {
    fun postAnswer(answerModel: AnswerModel) = numerationRepository.postUserAnswer(answerModel)
}