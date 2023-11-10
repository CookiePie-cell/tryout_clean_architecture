package com.gosty.tryoutapp.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gosty.tryoutapp.core.domain.models.ScoreModel
import com.gosty.tryoutapp.core.domain.usecase.NumerationUseCase
import com.gosty.tryoutapp.core.utils.Result
import com.gosty.tryoutapp.presentation.score.ScoreViewModel
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
class ProfileViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var numerationUseCase: NumerationUseCase
    private lateinit var profileViewModel: ProfileViewModel
    private val dataDummy = DataDummy.dummyScores()

    @Before
    fun setUp() {
        profileViewModel = ProfileViewModel(numerationUseCase)
    }

    @Test
    fun `when Get Scores Should Not Null and Return Success`() {
        val observer = Observer<Result<List<ScoreModel>>> {}
        try {
            val expectedScore = MutableLiveData<Result<List<ScoreModel>>>()
            expectedScore.value = Result.Success(dataDummy)
            Mockito.`when`(numerationUseCase.getUserListScore()).thenReturn(expectedScore)

            val actualScore = profileViewModel.getScoreData().getOrAwaitValue()

            Mockito.verify(numerationUseCase).getUserListScore()
            assertNotNull(actualScore)
            assertTrue(actualScore is Result.Success)
            assertEquals(dataDummy.size, (actualScore as Result.Success).data.size)
        } finally {
            profileViewModel.getScoreData().removeObserver(observer)
        }
    }

    @Test
    fun `when get Scores Should return the Same Data`() {
        val observer = Observer<Result<List<ScoreModel>>> {}
        try {
            val expectedScore = MutableLiveData<Result<List<ScoreModel>>>()
            expectedScore.value = Result.Success(dataDummy)
            Mockito.`when`(numerationUseCase.getUserListScore()).thenReturn(expectedScore)

            val actualScore = profileViewModel.getScoreData().getOrAwaitValue()

            Mockito.verify(numerationUseCase).getUserListScore()

            val data = (actualScore as Result.Success).data

            assertNotNull(actualScore)

            assertEquals(data[0].scoreId, dataDummy[0].scoreId)
            assertEquals(data[0].score, dataDummy[0].score)
            assertEquals(data[0].dateTime, dataDummy[0].dateTime)
            assertEquals(data[0].totalTime, dataDummy[0].totalTime)
            assertEquals(data[0].correctAnswer, dataDummy[0].correctAnswer)
            assertEquals(data[0].wrongAnswer, dataDummy[0].wrongAnswer)
            assertEquals(data[0].notAnswered, dataDummy[0].notAnswered)
            assertEquals(data[0].tryoutCategory, dataDummy[0].tryoutCategory)
            assertEquals(data[0].answers, dataDummy[0].answers)
        } finally {
            profileViewModel.getScoreData().removeObserver(observer)
        }
    }

    @Test
    fun `When Network Error Should Return Error`() {
        val observer = Observer<Result<List<ScoreModel>>> {}
        val errorMessage = "Error masbrow"
        try {
            val expectedScore = MutableLiveData<Result<List<ScoreModel>>>()
            expectedScore.value = Result.Error(errorMessage)
            Mockito.`when`(profileViewModel.getScoreData()).thenReturn(expectedScore)

            val actualScore = profileViewModel.getScoreData().getOrAwaitValue()

            Mockito.verify(numerationUseCase).getUserListScore()

            assertNotNull(actualScore)
            assertTrue(actualScore is Result.Error)
            assertEquals((actualScore as Result.Error).error, errorMessage)
        } finally {
            profileViewModel.getScoreData().removeObserver(observer)
        }
    }
}