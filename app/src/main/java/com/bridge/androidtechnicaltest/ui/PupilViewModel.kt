package com.bridge.androidtechnicaltest.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bridge.androidtechnicaltest.models.Pupil
import com.bridge.androidtechnicaltest.network.HttpEventTracker
import com.bridge.androidtechnicaltest.network.NetworkUtils
import com.bridge.androidtechnicaltest.repository.PupilRepository
import okhttp3.ResponseBody

class PupilViewModel(
		val app: Application,
		private val repository: PupilRepository
) : AndroidViewModel(app) {
	
	private val pupilObservable: MutableLiveData<List<Pupil>> = MutableLiveData()
	private val errorObservable: MutableLiveData<Pair<String, Throwable>> = MutableLiveData()
	
	fun getPupils() {
		repository.getAllPupils(1, object : HttpEventTracker<List<Pupil>> {
			
			override fun onCallSuccess(response: List<Pupil>) {
				if (response.isNotEmpty()) {
					pupilObservable.postValue(response)
				} else {
					val emptyData = NetworkUtils.getRequestFailReason()
					errorObservable.postValue(emptyData.first to emptyData.second)
				}
			}
			
			override fun onCallFail(
					cause: String,
					throwable: Throwable,
					responseBody: ResponseBody?
			) {
				errorObservable.postValue(cause to throwable)
			}
		})
	}
	
	fun deletePupil(pupil: Pupil) {
		repository.deletePupil(pupil, object : HttpEventTracker<Unit> {
			override fun onCallSuccess(response: Unit) {
				getPupils()
			}
			
			override fun onCallFail(
					cause: String,
					throwable: Throwable,
					responseBody: ResponseBody?
			) {
				errorObservable.postValue(cause to throwable)
			}
		})
	}
	
	/**
	 * Holds latest data of all pupils.
	 */
	fun getPupilObservable() = pupilObservable
	
	/**
	 * Holds any error response.
	 */
	fun getErrorObservable() = errorObservable
}