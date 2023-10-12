package com.amora.filmbaru.di

import android.app.Application
import androidx.room.Room
import com.amora.filmbaru.R
import com.amora.filmbaru.data.persistence.AppDatabase
import com.amora.filmbaru.data.persistence.MovieDao
import com.amora.filmbaru.data.persistence.MovieDetailDao
import com.amora.filmbaru.data.persistence.VideoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

	@Provides
	@Singleton
	fun provideAppDatabase(application: Application): AppDatabase {
		return Room.databaseBuilder(
			application,
			AppDatabase::class.java,
			application.getString(R.string.database)
		).fallbackToDestructiveMigration()
			.build()
	}

	@Provides
	@Singleton
	fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
		return appDatabase.movieDao()
	}

	@Provides
	@Singleton
	fun provideMovieDetailDao(appDatabase: AppDatabase): MovieDetailDao {
		return appDatabase.movieDetailDao()
	}

	@Provides
	@Singleton
	fun provideVideoDao(appDatabase: AppDatabase): VideoDao {
		return appDatabase.videoDao()
	}
}