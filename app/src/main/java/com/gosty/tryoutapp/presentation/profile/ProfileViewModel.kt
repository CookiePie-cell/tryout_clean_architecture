package com.gosty.tryoutapp.presentation.profile

import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.core.domain.repository.NumerationRepository
import com.gosty.tryoutapp.core.domain.usecase.NumerationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val numerationUseCase: NumerationUseCase
) : ViewModel() {

    /***
    *   this method is to get user's score data for profile feature
    *   @author Andi
    *   @since September 11th, 2023
    * */
    fun getScoreData() = numerationUseCase.getUserListScore()
}