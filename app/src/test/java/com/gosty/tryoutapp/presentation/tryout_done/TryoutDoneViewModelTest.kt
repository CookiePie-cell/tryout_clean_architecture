package com.gosty.tryoutapp.presentation.tryout_done

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gosty.tryoutapp.core.domain.models.AnswerModel
import com.gosty.tryoutapp.core.domain.models.ScoreModel
import com.gosty.tryoutapp.core.domain.usecase.NumerationUseCase
import com.gosty.tryoutapp.core.utils.Result
import com.gosty.tryoutapp.utils.DataDummy
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
class TryoutDoneViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var numerationUseCase: NumerationUseCase
    private lateinit var tryoutDoneViewModel: TryoutDoneViewModel
    private val dummyData = DataDummy.dummyAnswers()

    @Before
    fun setUp() {
        tryoutDoneViewModel = TryoutDoneViewModel(numerationUseCase)
    }

    @Test
    fun `When postScore Should Return Result Success`() {
        val observer = Observer<Result<String>> {}
        val scoreModel = ScoreModel(
            "score-123",
            5,
            5,
            0,
            123,
            123,
            50,
            "Tes",
            null
        )
        try {
            val expectedResult = MutableLiveData<Result<String>>()
            expectedResult.value = Result.Success("Success")
            Mockito.`when`(numerationUseCase.postUserScore(scoreModel)).thenReturn(expectedResult)

            tryoutDoneViewModel.postScore(scoreModel)

            val actualResult = tryoutDoneViewModel.sending.getOrAwaitValue()

            Mockito.verify(numerationUseCase).postUserScore(scoreModel)
            assertNotNull(actualResult)
            assertTrue(actualResult is Result.Success)
            assertEquals((expectedResult.value as Result.Success).data, (actualResult as Result.Success).data)
        } finally {
            tryoutDoneViewModel.sending.removeObserver(observer)
        }
    }

    @Test
    fun `When getAllUserAnswer Should Not Return Null and Return Success`() {
        val observer = Observer<Result<List<AnswerModel>>> {}
        try {
            val expectedUserAnswers = MutableLiveData<Result<List<AnswerModel>>>()
            expectedUserAnswers.value = Result.Success(dummyData)
            Mockito.`when`(numerationUseCase.getAllUserAnswer()).thenReturn(expectedUserAnswers)

            tryoutDoneViewModel.getAllUserAnswer()

            val actualUserAnswer = tryoutDoneViewModel.answers.getOrAwaitValue()
            Mockito.verify(numerationUseCase).getAllUserAnswer()
            assertNotNull(actualUserAnswer)
            assertTrue(actualUserAnswer is Result.Success)
            assertEquals(dummyData.size, (actualUserAnswer as Result.Success).data.size)

        } finally {
            tryoutDoneViewModel.answers.removeObserver(observer)
        }
    }
}