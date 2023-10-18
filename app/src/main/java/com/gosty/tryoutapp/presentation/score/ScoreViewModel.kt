package com.gosty.tryoutapp.presentation.score

import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.core.domain.repository.NumerationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val numerationRepository: NumerationRepository
) : ViewModel() {
    /***
    *   this method is to get user's data related to his/her scores
     *   @author Andi
     *   @since September 14th, 2023
    * */
    fun getListScore() = numerationRepository.getUserListScore()
}