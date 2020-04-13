package com.xiandabai.moviewebsite.services;

import com.xiandabai.moviewebsite.domain.Invitation;
import com.xiandabai.moviewebsite.domain.User;
import com.xiandabai.moviewebsite.exceptions.UsernameAlreadyExistsException;
import com.xiandabai.moviewebsite.repositories.InvitationRepository;
import com.xiandabai.moviewebsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private InvitationRepository invitationRepository;

    public User saveUser(User newUser) {
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            newUser.setUsername(newUser.getUsername());
            newUser.setConfirmPassword("");

            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() +"' already exists");
        }
    }

    public void deleteInvitation(Long id) {
        Invitation invitation = invitationRepository.getById(id);
        invitationRepository.delete(invitation);
    }

}
