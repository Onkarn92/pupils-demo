package com.bridge.androidtechnicaltest.network

import com.bridge.androidtechnicaltest.models.Pupil
import com.bridge.androidtechnicaltest.models.PupilsResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PupilApi {
	
	@GET("pupils")
	fun getPupils(
			@Header("X-Request-ID") requestId: String = NetworkUtils.REQUEST_ID_GET_ALL,
			@Query("page") page: Int = 1
	): Call<PupilsResponse>
	
	@POST("pupils")
	fun addPupil(
			@Header("X-Request-ID") requestId: String = NetworkUtils.REQUEST_ID_POST,
			@Body requestBody: RequestBody
	): Call<Pupil>
	
	@DELETE("pupils/{pupilId}")
	fun deletePupil(
			@Header("X-Request-ID") requestId: String = NetworkUtils.REQUEST_ID_DELETE,
			@Path("pupilId") id: Long
	): Call<Unit>
}