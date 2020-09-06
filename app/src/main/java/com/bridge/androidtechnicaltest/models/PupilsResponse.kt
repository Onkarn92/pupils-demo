package com.bridge.androidtechnicaltest.models

import com.bridge.androidtechnicaltest.utils.DEFAULT_INT
import com.google.gson.annotations.SerializedName

data class PupilsResponse(
		@SerializedName("items") var items: ArrayList<Pupil> = arrayListOf(),
		@SerializedName("pageNumber") var pageNumber: Int = DEFAULT_INT,
		@SerializedName("itemCount") var itemCount: Int = DEFAULT_INT,
		@SerializedName("totalPages") var totalPages: Int = DEFAULT_INT
)