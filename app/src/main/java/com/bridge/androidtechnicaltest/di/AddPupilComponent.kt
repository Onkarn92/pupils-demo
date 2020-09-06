package com.bridge.androidtechnicaltest.di

import androidx.lifecycle.ViewModelStoreOwner
import com.bridge.androidtechnicaltest.repository.PupilRepository
import com.bridge.androidtechnicaltest.ui.AddPupilViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [PupilModule::class])
interface AddPupilComponent {
	
	/**
	 * Provides [AddPupilViewModel] instance.
	 */
	fun getAddPupilViewModel(): AddPupilViewModel
	
	/**
	 * Provides [PupilRepository] instance used by view model factory.
	 */
	fun getPupilRepository(): PupilRepository
	
	@Component.Builder
	interface Builder {
		
		@BindsInstance
		fun withOwner(owner: ViewModelStoreOwner): Builder
		
		fun build(): AddPupilComponent
	}
}