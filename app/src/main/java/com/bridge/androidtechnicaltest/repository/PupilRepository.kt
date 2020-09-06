package com.bridge.androidtechnicaltest.repository

import com.bridge.androidtechnicaltest.db.AppDatabase
import com.bridge.androidtechnicaltest.helpers.AppExecutors
import com.bridge.androidtechnicaltest.models.Pupil
import com.bridge.androidtechnicaltest.models.PupilsResponse
import com.bridge.androidtechnicaltest.network.HttpEventTracker
import com.bridge.androidtechnicaltest.network.HttpOperationCallback
import com.bridge.androidtechnicaltest.network.HttpOperationWrapper
import com.bridge.androidtechnicaltest.network.NetworkUtils
import com.bridge.androidtechnicaltest.network.PupilApi
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call

class PupilRepository {
	
	private val pupilApi: PupilApi by lazy {NetworkUtils.retrofit.create(PupilApi::class.java)}
	
	fun getAllPupils(
			page: Int,
			callback: HttpEventTracker<List<Pupil>>
	) {
		val call = pupilApi.getPupils(page = page)
		HttpOperationWrapper<PupilsResponse>().initCall(call, object : HttpOperationCallback<PupilsResponse> {
			override fun onResponse(
					call: Call<PupilsResponse>,
					result: PupilsResponse?,
					errorPair: Pair<String, Throwable>,
					errorBody: ResponseBody?
			) {
				when {
					result != null && result.items.isNotEmpty() -> {
						AppExecutors.diskIO.execute {
							AppDatabase.getInstance().pupilDao().saveOrUpdatePupils(result.items)
							val pupils = AppDatabase.getInstance().pupilDao().pupils
							AppExecutors.mainThread.execute {
								callback.onCallSuccess(pupils)
							}
						}
					}
					else -> {
						AppExecutors.diskIO.execute {
							val pupils = AppDatabase.getInstance().pupilDao().pupils
							AppExecutors.mainThread.execute {
								if (pupils.isNotEmpty()) {
									callback.onCallSuccess(pupils)
								} else {
									callback.onCallFail(errorPair.first, errorPair.second, errorBody)
								}
							}
						}
					}
				}
			}
		})
	}
	
	fun addPupil(
			pupil: Pupil,
			callback: HttpEventTracker<Pupil>
	) {
		val request = RequestBody.create(MediaType.parse(NetworkUtils.TYPE_JSON), pupil.buildPostRequest())
		val call = pupilApi.addPupil(requestBody = request)
		HttpOperationWrapper<Pupil>().initCall(call, object : HttpOperationCallback<Pupil> {
			override fun onResponse(
					call: Call<Pupil>,
					result: Pupil?,
					errorPair: Pair<String, Throwable>,
					errorBody: ResponseBody?
			) {
				when {
					result != null -> {
						AppExecutors.diskIO.execute {
							AppDatabase.getInstance().pupilDao().saveOrUpdatePupil(result)
						}
						callback.onCallSuccess(result)
					}
					else -> callback.onCallFail(errorPair.first, errorPair.second, errorBody)
				}
			}
		})
	}
	
	fun getPupil(
			pupilId: Long,
			callback: HttpEventTracker<Pupil>
	) {
		val pupil: Pupil? = AppDatabase.getInstance().pupilDao().getPupil(pupilId)
		if (pupil != null) {
			callback.onCallSuccess(pupil)
		} else {
			val emptyData = NetworkUtils.getRequestFailReason()
			callback.onCallFail(emptyData.first, emptyData.second)
		}
	}
	
	fun deletePupil(
			pupil: Pupil,
			callback: HttpEventTracker<Unit>
	) {
		val call = pupilApi.deletePupil(id = pupil.pupilId)
		HttpOperationWrapper<Unit>().initCall(call, object : HttpOperationCallback<Unit> {
			override fun onResponse(
					call: Call<Unit>,
					result: Unit?,
					errorPair: Pair<String, Throwable>,
					errorBody: ResponseBody?
			) {
				when {
					result != null -> {
						AppExecutors.diskIO.execute {
							AppDatabase.getInstance().pupilDao().deletePupil(pupil)
						}
						callback.onCallSuccess(result)
					}
					else -> callback.onCallFail(errorPair.first, errorPair.second, errorBody)
				}
			}
		})
	}
}