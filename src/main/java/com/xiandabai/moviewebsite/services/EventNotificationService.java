package com.xiandabai.moviewebsite.services;

import com.xiandabai.moviewebsite.domain.EventNotification;
import com.xiandabai.moviewebsite.domain.User;
import com.xiandabai.moviewebsite.repositories.EventNotificationRepository;
import com.xiandabai.moviewebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventNotificationService {

    @Autowired
    private EventNotificationRepository eventNotificationRepository;

    @Autowired
    private UserRepository userRepository;

    public void createNotification(String groupName, String username, String eventName) {
        EventNotification eventNotification = new EventNotification();
        User user = userRepository.findByUsername(username);
        eventNotification.setGroupName(groupName);
        eventNotification.setUser(user);
        eventNotification.setEventName(eventName);

        eventNotificationRepository.save(eventNotification);
    }

    public void deleteNotification(Long id) {
        EventNotification eventNotification = eventNotificationRepository.getById(id);
        eventNotificationRepository.delete(eventNotification);
    }
}
