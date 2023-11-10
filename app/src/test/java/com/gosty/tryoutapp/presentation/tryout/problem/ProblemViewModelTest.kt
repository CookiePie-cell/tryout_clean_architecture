package com.gosty.tryoutapp.presentation.tryout.problem

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gosty.tryoutapp.core.domain.usecase.NumerationUseCase
import com.gosty.tryoutapp.core.utils.Result
import com.gosty.tryoutapp.utils.getOrAwaitValue
import org.junit.Assert

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProblemViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var numerationUseCase: NumerationUseCase
    private lateinit var problemViewModel: ProblemViewModel

    @Before
    fun setUp() {
        problemViewModel = ProblemViewModel(numerationUseCase)
    }

    @Test
    fun `When deleteAllUserAnswer got called result Should Return Success`() {
        val observer = Observer<Result<String>> {}
        try {
            val expectedResult = MutableLiveData<Result<String>>()
            expectedResult.value = Result.Success("Success")
            Mockito.`when`(numerationUseCase.deleteAllUserAnswer()).thenReturn(expectedResult)

            problemViewModel.deleteAllUserAnswer()

            val actualResult = problemViewModel.result.getOrAwaitValue()
            Mockito.verify(numerationUseCase).deleteAllUserAnswer()
            Assert.assertNotNull(actualResult)
            Assert.assertTrue(actualResult is Result.Success)
            Assert.assertEquals((expectedResult.value as Result.Success).data, (actualResult as Result.Success).data)
        } finally {
            problemViewModel.result.removeObserver(observer)
        }
    }
}