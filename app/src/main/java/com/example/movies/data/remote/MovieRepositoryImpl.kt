package com.example.movies.data.remote

import com.example.movies.data.local.MovieDao
import com.example.movies.domain.MovieRepository
import com.example.movies.domain.model.Movie
import com.example.movies.data.remote.model.MovieListResponse



/*repository should handle the API call,
 check if the response is successful,
  extract the data from the response, and return this data.
 */
class MovieRepositoryImpl(private val movieApi: MovieApi, private val movieDao: MovieDao) /*@Inject constructor(private val movieApi: MovieApi)*/ : MovieRepository {


        override suspend fun getPopularMovies(apiKey: String, page: Int): Result<MovieListResponse> {
            val response = runCatching { movieApi.getPopularMovies(apiKey, page) }
            return response.fold(
                onSuccess = {
                    if (it.isSuccessful && it.body() != null) Result.success(it.body()!!)
                    else Result.failure(Exception("Error fetching popular movies: ${it.errorBody()?.string()}"))
                },
                onFailure = { Result.failure(it) }
            )
        }

        override suspend fun getMovieById(apiKey: String, id: Int): Result<Movie> {
            val response = runCatching { movieApi.getMovieById(apiKey, id) }
            return response.fold(
                onSuccess = {
                    if (it.isSuccessful && it.body() != null) Result.success(it.body()!!)
                    else Result.failure(Exception("Error fetching movie: ${it.errorBody()?.string()}"))
                },
                onFailure = { Result.failure(it) }
            )
        }


}