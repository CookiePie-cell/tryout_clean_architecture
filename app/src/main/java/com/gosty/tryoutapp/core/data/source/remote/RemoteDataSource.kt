package com.gosty.tryoutapp.core.data.source.remote

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gosty.tryoutapp.BuildConfig
import com.gosty.tryoutapp.core.data.source.remote.network.ApiResponse
import com.gosty.tryoutapp.core.data.source.remote.network.ApiService
import com.gosty.tryoutapp.core.data.source.remote.responses.DataItemResponse
import com.gosty.tryoutapp.core.domain.models.AnswerModel
import com.gosty.tryoutapp.core.domain.models.ScoreModel
import com.gosty.tryoutapp.core.utils.Result

class RemoteDataSource(
    private val apiService: ApiService,
    private val db: FirebaseDatabase,
    private val auth: FirebaseAuth,
    private val crashlytics: FirebaseCrashlytics,
    private val context: Context
) {
    /***
     * This method to get all the numeration tryouts.
     * @author Ghifari Octaverin
     * @since Sept 4th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    fun getAllNumerationTryouts(): LiveData<ApiResponse<List<DataItemResponse?>?>> = liveData {
        val subjectList = MutableLiveData<List<DataItemResponse?>?>()
        emit(ApiResponse.Fetching)
        try {
            val response = apiService.getAllNumerationTryouts()
            if (response.isSuccessful) {
//                subjectList.value = response.body()?.data?.map {
//                    DataMapper.mapDataItemResponseToSubjectModel(it)
//                }
                val data = response.body()?.data
                if (data?.isNotEmpty()!!) {
                    subjectList.value = data
                } else {
                    emit(ApiResponse.Empty)
                }
            } else {
                emit(ApiResponse.Error("Code ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            crashlytics.log(e.message.toString())
            emit(ApiResponse.Error(e.message.toString()))
        }
        val data: LiveData<ApiResponse<List<DataItemResponse?>?>> = subjectList.map {
            ApiResponse.Success(it)
        }
        emitSource(data)
    }

    /***
     *   this method is to get all numeration tryout data for implementing on explanation feature.
     *   @author Andi
     *   @since September 8th, 2023
     *   Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    fun getAllNumerationTryoutsForExplanation(): LiveData<ApiResponse<List<DataItemResponse?>?>> = liveData {
        val subjectList = MutableLiveData<List<DataItemResponse?>?>()
        emit(ApiResponse.Fetching)
        try {
            val response = apiService.getAllNumerationTryouts()
            if (response.isSuccessful) {
//                subjectList.value = response.body()?.data?.map {
//                    DataMapper.mapDataItemResponseToSubjectModel(it)
//                }
                val data = response.body()?.data
                if (data?.isNotEmpty()!!) {
                    subjectList.value = data
                } else {
                    emit(ApiResponse.Empty)
                }
            } else {
                emit(ApiResponse.Error("Code ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            crashlytics.log(e.message.toString())
            emit(ApiResponse.Error(e.message.toString()))
        }
        val data: LiveData<ApiResponse<List<DataItemResponse?>?>> = subjectList.map {
            ApiResponse.Success(it)
        }
        emitSource(data)
    }

    /***
     * This method to send user answer to realtime database.
     * @param answerModel variable that contain user answer
     * @author Ghifari Octaverin
     * @since Sept 4th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    fun postUserAnswer(answerModel: AnswerModel): LiveData<ApiResponse<String>> {
        val userId = auth.currentUser?.uid
        val ref = db.reference.child(BuildConfig.ANSWER_REF)
        val result = MutableLiveData<ApiResponse<String>>()
        if (isInternetAvailable()) {
            ref.child(userId!!).child(answerModel.questionId.toString()).setValue(answerModel)
                .addOnFailureListener {
                    result.value = ApiResponse.Error(it.message.toString())
                    crashlytics.log(it.message.toString())
                }
        } else {
            result.value = ApiResponse.Error("Connection Error")
        }
        return result
    }

    /***
     *   this method is to get all user's data related to his/her scores for score feature by using firebase realtime db.
     *   @author Andi
     *   @since September 11th, 2023
     */
    fun getUserListScore(): LiveData<ApiResponse<List<ScoreModel>>> {
        val userId = auth.currentUser?.uid
        val ref = db.reference.child(BuildConfig.USER_REF)
        val result = MutableLiveData<ApiResponse<List<ScoreModel>>>()

        result.value = ApiResponse.Fetching

        if (isInternetAvailable()) {
            ref.child(userId!!).orderByChild("dateTime")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val data = snapshot.children.map {
                            it.getValue(ScoreModel::class.java)!!
                        }
                        result.value = ApiResponse.Success(data)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        result.value = ApiResponse.Error(error.message)
                        crashlytics.log(error.message)
                    }
                })
        } else {
            result.value = ApiResponse.Error("Connection Error")
        }

        return result
    }

    /***
     * This method to get all user answers from realtime database.
     * @author Ghifari Octaverin
     * @since Sept 7th, 2023
     */
    fun getAllUserAnswer(): LiveData<ApiResponse<List<AnswerModel>>> {
        val result = MutableLiveData<ApiResponse<List<AnswerModel>>>()
        val userId = auth.currentUser?.uid
        val ref = db.reference.child(BuildConfig.ANSWER_REF)

        result.value = ApiResponse.Fetching
        if (isInternetAvailable()) {
            ref.child(userId!!).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.children.map {
                        it.getValue(AnswerModel::class.java)!!
                    }
                    result.value = ApiResponse.Success(data)
                }

                override fun onCancelled(error: DatabaseError) {
                    result.value = ApiResponse.Error(error.message)
                    crashlytics.log(error.message)
                }
            })
        } else {
            result.value = ApiResponse.Error("Connection Error")
        }

        return result
    }

    /***
     * This method to delete all user answers in realtime database.
     * @author Ghifari Octaverin
     * @since Sept 7th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    fun deleteAllUserAnswer(): LiveData<ApiResponse<String>> {
        val userId = auth.currentUser?.uid
        val ref = db.reference.child(BuildConfig.ANSWER_REF)
        val result = MutableLiveData<ApiResponse<String>>()
        if (isInternetAvailable()) {
            ref.child(userId!!).removeValue()
                .addOnFailureListener {
                    result.value = ApiResponse.Error(it.message.toString())
                    crashlytics.log(it.message.toString())
                }
        } else {
            result.value = ApiResponse.Error("Connection Error")
        }
        return result
    }

    /***
     * This method to send user score to realtime database.
     * @param score variable that contain user score model
     * @author Ghifari Octaverin
     * @since Sept 11th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    fun postUserScore(score: ScoreModel): LiveData<ApiResponse<String>> {
        val userId = auth.currentUser?.uid
        val ref = db.reference.child(BuildConfig.USER_REF)
        val result = MutableLiveData<ApiResponse<String>>()
        if (isInternetAvailable()) {
            ref.child(userId!!).child(score.scoreId!!).setValue(score)
                .addOnFailureListener {
                    result.value = ApiResponse.Error(it.message.toString())
                    crashlytics.log(it.message.toString())
                }
        } else {
            result.value = ApiResponse.Error("Connection Error")
        }
        return result
    }

    /***
     * This method to check user internet connectivity.
     * @author Ghifari Octaverin
     * @since Sept 13th, 2023
     */
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}