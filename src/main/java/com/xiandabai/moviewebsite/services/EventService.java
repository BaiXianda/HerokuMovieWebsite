package com.xiandabai.moviewebsite.services;

import com.xiandabai.moviewebsite.domain.*;
import com.xiandabai.moviewebsite.exceptions.MovieListIDException;
import com.xiandabai.moviewebsite.repositories.EventRepository;
import com.xiandabai.moviewebsite.repositories.MovieListRepository;
import com.xiandabai.moviewebsite.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MovieListRepository movieListRepository;

    @Autowired
    private MovieGroupService movieGroupService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EventNotificationService eventNotificationService;

    public Event saveOrUpdateEvent(Event event) {
        if(event.getId() == null) {
            MovieList movieList = movieListRepository.getById(event.getMovieListId());
            if (movieList == null) {
                throw new MovieListIDException("there is no movieList with this ID exist in your group");
            } else if (!movieList.getMovieGroupID().equals(event.getEventGroupId())) {
                throw new MovieListIDException("there is no movieList with this ID exist in your group");
            } else if (movieList.getMovies().size() < 3) {
                throw new MovieListIDException("You need to choose a movie list with at least 3 movies");
            }

            MovieGroup movieGroup = movieGroupService.findByGroupID(event.getEventGroupId());
            Set<User> users = movieGroup.getUsers();

            for(User user: users) {
                eventNotificationService.createNotification(movieGroup.getGroupName(), user.getUsername(), event.getEventName());
            }

        } else {
            Event newEvent = eventRepository.getById(event.getId());
            newEvent.setEventName(event.getEventName());
            newEvent.setDescription(event.getDescription());
            newEvent.setVoteStartTime(event.getVoteStartTime());
            newEvent.setVoteEndTime(event.getVoteEndTime());
        }
        event.setMovieGroup(movieGroupService.findByGroupID(event.getEventGroupId()));

        return eventRepository.save(event);
    }

    public Iterable<Event> getAllEventByMovieGroupId(String movieGroupId) {
        return eventRepository.findByEventGroupId(movieGroupId);
    }

    public Iterable<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    public Event getEvent(Long id) {
        return eventRepository.getById(id);
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.getById(id);
        eventRepository.delete(event);
    }

    public Event vote(Long eventId, Long movieId, String username) {
        Event event = eventRepository.getById(eventId);

        HashMap<String, Long> votes = event.getVotes();
        HashMap<Long, Integer> counts = event.getCounts();

        if(votes.containsKey(username)) {
            Long movieVoted = votes.get(username);
            counts.put(movieVoted, counts.getOrDefault(movieVoted, 0) - 1);
        }

        votes.put(username, movieId);
        counts.put(movieId, counts.getOrDefault(movieId, 0) + 1);
        event.setVotes(votes);
        event.setCounts(counts);

        return eventRepository.save(event);
    }

    public Movie getWinner(Long eventId) {
        Event event = eventRepository.getById(eventId);

        HashMap<Long, Integer> counts = event.getCounts();
        int max = Integer.MIN_VALUE;
        Long winner = Long.valueOf(1);

        for(Long id: counts.keySet()) {
            if(counts.getOrDefault(id, 0) > max)
                winner = id;
        }

        return movieRepository.getById(winner);
    }

}
