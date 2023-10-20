package com.gosty.tryoutapp.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiscussionModel(
	val image: String? = null,
	val discussionText: String? = null,
	val questionId: Int? = null,
	val idDiscussion: Int? = null
) : Parcelable