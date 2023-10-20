package com.gosty.tryoutapp.presentation.explanation

import androidx.lifecycle.ViewModel
import com.gosty.tryoutapp.core.domain.repository.NumerationRepository
import com.gosty.tryoutapp.core.domain.usecase.NumerationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExplanationViewModel @Inject constructor(
    private val numerationUseCase: NumerationUseCase
) : ViewModel() {

    /***
    *   this method is to get numeration tryout data for explanation feature
    *   @author Andi
    *   @since September 11th, 2023
    * */
    fun getSubjectForExplanation() = numerationUseCase.getAllNumerationTryoutsForExplanation()
}