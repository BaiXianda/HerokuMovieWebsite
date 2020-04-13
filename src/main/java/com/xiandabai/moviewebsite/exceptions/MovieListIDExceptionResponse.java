package com.xiandabai.moviewebsite.exceptions;

public class MovieListIDExceptionResponse {

    private String movieListId;

    public MovieListIDExceptionResponse(String movieListId) {
        this.movieListId = movieListId;
    }

    public String getMovieListId() {
        return movieListId;
    }

    public void setMovieListId(String movieListId) {
        this.movieListId = movieListId;
    }
}
