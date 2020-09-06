package com.bridge.androidtechnicaltest.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bridge.androidtechnicaltest.models.Pupil
import com.bridge.androidtechnicaltest.network.HttpEventTracker
import com.bridge.androidtechnicaltest.repository.PupilRepository
import okhttp3.ResponseBody

class PupilDetailsViewModel(
		val app: Application,
		private val repository: PupilRepository
) : AndroidViewModel(app) {
	
	private val pupilObservable: MutableLiveData<Pupil> = MutableLiveData()
	private val errorObservable: MutableLiveData<Pair<String, Throwable>> = MutableLiveData()
	
	fun getPupils(pupilId: Long) {
		repository.getPupil(pupilId, object : HttpEventTracker<Pupil> {
			
			override fun onCallSuccess(response: Pupil) {
				pupilObservable.postValue(response)
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
	 * Holds latest data of added pupil.
	 */
	fun gePupilObservable() = pupilObservable
	
	/**
	 * Holds any error response.
	 */
	fun getErrorObservable() = errorObservable
}