package org.dailygreen.dailygreen.repository;

import org.dailygreen.dailygreen.model.post.Reaction;

import java.util.List;
import java.util.Optional;

public interface IReactionRepository {
    void saveOrToggle(Reaction reaction);
    void delete(String autorEmail, long idPost);
    List<Reaction> findByPost(long idPost);
    long countByTarget(long idPost, String tipo);
    Optional<String> findUserReactionType(String autorEmail, long idPost);
    List<Reaction> findAll();
}
