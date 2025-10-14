package org.dailygreen.dailygreen.repository;

import org.dailygreen.dailygreen.model.moderation.Report;
import org.dailygreen.dailygreen.model.post.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentRepository {
    void save(Comment comment);
    void update(Comment comment);
    void delete(Comment comment);
    Optional<Comment> findByPost(long postId);
    List<Comment> findAllByPost(long postId);
    List<Comment> findAll();
}
