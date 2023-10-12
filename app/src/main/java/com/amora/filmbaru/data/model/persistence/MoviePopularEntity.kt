package com.amora.filmbaru.data.model.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_popular")
data class MoviePopularEntity(
	val overview: String? = null,

	val title: String? = null,

	@ColumnInfo(name = "poster_path")
	val posterPath: String? = null,

	@ColumnInfo(name = "release_date")
	val releaseDate: String? = null,

	val popularity: Double? = null,

	@ColumnInfo(name = "voteAverage")
	val voteAverage: Int? = null,

	@PrimaryKey(autoGenerate = true)
	val id: Long = 0L,

	val remoteId: Long,

	@ColumnInfo(name = "vote_count")
	val voteCount: Int? = null
)
