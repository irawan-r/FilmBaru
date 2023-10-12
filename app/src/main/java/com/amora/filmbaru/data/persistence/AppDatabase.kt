package com.amora.filmbaru.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amora.filmbaru.data.model.network.RemoteKeysPopular
import com.amora.filmbaru.data.model.network.RemoteKeysUpcoming
import com.amora.filmbaru.data.model.persistence.MovieDetailEntity
import com.amora.filmbaru.data.model.persistence.MovieFavoriteEntity
import com.amora.filmbaru.data.model.persistence.MoviePopularEntity
import com.amora.filmbaru.data.model.persistence.MovieUpcomingEntity
import com.amora.filmbaru.data.model.persistence.VideoMovieEntity
import com.amora.filmbaru.utils.Converters

@Database(
	entities = [
		MoviePopularEntity::class,
		MovieUpcomingEntity::class,
		RemoteKeysUpcoming::class,
		RemoteKeysPopular::class,
		VideoMovieEntity::class,
		MovieDetailEntity::class,
		MovieFavoriteEntity::class
	],
	version = 1,
	exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

	abstract fun movieDao(): MovieDao

	abstract fun remoteKeysDao(): RemoteKeysDao

	abstract fun videoDao(): VideoDao

	abstract fun movieDetailDao(): MovieDetailDao
}