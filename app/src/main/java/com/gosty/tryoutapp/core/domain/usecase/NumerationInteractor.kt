package com.gosty.tryoutapp.core.domain.usecase

import androidx.lifecycle.LiveData
import com.gosty.tryoutapp.core.domain.models.AnswerModel
import com.gosty.tryoutapp.core.domain.models.ScoreModel
import com.gosty.tryoutapp.core.domain.models.SubjectModel
import com.gosty.tryoutapp.core.domain.repository.NumerationRepository
import com.gosty.tryoutapp.core.utils.Result

class NumerationInteractor(private val numerationRepository: NumerationRepository):
    NumerationUseCase {
    override fun getAllNumerationTryouts(): LiveData<Result<List<SubjectModel>>> = numerationRepository.getAllNumerationTryouts()

    override fun getAllNumerationTryoutsForExplanation(): LiveData<Result<List<SubjectModel>>> = numerationRepository.getAllNumerationTryoutsForExplanation()

    override fun postUserAnswer(answerModel: AnswerModel): LiveData<Result<String>> = numerationRepository.postUserAnswer(answerModel)

    override fun getAllUserAnswer(): LiveData<Result<List<AnswerModel>>> = numerationRepository.getAllUserAnswer()

    override fun getUserListScore(): LiveData<Result<List<ScoreModel>>> = numerationRepository.getUserListScore()

    override fun deleteAllUserAnswer(): LiveData<Result<String>> = numerationRepository.deleteAllUserAnswer()

    override fun postUserScore(score: ScoreModel): LiveData<Result<String>> = numerationRepository.postUserScore(score)

}