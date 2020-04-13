package com.xiandabai.moviewebsite.controllers;

import com.xiandabai.moviewebsite.domain.Invitation;
import com.xiandabai.moviewebsite.domain.Movie;
import com.xiandabai.moviewebsite.domain.MovieGroup;
import com.xiandabai.moviewebsite.services.MovieGroupService;
import com.xiandabai.moviewebsite.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/group")
@CrossOrigin
public class MovieGroupController {

    @Autowired
    private MovieGroupService movieGroupService;

    @Autowired
    private ValidationErrorService validationErrorService;


    @PostMapping("")
    public ResponseEntity<?> createNewGroup(@Valid @RequestBody MovieGroup movieGroup, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if(errorMap != null)
            return errorMap;

        MovieGroup newMovieGroup = movieGroupService.saveOrUpdateGroup(movieGroup, principal.getName());
        return new ResponseEntity<MovieGroup>(newMovieGroup, HttpStatus.CREATED);
    }

    @GetMapping("/{groupID}")
    public ResponseEntity<?> getGroupByGroupID(@PathVariable String groupID) {
        MovieGroup movieGroup = movieGroupService.findByGroupID(groupID);

        return new ResponseEntity<MovieGroup>(movieGroup, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<MovieGroup> getAllGroups() {
        return movieGroupService.findAllGroups();
    }

    @DeleteMapping("/{groupID}")
    public ResponseEntity<?> deleteGroup(@PathVariable String groupID) {
        movieGroupService.deleteGroupByGroupId(groupID);
        return new ResponseEntity<String>("Group with ID: " + groupID + " is deleted", HttpStatus.OK);
    }

    @GetMapping("/groups")
    public Iterable<MovieGroup> getGroups(Principal principal) {

        return movieGroupService.findGroups(principal.getName());
    }

    @PostMapping("/addUser/{invitation_id}")
    public ResponseEntity<?> addNewUserToGroup(@PathVariable Long invitation_id) {
        movieGroupService.addUser(invitation_id);
        return new ResponseEntity<String>("The user is add to the group", HttpStatus.OK);
    }

    @PostMapping("/inviteUser")
    public ResponseEntity<?> inviteUserToGroup(@Valid @RequestBody Invitation invitation, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if(errorMap != null)
            return errorMap;

        movieGroupService.inviteUser(invitation, principal.getName());
        return new ResponseEntity<String>("Invitation is send", HttpStatus.OK);
    }

    @GetMapping("/searchMovies/{movie_name}/{group_id}")
    public ResponseEntity<?> searchMovies(@PathVariable String movie_name, @PathVariable String group_id) {
        ArrayList<Movie> movies = movieGroupService.searchMovies(movie_name, group_id);
        return new ResponseEntity<ArrayList<Movie>>(movies, HttpStatus.OK);
    }

}
