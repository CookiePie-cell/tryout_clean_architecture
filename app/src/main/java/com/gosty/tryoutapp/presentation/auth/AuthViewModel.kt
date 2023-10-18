package com.gosty.tryoutapp.presentation.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.gosty.tryoutapp.core.domain.repository.UserRepository
import com.gosty.tryoutapp.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    /***
    *   this method is to processing sign in of the user by automatically get the list of user's account
     *   @author Ghifari
     *   @since September 14th, 2023
    * */
    fun signIn(credential: AuthCredential) = userUseCase.signIn(credential)
}