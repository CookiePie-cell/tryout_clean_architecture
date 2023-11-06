package com.gosty.tryoutapp.presentation.score

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gosty.tryoutapp.core.domain.models.ScoreModel
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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ScoreViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var numerationUseCase: NumerationUseCase
    private lateinit var scoreViewModel: ScoreViewModel
    private val dataDummy = DataDummy.dummyScores()

    @Before
    fun setUp() {
        scoreViewModel = ScoreViewModel(numerationUseCase)
    }

    @Test
    fun `when Get Scores Should Not Null and Return Success`() {
        val observer = Observer<Result<List<ScoreModel>>> {}
        try {
            val expectedScore = MutableLiveData<Result<List<ScoreModel>>>()
            expectedScore.value = Result.Success(dataDummy)
            `when`(numerationUseCase.getUserListScore()).thenReturn(expectedScore)

            val actualScore = scoreViewModel.getListScore().getOrAwaitValue()

            Mockito.verify(numerationUseCase).getUserListScore()
            assertNotNull(actualScore)
            assertTrue(actualScore is Result.Success)
            assertEquals(dataDummy.size, (actualScore as Result.Success).data.size)
        } finally {
            scoreViewModel.getListScore().removeObserver(observer)
        }
    }


}