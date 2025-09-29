package org.dailygreen.dailygreen.util.DAT;

import org.dailygreen.dailygreen.Postagens.Post;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class PostDAT {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/postagens.dat";
    private static final String ARQUIVO_ID = "src/main/resources/db_dailygreen/postagens_ultimo_id.dat";
    private static AtomicLong ultimoId;
    static { carregarUltimoId(); }

    private static void carregarUltimoId() {
        File arquivo = new File(ARQUIVO_ID);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                ultimoId = new AtomicLong(ois.readLong());
            } catch (IOException e) {
                ultimoId = new AtomicLong(0);
            }
        } else {
            ultimoId = new AtomicLong(0);
        }
    }

    private static void salvarUltimoId() {
        File arquivo = new File(ARQUIVO_ID);
        try {
            if (!arquivo.getParentFile().exists()) {
                arquivo.getParentFile().mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
                oos.writeLong(ultimoId.get());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar último ID: " + e.getMessage());
        }
    }

    public static long gerarNovoId() {
        long novoId = ultimoId.incrementAndGet();
        salvarUltimoId();
        return novoId;
    }

    // ? CREATE
    public static void adicionarPost(Post post) {
        List<Post> posts = lerLista();
        posts.add(post);
        salvarLista(posts);
    }

    // ? READ
    public static List<Post> lerLista() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Post>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    // ? UPDATE
    public static boolean atualizarPost(long id, Post novoPost) {
        List<Post> posts = lerLista();
        Optional<Post> postOpt = posts.stream().filter(p -> p.getID() == id).findFirst();
        if (postOpt.isPresent()) {
            int idx = posts.indexOf(postOpt.get());
            posts.set(idx, novoPost);
            salvarLista(posts);
            return true;
        }
        return false;
    }

    // ? DELETE
    public static boolean removerPost(long id) {
        List<Post> posts = lerLista();
        boolean removed = posts.removeIf(p -> p.getID() == id);
        if (removed) salvarLista(posts);
        return removed;
    }

    // ? SAVE
    private static void salvarLista(List<Post> posts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(posts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}