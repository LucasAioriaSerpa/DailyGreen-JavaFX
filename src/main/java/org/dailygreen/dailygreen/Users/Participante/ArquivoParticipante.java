package org.dailygreen.dailygreen.Users.Participante;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ArquivoParticipante {
    private static final String CAMINHO_ARQUIVO = "src/main/resources/db_dailygreen/participante.dat";
    private static final String ARQUIVO_ID = "src/main/resources/db_dailygreen/ultimo_id.dat";
    private static AtomicLong ultimoId;
    static {carregarUltimoId();}

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
            Files.createDirectories(arquivo.getParentFile().toPath());
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

    private static void garantirArquivo() throws IOException {
        Files.createDirectories(Paths.get(CAMINHO_ARQUIVO).getParent());
        if (!Files.exists(Paths.get(CAMINHO_ARQUIVO))) {
            Files.createFile(Paths.get(CAMINHO_ARQUIVO));
            salvarLista(new ArrayList<>());
        }
    }

    public static void salvarLista(ArrayList<Participante> participantes) {
        try {
            garantirArquivo();
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(CAMINHO_ARQUIVO)))) {
                oos.writeObject(participantes);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    public static ArrayList<Participante> lerLista() {
        ArrayList<Participante> lista = new ArrayList<>();
        try {
            garantirArquivo();
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(CAMINHO_ARQUIVO)))) {lista = (ArrayList<Participante>) ois.readObject();}
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    public static void adicionarParticipante(Participante participante) {
        List<Participante> participantes = lerLista();
        if (participantes.stream().anyMatch(p -> p.getEmail().equals(participante.getEmail()))) {throw new IllegalArgumentException("Já existe um participante com este email");}
        participante.setID(gerarNovoId());
        participantes.add(participante);
        salvarLista(new ArrayList<>(participantes));
    }
}