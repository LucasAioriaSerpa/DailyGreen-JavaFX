package org.dailygreen.dailygreen.util.DAO;

import org.dailygreen.dailygreen.Postagens.Comentario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/comentarios.dat";
    public static List<Comentario> lerComentarios() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Comentario>) OIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public static void salvarComentario(Comentario comentario) {
        List<Comentario> comentarios = lerComentarios();
        comentarios.add(comentario);
        try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            OOS.writeObject(comentarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Comentario> buscarPorPost(long idPost) {
        List<Comentario> todos = lerComentarios();
        List<Comentario> filtrados = new ArrayList<>();
        for (Comentario c : todos) {
            if (c.getIdPost() == idPost) filtrados.add(c);
        }
        return filtrados;
    }
}
