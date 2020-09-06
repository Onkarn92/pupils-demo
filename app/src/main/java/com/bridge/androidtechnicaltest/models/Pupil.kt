package com.bridge.androidtechnicaltest.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bridge.androidtechnicaltest.utils.DEFAULT_DOUBLE
import com.bridge.androidtechnicaltest.utils.DEFAULT_LONG
import com.bridge.androidtechnicaltest.utils.EMPTY_STRING
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
@Entity(tableName = "Pupils")
data class Pupil(
		@PrimaryKey @ColumnInfo(name = "pupil_id") @SerializedName("pupilId") val pupilId: Long = DEFAULT_LONG,
		@SerializedName("name") val name: String? = EMPTY_STRING,
		@SerializedName("country") val country: String? = EMPTY_STRING,
		@SerializedName("image") val image: String? = EMPTY_STRING,
		@SerializedName("latitude") val latitude: Double = DEFAULT_DOUBLE,
		@SerializedName("longitude") val longitude: Double = DEFAULT_DOUBLE
) : Parcelable {
	
	fun buildPostRequest(): String {
		val jsonObject = JSONObject()
		jsonObject.put("name", name)
		jsonObject.put("country", country)
		jsonObject.put("image", "http://lorempixel.com/640/480/city?name=Arvel Morar")
		jsonObject.put("latitude", 34.2554)
		jsonObject.put("longitude", 178.0795)
		return jsonObject.toString()
	}
}