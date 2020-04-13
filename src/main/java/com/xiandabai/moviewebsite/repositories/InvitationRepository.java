package com.xiandabai.moviewebsite.repositories;

import com.xiandabai.moviewebsite.domain.Invitation;
import com.xiandabai.moviewebsite.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends CrudRepository<Invitation, Long> {
    Invitation getById(Long id);
}
