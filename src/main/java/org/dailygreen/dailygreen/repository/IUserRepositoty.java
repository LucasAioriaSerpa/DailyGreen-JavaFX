package org.dailygreen.dailygreen.repository;

import org.dailygreen.dailygreen.model.user.AbstractUser;

import java.util.List;

public interface IUserRepositoty {
    List<AbstractUser> findAll();
    AbstractUser findByEmail(String email);
    boolean save(AbstractUser abstractUser);
    boolean deleteByEmail(String email);
}
