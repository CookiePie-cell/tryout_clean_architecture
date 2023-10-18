package com.gosty.tryoutapp.domain.usecase

import androidx.lifecycle.LiveData
import com.google.firebase.auth.AuthCredential
import com.gosty.tryoutapp.utils.Result

interface UserUseCase {
    /***
     * This method is a contract to implement in UserRepositoryImpl to let user sign in using their Google Account.
     * @author Ghifari Octaverin
     * @since Sept 4th, 2023
     */
    fun signIn(credential: AuthCredential): LiveData<Result<String>>
}