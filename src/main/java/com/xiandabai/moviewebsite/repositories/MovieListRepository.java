package com.xiandabai.moviewebsite.repositories;

import com.xiandabai.moviewebsite.domain.MovieList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieListRepository extends CrudRepository<MovieList, Long> {

    MovieList getById(Long id);

    Iterable<MovieList> findByMovieGroupID(String movieGroupID);
}
