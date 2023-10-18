package com.gosty.tryoutapp.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectionModel(
	val image: String? = null,
	val idSelection: Int? = null,
	val selectionText: String? = null,
	val questionId: Int? = null,
	var isAnswered: Boolean = false
) : Parcelable