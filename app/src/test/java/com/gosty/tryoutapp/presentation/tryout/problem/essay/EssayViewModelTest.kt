package com.gosty.tryoutapp.presentation.tryout.problem.essay

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gosty.tryoutapp.core.domain.models.AnswerModel
import com.gosty.tryoutapp.core.domain.usecase.NumerationUseCase
import com.gosty.tryoutapp.core.utils.Result
import com.gosty.tryoutapp.presentation.tryout.problem.multiplechoice.MultipleChoiceViewModel
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
class EssayViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var numerationUseCase: NumerationUseCase
    private lateinit var essayViewModel: EssayViewModel

    @Before
    fun setUp() {
        essayViewModel = EssayViewModel(numerationUseCase)
    }

    @Test
    fun `When postAnswer result Should Return Success`() {
        val observer = Observer<Result<String>> {}

        val answerModel = AnswerModel(
            id = 1,
            tryoutId = 123,
            questionId = 234,
            essay = true,
            answer = listOf("answer test1"),
            correct = true
        )

        try {
            val expectedResult = MutableLiveData<Result<String>>()
            expectedResult.value = Result.Success("Success")
            Mockito.`when`(numerationUseCase.postUserAnswer(answerModel)).thenReturn(expectedResult)

            essayViewModel.postAnswer(answerModel)

            val actualResult = essayViewModel.result.getOrAwaitValue()
            Mockito.verify(numerationUseCase).postUserAnswer(answerModel)
            assertNotNull(actualResult)
            assertTrue(actualResult is Result.Success)
            assertEquals((expectedResult.value as Result.Success).data, (actualResult as Result.Success).data)
        } finally {
            essayViewModel.result.removeObserver(observer)
        }
    }
}