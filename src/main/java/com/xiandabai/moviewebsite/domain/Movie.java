package com.xiandabai.moviewebsite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Must have a name for the movie")
    private String name;
    private String description;
    @NotBlank(message = "Must have a trailer link")
    private String trailerLink;
    @NotBlank(message = "Must have a review link")
    private String reviewLink;

    @Column(updatable = false)
    private Long list_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieList_id", nullable = false)
    @JsonIgnore
    private MovieList movieList;

    public Movie() {

    }

    public Movie(String name, String description, String trailerLink, String reviewLink) {
        this.name = name;
        this.description = description;
        this.trailerLink = trailerLink;
        this.reviewLink = reviewLink;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getReviewLink() {
        return reviewLink;
    }

    public void setReviewLink(String reviewLink) {
        this.reviewLink = reviewLink;
    }

    public MovieList getMovieList() {
        return movieList;
    }

    public void setMovieList(MovieList movieList) {
        this.movieList = movieList;
    }

    public Long getMovieList_id() {
        return list_id;
    }

    public void setMovieList_id(Long movieList_id) {
        this.list_id = movieList_id;
    }
}
