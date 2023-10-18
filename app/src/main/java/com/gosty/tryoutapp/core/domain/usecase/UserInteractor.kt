package com.gosty.tryoutapp.core.domain.usecase

import androidx.lifecycle.LiveData
import com.google.firebase.auth.AuthCredential
import com.gosty.tryoutapp.core.domain.repository.UserRepository
import com.gosty.tryoutapp.core.utils.Result

class UserInteractor(private val userRepository: UserRepository): UserUseCase {

    override fun signIn(credential: AuthCredential): LiveData<Result<String>> = userRepository.signIn(credential)

}