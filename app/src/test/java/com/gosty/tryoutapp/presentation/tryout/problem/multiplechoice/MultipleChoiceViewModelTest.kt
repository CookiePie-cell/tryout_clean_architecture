package com.gosty.tryoutapp.presentation.tryout.problem.multiplechoice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gosty.tryoutapp.core.domain.models.AnswerModel
import com.gosty.tryoutapp.core.domain.usecase.NumerationUseCase
import com.gosty.tryoutapp.core.utils.Result
import com.gosty.tryoutapp.presentation.tryout.problem.ProblemViewModel
import com.gosty.tryoutapp.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MultipleChoiceViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var numerationUseCase: NumerationUseCase
    private lateinit var multipleChoiceViewModel: MultipleChoiceViewModel

    @Before
    fun setUp() {
        multipleChoiceViewModel = MultipleChoiceViewModel(numerationUseCase)
    }

    @Test
    fun `When postAnswer result Should Return Success`() {
        val observer = Observer<Result<String>> {}

        val answerModel = AnswerModel(
            id = 1,
            tryoutId = 123,
            questionId = 234,
            essay = false,
            answer = listOf("answer test1", "answer test2"),
            correct = true
        )

        try {
            val expectedResult = MutableLiveData<Result<String>>()
            expectedResult.value = Result.Success("Success")
            Mockito.`when`(numerationUseCase.postUserAnswer(answerModel)).thenReturn(expectedResult)

            multipleChoiceViewModel.postAnswer(answerModel)

            val actualResult = multipleChoiceViewModel.result.getOrAwaitValue()
            Mockito.verify(numerationUseCase).postUserAnswer(answerModel)
            assertNotNull(actualResult)
            assertTrue(actualResult is Result.Success)
            assertEquals((expectedResult.value as Result.Success).data, (actualResult as Result.Success).data)
        } finally {
            multipleChoiceViewModel.result.removeObserver(observer)
        }
    }
}