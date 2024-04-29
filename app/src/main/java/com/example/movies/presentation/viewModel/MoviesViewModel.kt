package com.example.movies.presentation.viewModel

import androidx.collection.emptyFloatSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.local.MovieDao
import com.example.movies.data.remote.MovieApi
import com.example.movies.data.remote.MovieApi.Companion.API_KEY
import com.example.movies.data.remote.MovieRepositoryImpl
import com.example.movies.data.remote.model.MovieListResponse
import com.example.movies.domain.MovieRepository
import com.example.movies.domain.mappers.MoviesMapper
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.uiState.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepositoryImpl,
    private val movieDao: MovieDao
) : ViewModel() {


   private val _popularMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val popularMovies: StateFlow<UiState<List<Movie>>> = _popularMovies.asStateFlow()

    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie>(Movie())

    val selectedMovie = _selectedMovie.asStateFlow()

    private val API_KEY = MovieApi.API_KEY

    init {
        fetchPopularMovies(API_KEY, 1)
    }

    /**
     * Fetches popular movies from the database or the API.
     *
     * This function first checks if the database is populated with movies. If it is, it fetches the movies from the database.
     * If the database is not populated, it fetches the movies from the API.
     *
     * @param apiKey The API key used to authenticate the request to the API.
     * @param page The page number to fetch from the API.
     */
    private fun fetchPopularMovies(apiKey: String = API_KEY, page: Int) {
        val movieMapper = MoviesMapper()

        viewModelScope.launch {
            // Check if the database is populated
            val moviesFromDb = movieDao.getAllMovies()

            _popularMovies.value = UiState.Loading

            if (moviesFromDb.isNotEmpty()) {
                // If the database is populated, fetch the movies from the database
                _popularMovies.value = UiState.Success(moviesFromDb.map { movieMapper.movieEntityToMovie(it) })

            } else {

                delay(2000) //Just so loading animation is visible for a while

                try {
                    val result = movieRepository.getPopularMovies(apiKey, page) // If the database is not populated, fetch the movies from the API
                    val movies = result.getOrNull()?.results ?: emptyList()

                    if (result.isSuccess) {
                        _popularMovies.value = UiState.Success(movies)
                        movieDao.insertAllMovies(movies.map { movieMapper.movieToMovieEntity(it) })  // Then populate the database
                    } else {
                        _popularMovies.value = UiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    }
                } catch (e: Exception) {
                    _popularMovies.value = UiState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }

    /**
     * Fetches a movie by its ID from the database.
     *
     * This function fetches a movie with the given ID from the database. If the movie is found, it sets the _selectedMovie value to the fetched movie.
     * If the movie is not found, it prints an error message.
     *
     * @param id The ID of the movie to fetch from the database.
     */
    fun getMovieById(id: Int) {
        viewModelScope.launch {
            movieDao.getMovieById(id)?.let {
                // If the movie is found, set the _selectedMovie value to the fetched movie
                _selectedMovie.value = MoviesMapper().movieEntityToMovie(it)
            } ?: println("Error finding movie with id: $id") // If the movie is not found, print an error message
        }
    }

    /**
     * Updates the favorite status of a movie.
     *
     * This function toggles the favorite status of the given movie. It updates the favorite status in the database,
     * updates the _popularMovies value with the updated movie, and updates the _favoriteMovies value accordingly.
     *
     * If the movie is marked as favorite, it is added to the _favoriteMovies value. If it is unmarked as favorite, it is removed from the _favoriteMovies value.
     *
     * If an exception is thrown when updating the favorite status, it prints an error message.
     *
     * @param movie The movie to update the favorite status of.
     */

    fun updateIsFavorite(movie: Movie) {
        viewModelScope.launch {
            try {
                movie.isFavorite = !movie.isFavorite
                movieDao.updateFavoriteMovieStatus(movie.id, movie.isFavorite)

                val updatedMovies = (_popularMovies.value as? UiState.Success)?.data?.map {
                    if (it.id == movie.id) movie else it
                } ?: emptyList()
                _popularMovies.emit(UiState.Success(updatedMovies))

                _favoriteMovies.value = if (movie.isFavorite) _favoriteMovies.value + movie else _favoriteMovies.value.filter { it.id != movie.id }
            } catch (e: Exception) {
                println("Error updating favorite status: ${e.message}")
            }
        }
    }

    /**
     * Fetches the favorite movies from the database.
     *
     * This function fetches the favorite movies from the database and updates the _favoriteMovies value with the fetched movies.
     * The fetched movies are mapped from MovieEntity objects to Movie objects using the MoviesMapper.
     */
    fun getFavoriteMovies() {
        viewModelScope.launch {
            val favoriteMovies = movieDao.getFavoriteMovies()
            _favoriteMovies.value = favoriteMovies.map { MoviesMapper().movieEntityToMovie(it) }
        }
    }

    fun isFavorite(movieId: Int): Flow<Boolean> {
        return flow { emit( movieDao.isMovieFavorite(movieId)) }
    }

    /**
     * Extracts the year of release from a release date string.
     *
     * This function takes a release date string in the format "YYYY-MM-DD" and returns the year of release as a string.
     *
     * @param releaseDate The release date string to extract the year from.
     * @return The year of release as a string.
     */
    fun getYearOfRelease(releaseDate: String) : String {
        return releaseDate.substring(0, 4)
    }

    /**
     * Formats a rating value to one decimal place.
     *
     * This function takes a rating value as a double and returns it formatted to one decimal place as a string.
     *
     * @param rating The rating value to format.
     * @return The formatted rating value as a string.
     */
    fun formatRating(rating: Double) : String {
        return String.format("%.1f", rating)
    }


}