package org.dailygreen.dailygreen.repository.impl;

import com.google.gson.reflect.TypeToken;
import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.repository.IPostRepository;

import java.lang.reflect.Type;
import java.util.List;

public class PostJsonRepository extends BaseJsonRepository<Post> implements IPostRepository {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/posts.json";
    private static final String ID_FILE_PATH = "src/main/resources/db_dailygreen/posts_last_id.txt";
    private static final Type LIST_TYPE = new TypeToken<List<Post>>() {}.getType();

    public PostJsonRepository() { super(FILE_PATH, ID_FILE_PATH, LIST_TYPE); }

    @Override public List<Post> findAll() { return readAll(); }

    @Override public Post findById(long id) { return readAll().stream().filter(p -> p.getID() == id).findFirst().orElse(null); }

    @Override
    public boolean save(Post post) {
        List<Post> posts = readAll();
        if (post.getID() == 0) post.setID(generateNewId());
        post.setID(generateNewId());
        posts.add(post);
        saveAll(posts);
        return true;
    }

    @Override
    public boolean update(Post post) {
        List<Post> posts = readAll();
        for (int i = 0; i < posts.size(); i++) {
            Post p = posts.get(i);
            if (p.getID() == post.getID()) {
                posts.set(i, post);
                saveAll(posts);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        List<Post> posts = readAll();
        boolean removed = posts.removeIf(p -> p.getID() == id);
        if (removed) saveAll(posts);
        return removed;
    }

}
