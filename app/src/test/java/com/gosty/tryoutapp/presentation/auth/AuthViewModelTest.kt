package com.gosty.tryoutapp.presentation.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.auth.AuthCredential
import com.gosty.tryoutapp.core.domain.usecase.NumerationUseCase
import com.gosty.tryoutapp.core.domain.usecase.UserUseCase
import com.gosty.tryoutapp.core.utils.Result
import com.gosty.tryoutapp.presentation.profile.ProfileViewModel
import com.gosty.tryoutapp.utils.getOrAwaitValue
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userUseCase: UserUseCase
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setUp() {
        authViewModel = AuthViewModel(userUseCase)
    }

    @Test
    fun `test signIn success`() {
        val observer = Observer<Result<String>> {}
        val authCredential = mock(AuthCredential::class.java)
        try {
            val expectedResult = MutableLiveData<Result<String>>()
            expectedResult.value = Result.Success("Berhasil: User-123")
            Mockito.`when`(userUseCase.signIn(authCredential)).thenReturn(expectedResult)

            val actualResult = authViewModel.signIn(authCredential).getOrAwaitValue()

            Mockito.verify(userUseCase).signIn(authCredential)
            assertNotNull(actualResult)
            assertTrue(actualResult is Result.Success)
            assertEquals((expectedResult.value as Result.Success).data, (actualResult as Result.Success).data)

        } finally {
            authViewModel.signIn(authCredential).removeObserver(observer)
        }
    }
}