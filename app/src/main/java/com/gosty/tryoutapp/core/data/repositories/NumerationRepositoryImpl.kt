package com.gosty.tryoutapp.core.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.gosty.tryoutapp.core.data.source.remote.RemoteDataSource
import com.gosty.tryoutapp.core.data.source.remote.network.ApiResponse
import com.gosty.tryoutapp.core.domain.models.AnswerModel
import com.gosty.tryoutapp.core.domain.models.ScoreModel
import com.gosty.tryoutapp.core.domain.models.SubjectModel
import com.gosty.tryoutapp.core.domain.repository.NumerationRepository
import com.gosty.tryoutapp.core.utils.DataMapper
import com.gosty.tryoutapp.core.utils.Result
import javax.inject.Inject

class NumerationRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : NumerationRepository {

    /***
     * This method to get all the numeration tryouts.
     * @author Ghifari Octaverin
     * @since Sept 4th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    override fun getAllNumerationTryouts(): LiveData<Result<List<SubjectModel>>> {
        return remoteDataSource.getAllNumerationTryouts().map { response ->
            when (response) {
                is ApiResponse.Fetching -> {
                    Result.Loading
                }
                is ApiResponse.Success -> {
                    val subjectList = response.data?.map { DataMapper.mapDataItemResponseToSubjectModel(it) }
                    Result.Success(subjectList!!)

                }
                is ApiResponse.Empty -> {
                    Result.Success(emptyList()) // Or Result.Empty if you have a custom Result type
                }
                is ApiResponse.Error -> {
                    Result.Error(response.errorMessage)
                }
            }
        }
    }

    /***
     *   this method is to get all numeration tryout data for implementing on explanation feature.
     *   @author Andi
     *   @since September 8th, 2023
     *   Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    override fun getAllNumerationTryoutsForExplanation(): LiveData<Result<List<SubjectModel>>> {
        return remoteDataSource.getAllNumerationTryoutsForExplanation().map { response ->
            when(response) {
                is ApiResponse.Fetching -> Result.Loading
                is ApiResponse.Success -> {
                    val subjectList = response.data?.map { DataMapper.mapDataItemResponseToSubjectModel(it) }
                    Result.Success(subjectList!!)
                }
                is ApiResponse.Empty -> Result.Success(emptyList())
                is ApiResponse.Error -> Result.Error(response.errorMessage)
            }
        }
    }

    /***
     * This method to send user answer to realtime database.
     * @param answerModel variable that contain user answer
     * @author Ghifari Octaverin
     * @since Sept 4th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    override fun postUserAnswer(answerModel: AnswerModel): LiveData<Result<String>> {
        return remoteDataSource.postUserAnswer(answerModel).map {
            when(it) {
                is ApiResponse.Fetching -> Result.Loading
                is ApiResponse.Error -> Result.Error(it.errorMessage)
                is ApiResponse.Empty -> Result.Success("")
                is ApiResponse.Success -> Result.Success("Success")
            }
        }
    }

    /***
     *   this method is to get all user's data related to his/her scores for score feature by using firebase realtime db.
     *   @author Andi
     *   @since September 11th, 2023
     */
    override fun getUserListScore(): LiveData<Result<List<ScoreModel>>> {
        return remoteDataSource.getUserListScore().map {
            when(it) {
                is ApiResponse.Fetching -> Result.Loading
                is ApiResponse.Error -> Result.Error(it.errorMessage)
                is ApiResponse.Empty -> Result.Success(emptyList())
                is ApiResponse.Success -> Result.Success(it.data)
            }
        }
    }

    /***
     * This method to get all user answers from realtime database.
     * @author Ghifari Octaverin
     * @since Sept 7th, 2023
     */
    override fun getAllUserAnswer(): LiveData<Result<List<AnswerModel>>> {
        return remoteDataSource.getAllUserAnswer().map {
            when(it) {
                is ApiResponse.Fetching -> Result.Loading
                is ApiResponse.Error -> Result.Error(it.errorMessage)
                is ApiResponse.Empty -> Result.Success(emptyList())
                is ApiResponse.Success -> Result.Success(it.data)
            }
        }
    }

    /***
     * This method to delete all user answers in realtime database.
     * @author Ghifari Octaverin
     * @since Sept 7th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    override fun deleteAllUserAnswer(): LiveData<Result<String>> {
        return remoteDataSource.deleteAllUserAnswer().map {
            when(it) {
                is ApiResponse.Fetching -> Result.Loading
                is ApiResponse.Error -> Result.Error(it.errorMessage)
                is ApiResponse.Empty -> Result.Success("")
                is ApiResponse.Success -> Result.Success("Success")
            }
        }
    }

    /***
     * This method to send user score to realtime database.
     * @param score variable that contain user score model
     * @author Ghifari Octaverin
     * @since Sept 11th, 2023
     * Updated Sept 14th, 2023 by Ghifari Octaverin
     */
    override fun postUserScore(score: ScoreModel): LiveData<Result<String>> {
        return remoteDataSource.postUserScore(score).map {
            when(it) {
                is ApiResponse.Fetching -> Result.Loading
                is ApiResponse.Error -> Result.Error(it.errorMessage)
                is ApiResponse.Empty -> Result.Success("")
                is ApiResponse.Success -> Result.Success("Success")
            }
        }
    }
}