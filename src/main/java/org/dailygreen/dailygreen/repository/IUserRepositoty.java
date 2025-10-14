package org.dailygreen.dailygreen.repository;

import org.dailygreen.dailygreen.model.user.User;

import java.util.List;

public interface IUserRepositoty {
    List<User> findAll();
    User findByEmail(String email);
    boolean save(User user);
    boolean update(User user);
    boolean deleteByEmail(String email);
    boolean validateLogin(String email);
}
