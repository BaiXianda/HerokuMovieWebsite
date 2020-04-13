package com.xiandabai.moviewebsite.repositories;

import com.xiandabai.moviewebsite.domain.EventNotification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventNotificationRepository extends CrudRepository<EventNotification, Long> {
    EventNotification getById(Long id);
}
