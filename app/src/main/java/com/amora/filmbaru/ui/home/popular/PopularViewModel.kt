package com.amora.filmbaru.ui.home.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.amora.filmbaru.data.model.network.Movie
import com.amora.filmbaru.data.model.network.Movie.Companion.toMovie
import com.amora.filmbaru.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
	private val repository: MainRepository
) : ViewModel() {

	private val _moviesState = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
	val moviesState = _moviesState.asStateFlow()

	fun getMoviesPopular() {
		viewModelScope.launch {
			repository.getPopularMovies().cachedIn(viewModelScope)
				.collect { response ->
					_moviesState.update {
						val data = response.map { it.toMovie() }
						data
					}
				}
		}
	}
}