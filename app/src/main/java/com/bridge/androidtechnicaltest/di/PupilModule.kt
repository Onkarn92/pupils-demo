package com.bridge.androidtechnicaltest.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bridge.androidtechnicaltest.repository.PupilRepository
import com.bridge.androidtechnicaltest.ui.AddPupilViewModel
import com.bridge.androidtechnicaltest.ui.Factory
import com.bridge.androidtechnicaltest.ui.PupilAdapter
import com.bridge.androidtechnicaltest.ui.PupilDetailsViewModel
import com.bridge.androidtechnicaltest.ui.PupilViewModel
import dagger.Module
import dagger.Provides

@Module
class PupilModule {
	
	@Provides
	fun providePupilAdapter(callback: PupilAdapter.Callback): PupilAdapter = PupilAdapter(callback)
	
	@Provides
	fun providePupilViewModel(
			owner: ViewModelStoreOwner,
			repository: PupilRepository
	): PupilViewModel {
		return ViewModelProvider(owner, Factory(repository))[PupilViewModel::class.java]
	}
	
	@Provides
	fun provideAddPupilViewModel(
			owner: ViewModelStoreOwner,
			repository: PupilRepository
	): AddPupilViewModel {
		return ViewModelProvider(owner, Factory(repository))[AddPupilViewModel::class.java]
	}
	
	@Provides
	fun providePupilDetailsViewModel(
			owner: ViewModelStoreOwner,
			repository: PupilRepository
	): PupilDetailsViewModel {
		return ViewModelProvider(owner, Factory(repository))[PupilDetailsViewModel::class.java]
	}
	
	@Provides
	fun providePupilRepository(): PupilRepository = PupilRepository()
}