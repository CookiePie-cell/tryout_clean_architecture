package com.gosty.tryoutapp.presentation.tryout.choice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gosty.tryoutapp.core.domain.models.SubjectModel
import com.gosty.tryoutapp.core.domain.usecase.NumerationUseCase
import com.gosty.tryoutapp.core.utils.Result
import com.gosty.tryoutapp.utils.DataDummy
import com.gosty.tryoutapp.utils.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ChoiceViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var numerationUseCase: NumerationUseCase
    private lateinit var choiceViewModel: ChoiceViewModel
    private val dataDummy = DataDummy.generateSubjectModels()

    @Before
    fun setUp() {
        choiceViewModel = ChoiceViewModel(numerationUseCase)
    }

    @Test
    fun `when Get Subject Should Not Null and Return Success`() {
        val observer = Observer<Result<List<SubjectModel>>> {}
        try {
            val expectedSubject = MutableLiveData<Result<List<SubjectModel>>>()
            expectedSubject.value = Result.Success(dataDummy)
            Mockito.`when`(numerationUseCase.getAllNumerationTryouts()).thenReturn(expectedSubject)

            choiceViewModel.getSubjects()

            val actualTryouts = choiceViewModel.tryouts.getOrAwaitValue()
            Mockito.verify(numerationUseCase).getAllNumerationTryouts()
            assertNotNull(actualTryouts)
            assertTrue(actualTryouts is Result.Success)
            assertEquals(dataDummy.size, (actualTryouts as Result.Success).data.size)

        } finally {
            choiceViewModel.tryouts.removeObserver(observer)
        }
    }


}