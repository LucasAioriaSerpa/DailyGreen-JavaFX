package org.dailygreen.dailygreen.repository;

import org.dailygreen.dailygreen.model.user.types.Administrator;

import java.util.List;

public interface IAdminRepository {
    List<Administrator> findAll();
    Administrator findByEmail(String email);
    boolean save(Administrator admin);
    boolean deleteByEmail(String email);
    boolean validateLogin(String email, String password);
}
