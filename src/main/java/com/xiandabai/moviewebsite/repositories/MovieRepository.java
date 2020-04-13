package com.xiandabai.moviewebsite.repositories;

import com.xiandabai.moviewebsite.domain.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Movie getById(Long id);

    @Override
    Iterable<Movie> findAll();

    Iterable<Movie> findByMovieList_id(Long movieList_id);
}
