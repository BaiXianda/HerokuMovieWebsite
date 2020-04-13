package com.xiandabai.moviewebsite.controllers;


import com.xiandabai.moviewebsite.domain.Movie;
import com.xiandabai.moviewebsite.services.MovieService;
import com.xiandabai.moviewebsite.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin
public class MovieController {

    @Autowired
    MovieService movieService;

    @Autowired
    ValidationErrorService validationErrorService;

    @PostMapping("/{movieList_id}")
    public ResponseEntity<?> createNewMovie(@PathVariable Long movieList_id, @Valid @RequestBody Movie movie, BindingResult result) {

        ResponseEntity<?> error = validationErrorService.MapValidationService(result);
        if (error != null)
            return error;

        Movie m = movieService.saveOrUpdateMovie(movieList_id, movie);
        return new ResponseEntity<Movie>(m, HttpStatus.OK);
    }

    @GetMapping("/{movie_id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long movie_id) {
        Movie m = movieService.findById(movie_id);
        return new ResponseEntity<Movie>(m, HttpStatus.OK);
    }

    @DeleteMapping("/{movie_id}")
    public ResponseEntity<?> deleteMovieById(@PathVariable Long movie_id) {
        movieService.deleteMovieById(movie_id);
        return new ResponseEntity<String>("Movie is deleted", HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Movie> getAllMovies() {
        return movieService.findAllMovies();
    }

    @GetMapping("/all/{movieList_id}")
    public Iterable<Movie> getAllMoviesByMovieListId(@PathVariable Long movieList_id) {
        return movieService.findAllMoviesByMovieListId(movieList_id);
    }

}
