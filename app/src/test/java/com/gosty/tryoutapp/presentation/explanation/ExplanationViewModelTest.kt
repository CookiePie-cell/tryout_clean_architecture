package com.gosty.tryoutapp.presentation.explanation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gosty.tryoutapp.core.domain.models.SubjectModel
import com.gosty.tryoutapp.core.domain.models.TryoutModel
import com.gosty.tryoutapp.core.domain.usecase.NumerationUseCase
import com.gosty.tryoutapp.core.utils.Result
import com.gosty.tryoutapp.utils.DataDummy
import com.gosty.tryoutapp.utils.getOrAwaitValue
import org.junit.Assert

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ExplanationViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var numerationUseCase: NumerationUseCase
    private lateinit var explanationViewModel: ExplanationViewModel
    private val dataDummy = DataDummy.generateSubjectModels()

    @Before
    fun setUp() {
        explanationViewModel = ExplanationViewModel(numerationUseCase)
    }

    @Test
    fun `when Get Subject For Explanation Should Not Null and Return Success`() {
        val observer = Observer<Result<List<SubjectModel>>> {}
        try {
            val expectedSubject = MutableLiveData<Result<List<SubjectModel>>>()
            expectedSubject.value = Result.Success(dataDummy)
            `when`(numerationUseCase.getAllNumerationTryoutsForExplanation()).thenReturn(expectedSubject)

            val actualSubject = explanationViewModel.getSubjectForExplanation().getOrAwaitValue()

            Mockito.verify(numerationUseCase).getAllNumerationTryoutsForExplanation()
            Assert.assertNotNull(actualSubject)
            Assert.assertTrue(actualSubject is Result.Success)
            Assert.assertEquals(dataDummy.size, (actualSubject as Result.Success).data.size)

        } finally {
            explanationViewModel.getSubjectForExplanation().removeObserver(observer)
        }
    }

    @Test
    fun `when Get Subject For Explanation Should Return the Same Data`() {
        val observer = Observer<Result<List<SubjectModel>>> {}
        try {
            val expectedSubject = MutableLiveData<Result<List<SubjectModel>>>()
            expectedSubject.value = Result.Success(dataDummy)
            `when`(numerationUseCase.getAllNumerationTryoutsForExplanation()).thenReturn(expectedSubject)

            val actualSubject = explanationViewModel.getSubjectForExplanation().getOrAwaitValue()

            Mockito.verify(numerationUseCase).getAllNumerationTryoutsForExplanation()

            val data = (actualSubject as Result.Success).data

            Assert.assertNotNull(actualSubject)

            val tryoutModel = TryoutModel(
                1,
                "category-name",
                123,
                null,
                "subject-name",
                false,
                234
            )

            Assert.assertEquals(data[0].idSubject, dataDummy[0].idSubject)
            Assert.assertEquals(data[0].subjectName, dataDummy[0].subjectName)
            Assert.assertEquals(data[0].tryout?.get(0) ?: emptyList<TryoutModel>(), tryoutModel)
        } finally {
            explanationViewModel.getSubjectForExplanation().removeObserver(observer)
        }
    }
}