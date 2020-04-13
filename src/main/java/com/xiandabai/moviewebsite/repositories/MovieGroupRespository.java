package com.xiandabai.moviewebsite.repositories;

import com.xiandabai.moviewebsite.domain.MovieGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieGroupRespository extends CrudRepository<MovieGroup, Long> {
    MovieGroup findByGroupID(String groupID);

}
