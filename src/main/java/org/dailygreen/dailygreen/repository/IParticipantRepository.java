package org.dailygreen.dailygreen.repository;

import org.dailygreen.dailygreen.model.user.types.Participant;

import java.util.List;

public interface IParticipantRepository {
    List<Participant> findAll() throws Exception;
    Participant findByEmail(String email) throws Exception;
    boolean save(Participant participant) throws Exception;
    boolean deleteByEmail(String Email) throws Exception;
    boolean validateLogin(String email, String password) throws Exception;
}
