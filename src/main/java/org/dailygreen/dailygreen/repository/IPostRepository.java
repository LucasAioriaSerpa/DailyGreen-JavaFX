package org.dailygreen.dailygreen.repository;

import org.dailygreen.dailygreen.model.post.Post;

import java.util.List;

public interface IPostRepository {
    List<Post> findAll();
    Post findById(long id);
    boolean save(Post post);
    boolean update(Post post);
    boolean deleteById(Long id);
}
