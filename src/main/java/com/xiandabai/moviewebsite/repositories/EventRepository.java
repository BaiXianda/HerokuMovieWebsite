package com.xiandabai.moviewebsite.repositories;

import com.xiandabai.moviewebsite.domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    Event getById(Long id);

    Iterable<Event> findByEventGroupId(String eventGroupId);
}
