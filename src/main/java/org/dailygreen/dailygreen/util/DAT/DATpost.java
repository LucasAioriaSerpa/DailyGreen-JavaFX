package org.dailygreen.dailygreen.util.DAT;

import org.dailygreen.dailygreen.Postagens.Post;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DATpost {
    private static final String CAMINHO_ARQUIVO = "src/main/resources/db_dailygreen/posts.dat";
    private static final String ARQUIVO_ID = "src/main/resources/db_dailygreen/posts_ultimo_id.dat";
    private static final Logger LOGGER = Logger.getLogger(DATpost.class.getName());
    private static AtomicLong ultimoId;

    static {
        carregarUltimoId();
    }

    private static void carregarUltimoId() {
        File arquivo = new File(ARQUIVO_ID);
        try {
            if (arquivo.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                    ultimoId = new AtomicLong(ois.readLong());
                    LOGGER.info("Último ID carregado com sucesso: " + ultimoId.get());
                }
            } else {
                ultimoId = new AtomicLong(0);
                LOGGER.info("Arquivo de ID não existe, iniciando com 0");
            }
        } catch (IOException e) {
            ultimoId = new AtomicLong(0);
            LOGGER.log(Level.WARNING, "Erro ao carregar último ID", e);
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
                LOGGER.info("Último ID salvo com sucesso: " + ultimoId.get());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar último ID", e);
        }
    }

    private static synchronized long gerarNovoId() {
        long novoId = ultimoId.incrementAndGet();
        salvarUltimoId();
        return novoId;
    }

    private static void garantirArquivo() throws IOException {
        Files.createDirectories(Paths.get(CAMINHO_ARQUIVO).getParent());
        if (!Files.exists(Paths.get(CAMINHO_ARQUIVO))) {
            Files.createFile(Paths.get(CAMINHO_ARQUIVO));
            salvarLista(new ArrayList<>());
        }
    }

    public static void salvarLista(List<Post> posts) {
        try {
            garantirArquivo();
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(CAMINHO_ARQUIVO)))) {
                oos.writeObject(posts);
                LOGGER.info("Lista de posts salva com sucesso");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar lista de posts", e);
        }
    }

    public static ArrayList<Post> lerLista() {
        ArrayList<Post> lista = new ArrayList<>();
        try {
            garantirArquivo();
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(CAMINHO_ARQUIVO)))) {
                lista = (ArrayList<Post>) ois.readObject();
                LOGGER.info("Lista de posts carregada com sucesso");
            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "Erro ao ler lista de posts", e);
        }
        return lista;
    }

    public static void adicionarPost(Post post) {
        ArrayList<Post> posts = lerLista();
        post.setID(gerarNovoId());
        posts.add(post);
        salvarLista(posts);
        LOGGER.info("Post adicionado com sucesso. ID: " + post.getID());
    }

    public static void atualizarPost(long id, Post novoPost) {
        List<Post> posts = lerLista();
        Optional<Post> postOpt = posts.stream().filter(p -> p.getID() == id).findFirst();
        if (postOpt.isPresent()) {
            int idx = posts.indexOf(postOpt.get());
            posts.set(idx, novoPost);
            salvarLista(posts);
        }
    }

    public static void removerPost(long id) {
        ArrayList<Post> posts = lerLista();
        if (posts.removeIf(post -> post.getID() == id)) {
            salvarLista(posts);
            LOGGER.info("Post removido com sucesso. ID: " + id);
        } else {
            LOGGER.warning("Post não encontrado para remoção. ID: " + id);
        }
    }

    public static Post buscarPorId(long id) {
        return lerLista().stream()
                .filter(post -> post.getID() == id)
                .findFirst()
                .orElse(null);
    }
}