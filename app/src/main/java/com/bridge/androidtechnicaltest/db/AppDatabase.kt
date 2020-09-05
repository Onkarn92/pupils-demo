package com.bridge.androidtechnicaltest.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bridge.androidtechnicaltest.App
import com.bridge.androidtechnicaltest.models.Pupil

@Database(entities = [Pupil::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	
	companion object {
		
		private const val NAME = "pupils_db"
		private val lock = Any()
		@Volatile private var appDatabase: AppDatabase? = null
		
		/**
		 * Provide and maintain single instance.
		 */
		fun getInstance(): AppDatabase = appDatabase ?: synchronized(lock) {
			appDatabase ?: buildDatabase().also {appDatabase = it}
		}
		
		private fun buildDatabase(): AppDatabase = Room.databaseBuilder(App.getContext(), AppDatabase::class.java, NAME).apply {
			fallbackToDestructiveMigration()
		}.build()
	}
	
	/**
	 * Data access object for image entity.
	 */
	abstract fun pupilDao(): PupilDao
}