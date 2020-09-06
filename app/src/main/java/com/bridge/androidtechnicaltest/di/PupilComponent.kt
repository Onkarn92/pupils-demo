package com.bridge.androidtechnicaltest.di

import androidx.lifecycle.ViewModelStoreOwner
import com.bridge.androidtechnicaltest.repository.PupilRepository
import com.bridge.androidtechnicaltest.ui.MainActivity
import com.bridge.androidtechnicaltest.ui.PupilAdapter
import com.bridge.androidtechnicaltest.ui.PupilViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [PupilModule::class])
interface PupilComponent {
	
	/**
	 * Inject [MainActivity] instance into dagger component.
	 */
	fun injectMainActivity(mainActivity: MainActivity)
	
	/**
	 * Provides [PupilAdapter] instance.
	 */
	fun getPupilAdapter(): PupilAdapter
	
	/**
	 * Provides [PupilViewModel] instance.
	 */
	fun getPupilViewModel(): PupilViewModel
	
	/**
	 * Provides [PupilRepository] instance used by view model factory.
	 */
	fun getPupilRepository(): PupilRepository
	
	@Component.Builder
	interface Builder {
		
		@BindsInstance
		fun withCallback(callback: PupilAdapter.Callback): Builder
		
		@BindsInstance
		fun withOwner(owner: ViewModelStoreOwner): Builder
		
		fun build(): PupilComponent
	}
}