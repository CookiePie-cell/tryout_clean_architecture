package com.gosty.tryoutapp.core.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.gosty.tryoutapp.core.data.source.remote.RemoteDataSource
import com.gosty.tryoutapp.core.data.source.remote.network.ApiResponse
import com.gosty.tryoutapp.core.domain.repository.UserRepository
import com.gosty.tryoutapp.core.utils.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : UserRepository {
    /***
     * This method to let user sign in using their Google Account.
     * @author Ghifari Octaverin
     * @since Sept 4th, 2023
     */
    override fun signIn(credential: AuthCredential): LiveData<Result<String>> {
        return remoteDataSource.signIn(credential).map {
            when(it) {
                is ApiResponse.Fetching -> Result.Loading
                is ApiResponse.Error -> Result.Error(it.errorMessage)
                is ApiResponse.Empty -> Result.Success("")
                is ApiResponse.Success -> Result.Success(it.data)
            }
        }
    }
}