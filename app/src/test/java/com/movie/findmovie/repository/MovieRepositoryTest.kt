package com.movie.findmovie.repository

import com.movie.findmovie.data.network.ApiService
import com.movie.findmovie.data.repository.MovieRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.powermock.modules.junit4.PowerMockRunner
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(PowerMockRunner::class)
class MovieRepositoryTest {

    private val apiService = mock<ApiService>()
    private val movieRepository = MovieRepository(apiService)

    @Test
    fun `test api succeed`() = runBlocking {

        whenever(apiService.getMoviesList()).thenReturn(TestFindMovieData.getFindMovieData()[0])
        movieRepository.getMovieList().collect{
            assertEquals(TestFindMovieData.getFindMovieData()[0],it)
        }

    }

    @Test
    fun apiFailed() = runBlocking {
        val error = NullPointerException("null pointer")
        whenever(apiService.getMoviesList()).thenThrow(error)
        movieRepository.getMovieList().catch {
            assertEquals("null pointer",it.message)
        }.collect{
            assertNull(it)
        }
    }
}