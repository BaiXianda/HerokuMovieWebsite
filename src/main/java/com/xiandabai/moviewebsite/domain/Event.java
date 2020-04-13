package com.xiandabai.moviewebsite.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "must have a name")
    private String eventName;
    private String description;

    @NotNull(message = "must set eventTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "America/New_York")
    @Column(updatable = false)
    private Date eventTime;

    @NotNull(message = "must set eventTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "America/New_York")
    private Date voteStartTime;

    @NotNull(message = "must set eventTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "America/New_York")
    private Date voteEndTime;

    @NotNull(message = "must include a movie list for the event")
    @Column(updatable = false)
    private Long movieListId;
    @NotBlank(message = "movie group id is required")
    private String eventGroupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    @JsonIgnore
    private MovieGroup movieGroup;

    // String is username, Long is movie id
    private HashMap<String, Long> votes = new HashMap<String, Long>();
    // Long is movie id, Integer is vote counts
    private HashMap<Long, Integer> counts = new HashMap<Long, Integer>();

    public Event() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Date getVoteStartTime() {
        return voteStartTime;
    }

    public void setVoteStartTime(Date voteStartTime) {
        this.voteStartTime = voteStartTime;
    }

    public Date getVoteEndTime() {
        return voteEndTime;
    }

    public void setVoteEndTime(Date voteEndTime) {
        this.voteEndTime = voteEndTime;
    }

    public Long getMovieListId() {
        return movieListId;
    }

    public void setMovieListId(Long movieListId) {
        this.movieListId = movieListId;
    }

    public String getEventGroupId() {
        return eventGroupId;
    }

    public void setEventGroupId(String eventGroupId) {
        this.eventGroupId = eventGroupId;
    }

    public MovieGroup getMovieGroup() {
        return movieGroup;
    }

    public void setMovieGroup(MovieGroup movieGroup) {
        this.movieGroup = movieGroup;
    }

    public HashMap<String, Long> getVotes() {
        return votes;
    }

    public void setVotes(HashMap<String, Long> votes) {
        this.votes = votes;
    }

    public HashMap<Long, Integer> getCounts() {
        return counts;
    }

    public void setCounts(HashMap<Long, Integer> counts) {
        this.counts = counts;
    }

}
