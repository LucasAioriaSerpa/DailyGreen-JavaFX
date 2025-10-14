package org.dailygreen.dailygreen.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dailygreen.dailygreen.model.post.Comment;
import org.dailygreen.dailygreen.repository.ICommentRepository;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CommentJsonRepository implements ICommentRepository {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/comments.json";
    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    public CommentJsonRepository() { inicializarArquivo(); }

    @Override
    public void save(Comment comment) {
        List<Comment> comments = readComments();
        comments.add(comment);
        writeComments(comments);
    }

    @Override
    public void update(Comment comment) {
        List<Comment> comments = readComments();
        for (int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i);
            if (Objects.equals(c.getAutorEmail(), comment.getAutorEmail()) && c.getIdPost() == comment.getIdPost()) {
                comments.set(i, comment);
                writeComments(comments);
                return;
            }
        }
    }

    @Override
    public void delete(Comment comment) {
        List<Comment> comments = readComments();
        comments.removeIf(c -> Objects.equals(c.getAutorEmail(), comment.getAutorEmail()) && c.getIdPost() == comment.getIdPost());
        writeComments(comments);
    }

    @Override
    public Optional<Comment> findByPost(long postId) {
        List<Comment> comments = readComments();
        return comments.stream().filter(c -> c.getIdPost() == postId).max(Comparator.comparing(Comment::getData));
    }

    @Override
    public List<Comment> findAllByPost(long postId) { return readComments().stream().filter(c -> c.getIdPost() == postId).collect(Collectors.toList()); }

    @Override
    public List<Comment> findAll() { return readComments(); }

    private List<Comment> readComments() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try { return mapper.readValue(file, new TypeReference<List<Comment>>() {}); }
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeComments(List<Comment> comments) {
        try { mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), comments); }
        catch (IOException e) { e.printStackTrace(); }
    }

    private void persist(List<Comment> comments) {
        try { mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), comments); }
        catch (IOException e) { throw new RuntimeException("Erro ao salvar comentários: " + e.getMessage(), e); }
    }

    private void inicializarArquivo() {
        File file = new File(FILE_PATH);
        if (!file.getParentFile().exists()) { file.getParentFile().mkdirs(); }
        if (!file.exists()) { persist(new ArrayList<>()); }
    }

}
