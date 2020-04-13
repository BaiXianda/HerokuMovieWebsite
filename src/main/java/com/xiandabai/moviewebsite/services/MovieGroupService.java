package com.xiandabai.moviewebsite.services;

import com.xiandabai.moviewebsite.domain.*;
import com.xiandabai.moviewebsite.exceptions.GroupIDException;
import com.xiandabai.moviewebsite.exceptions.UsernameAlreadyExistsException;
import com.xiandabai.moviewebsite.repositories.InvitationRepository;
import com.xiandabai.moviewebsite.repositories.MovieGroupRespository;
import com.xiandabai.moviewebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieGroupService {

    @Autowired
    private MovieGroupRespository movieGroupRespository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    public MovieGroup saveOrUpdateGroup(MovieGroup movieGroup, String username) {

        if(movieGroup.getId() != null) {
            MovieGroup existMovieGroup = movieGroupRespository.findByGroupID(movieGroup.getGroupID());

            if(existMovieGroup != null && !existMovieGroup.getModerator().equals(username)) {
                throw new GroupIDException("Group not found in your account");
            } else if(existMovieGroup == null) {
                throw new GroupIDException("Group not found in your account");
            }
        }

        try {
            User user = userRepository.findByUsername(username);

            movieGroup.setGroupID(movieGroup.getGroupID().toUpperCase());
            movieGroup.setModerator(username);
            movieGroup.getUsers().add(user);

            return movieGroupRespository.save(movieGroup);
        } catch (Exception e) {
            throw new GroupIDException("Group ID '" + movieGroup.getGroupID().toUpperCase() + "' already exists");
        }
    }

    public MovieGroup findByGroupID(String id) {
        MovieGroup movieGroup = movieGroupRespository.findByGroupID(id.toUpperCase());

        if(movieGroup == null) {
            throw new GroupIDException("Group ID '" + movieGroup.getGroupID().toUpperCase() + "' already exists");
        }

        return movieGroup;
    }


    public void deleteGroupByGroupId(String id) {
        MovieGroup movieGroup = findByGroupID(id);
        movieGroupRespository.delete(movieGroup);
    }

    public Iterable<MovieGroup> findAllGroups() {
        return movieGroupRespository.findAll();
    }

    public Iterable<MovieGroup> findGroups(String username) {

        User user = userRepository.findByUsername(username);

        return user.getMovieGroups();
    }

    public void addUser(Long invitation_id) {
        Invitation invitation = invitationRepository.getById(invitation_id);
        User user = invitation.getUser();
        MovieGroup movieGroup = movieGroupRespository.findByGroupID(invitation.getGroupId());
        movieGroup.getUsers().add(user);
        user.getMovieGroups().add(movieGroup);

        invitationRepository.delete(invitation);
        movieGroupRespository.save(movieGroup);
    }

    public void inviteUser(Invitation invitation, String owner) {
        User userToAdd = userRepository.findByUsername(invitation.getUsername());
        if(userToAdd == null) {
            throw new UsernameAlreadyExistsException("Username not found");
        }

        Iterable<Invitation> check = userToAdd.getInvitations();
        for (Invitation i: check) {
            if(i.getGroupId().equals(invitation.getGroupId())) {
                throw new UsernameAlreadyExistsException("Already send the invitation");
            }
        }

        Iterable<User> users = movieGroupRespository.findByGroupID(invitation.getGroupId()).getUsers();
        for(User u: users) {
            if(u.getUsername().equals(invitation.getUsername()))
                throw new UsernameAlreadyExistsException("User is already in the group");
        }

        User userWhoInvite = userRepository.findByUsername(owner);
        invitation.setInviterName(userWhoInvite.getFullName());
        invitation.setInviterEmail(userWhoInvite.getUsername());
        invitation.setUser(userToAdd);

        invitationRepository.save(invitation);
    }

    public ArrayList<Movie> searchMovies(String movieName, String groupID) {
        ArrayList<Movie> movieFind = new ArrayList<>();
        MovieGroup movieGroup = movieGroupRespository.findByGroupID(groupID);
        List<MovieList> movieLists = movieGroup.getMovieLists();

        for(MovieList list: movieLists) {
            List<Movie> movies = list.getMovies();
            for(Movie movie: movies) {
                if(movie.getName().toUpperCase().equals(movieName.toUpperCase()))
                    movieFind.add(movie);
            }
        }

        return movieFind;
    }

}
