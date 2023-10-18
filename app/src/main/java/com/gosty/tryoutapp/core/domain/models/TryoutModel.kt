package com.gosty.tryoutapp.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TryoutModel(
    val subjectId: Int? = null,
    val categoryName: String? = null,
    val categoryId: Int? = null,
    val question: List<QuestionModel?>? = null,
    val subjectName: String? = null,
    val isFav: Boolean? = null,
    val id: Int? = null
) : Parcelable