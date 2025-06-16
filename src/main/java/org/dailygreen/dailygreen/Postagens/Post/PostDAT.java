package org.dailygreen.dailygreen.Postagens.Post;

import java.io.*;
import java.util.ArrayList;

public class PostDAT {
    private static final String ARQUIVO_POST = "src/main/resources/posts.dat";

    public static void salvarPost(Post post) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_POST))) {
            ArrayList<Post> posts = carregarPosts();
            posts.add(post);
            oos.writeObject(posts);
        } catch (IOException e) {
            System.err.println("Erro ao salvar post: " + e.getMessage());
        }
    }

    public static ArrayList<Post> carregarPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        File arquivo = new File(ARQUIVO_POST);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                posts = (ArrayList<Post>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar posts: " + e.getMessage());
            }
        }
        return posts;
    }

    public static void atualizarPost(Post postAtualizado) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_POST))) {
            ArrayList<Post> posts = carregarPosts();
            for (int i = 0; i < posts.size(); i++) {
                if (posts.get(i).getId_autor() == postAtualizado.getId_autor()) {
                    posts.set(i, postAtualizado);
                    break;
                }
            }
            oos.writeObject(posts);
        } catch (IOException e) {
            System.err.println("Erro ao atualizar post: " + e.getMessage());
        }
    }

    public static void deletarPost(int idAutor) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_POST))) {
            ArrayList<Post> posts = carregarPosts();
            posts.removeIf(post -> post.getId_autor() == idAutor);
            oos.writeObject(posts);
        } catch (IOException e) {
            System.err.println("Erro ao deletar post: " + e.getMessage());
        }
    }
}