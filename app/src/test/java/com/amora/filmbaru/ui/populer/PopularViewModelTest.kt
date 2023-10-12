package com.amora.filmbaru.ui.populer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.map
import com.amora.filmbaru.data.State
import com.amora.filmbaru.data.model.network.Movie
import com.amora.filmbaru.data.model.network.Movie.Companion.toPopularEntity
import com.amora.filmbaru.data.repository.MainRepositoryImpl
import com.amora.filmbaru.ui.adapter.MoviesAdapter
import com.amora.filmbaru.ui.home.popular.PopularViewModel
import com.amora.filmbaru.utils.DataDummy
import com.amora.filmbaru.utils.FakeStoryPagingSource
import com.amora.filmbaru.utils.MainDispatcherRule
import com.amora.filmbaru.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PopularViewModelTest {

	@get:Rule
	val instantExecutorRule = InstantTaskExecutorRule()

	@OptIn(ExperimentalCoroutinesApi::class)
	@get:Rule
	val mainDispatcherRule = MainDispatcherRule()

	@Mock
	private lateinit var repository: MainRepositoryImpl

	private lateinit var dashboardViewModel: PopularViewModel
	private val dummyStories = DataDummy.generateDataDummy()
	private val emptyStories = DataDummy.generateEmptyStories()

	@Before
	fun setUp() {
		dashboardViewModel = PopularViewModel(repository)
	}

	@Test
	fun `when GetMoviesPopular Should Not Null And Return Success`() = runBlocking {
		val data: PagingData<Movie> = FakeStoryPagingSource.snapshot(dummyStories)

		val expectedPagingData = MutableStateFlow<State<PagingData<Movie>>>(State.Empty())
		expectedPagingData.update {
			State.Success(data)
		}

		val expectedFlow = flowOf(data.map { it.toPopularEntity() })
		val pagingStory = repository.getPopularMovies()
		`when`(pagingStory).thenReturn(expectedFlow)

		val differ = AsyncPagingDataDiffer(
			diffCallback = MoviesAdapter.differCallback,
			updateCallback = Utils.noopListUpdateCallback,
			workerDispatcher = Dispatchers.Main
		)

		dashboardViewModel.getMoviesPopular()
		verify(repository).getPopularMovies()

		val actualData = dashboardViewModel.moviesState.first()
		differ.submitData(actualData)

		assertNotNull(differ.snapshot())
		assertEquals(dummyStories.size, differ.snapshot().size)
		assertEquals(dummyStories[0], differ.snapshot()[0])
	}

	@Test
	fun `when GetMoviesPopular Is Empty`() = runBlocking {
		val data: PagingData<Movie> = PagingData.empty()

		val expectedPagingData = MutableStateFlow<State<PagingData<Movie>>>(State.Empty())
		expectedPagingData.update {
			State.Success(data)
		}

		val expectedFlow = flowOf(data.map { it.toPopularEntity() })
		val pagingStory = repository.getPopularMovies()
		`when`(pagingStory).thenReturn(expectedFlow)

		val differ = AsyncPagingDataDiffer(
			diffCallback = MoviesAdapter.differCallback,
			updateCallback = Utils.noopListUpdateCallback,
			workerDispatcher = Dispatchers.Main
		)

		dashboardViewModel.getMoviesPopular()
		verify(repository).getPopularMovies()

		val actualData = dashboardViewModel.moviesState.first()
		differ.submitData(actualData)

		assertNotNull(differ.snapshot())
		assertEquals(emptyStories.size, differ.snapshot().size)
	}
}