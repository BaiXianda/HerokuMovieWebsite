package com.xiandabai.moviewebsite.services;

import com.xiandabai.moviewebsite.domain.Movie;
import com.xiandabai.moviewebsite.domain.MovieList;
import com.xiandabai.moviewebsite.repositories.MovieListRepository;
import com.xiandabai.moviewebsite.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieListRepository movieListRepository;

    public Movie saveOrUpdateMovie(Long id, Movie movie) {
        MovieList movieList = movieListRepository.getById(id);
        movie.setMovieList(movieList);
        movie.setMovieList_id(id);

        return movieRepository.save(movie);

    }

    public Movie findById(Long movie_id) {
        return movieRepository.getById(movie_id);
    }

    public void deleteMovieById(Long movie_id) {
        Movie movie = findById(movie_id);
        movieRepository.delete(movie);
    }

    public Iterable<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public Iterable<Movie> findAllMoviesByMovieListId(Long movieList_id) {
        return movieRepository.findByMovieList_id(movieList_id);
    }

}
