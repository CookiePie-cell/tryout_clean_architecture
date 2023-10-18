package com.gosty.tryoutapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiscussionModel(
	val image: String? = null,
	val discussionText: String? = null,
	val questionId: Int? = null,
	val idDiscussion: Int? = null
) : Parcelable