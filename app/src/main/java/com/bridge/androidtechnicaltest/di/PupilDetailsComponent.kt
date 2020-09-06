package com.bridge.androidtechnicaltest.di

import androidx.lifecycle.ViewModelStoreOwner
import com.bridge.androidtechnicaltest.repository.PupilRepository
import com.bridge.androidtechnicaltest.ui.PupilDetailsViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [PupilModule::class])
interface PupilDetailsComponent {
	
	/**
	 * Provides [PupilDetailsViewModel] instance.
	 */
	fun getPupilDetailsViewModel(): PupilDetailsViewModel
	
	/**
	 * Provides [PupilRepository] instance used by view model factory.
	 */
	fun getPupilRepository(): PupilRepository
	
	@Component.Builder
	interface Builder {
		
		@BindsInstance
		fun withOwner(owner: ViewModelStoreOwner): Builder
		
		fun build(): PupilDetailsComponent
	}
}