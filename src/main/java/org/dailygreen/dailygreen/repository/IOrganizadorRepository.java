package org.dailygreen.dailygreen.repository;

import org.dailygreen.dailygreen.model.user.types.Organizator;

import java.util.List;

public interface IOrganizadorRepository {
    List<Organizator> findAll();
    Organizator findByEmail(String email);
    boolean save(Organizator organizator);
    boolean deleteByEmail(String email);
    boolean validateLogin(String email, String password);
}
