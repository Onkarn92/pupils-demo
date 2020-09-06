package com.bridge.androidtechnicaltest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bridge.androidtechnicaltest.App
import com.bridge.androidtechnicaltest.repository.PupilRepository

class Factory(private val repository: PupilRepository) : ViewModelProvider.NewInstanceFactory() {
	
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		return when {
			modelClass.isAssignableFrom(PupilViewModel::class.java) -> {
				(PupilViewModel(App.getApplication(), repository) as T)
			}
			modelClass.isAssignableFrom(AddPupilViewModel::class.java) -> {
				(AddPupilViewModel(App.getApplication(), repository) as T)
			}
			modelClass.isAssignableFrom(PupilDetailsViewModel::class.java) -> {
				(PupilDetailsViewModel(App.getApplication(), repository) as T)
			}
			else -> {
				super.create(modelClass)
			}
		}
	}
}