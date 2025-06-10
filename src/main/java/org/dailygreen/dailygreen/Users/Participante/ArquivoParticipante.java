package org.dailygreen.dailygreen.Users.Participante;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ArquivoParticipante {
    private static final String CAMINHO_ARQUIVO = "db_dailygreen/participante.dat";




    private static void garantirArquivo() throws IOException {
        File arq = new File(CAMINHO_ARQUIVO);

        if (!arq.exists()) {

            File pasta = arq.getParentFile();
            if (pasta != null && !pasta.exists()) {
                pasta.mkdirs();
            }

            arq.createNewFile();

            salvarLista(new ArrayList<>());
        }
    }

    public static void salvarLista(ArrayList<Participante> participantes) {
        try {
            garantirArquivo();

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
                oos.writeObject(participantes);
                System.out.println("Lista de participantes salva com sucesso.");
            }

        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    public static ArrayList<Participante> lerLista() {
        ArrayList<Participante> lista = new ArrayList<>();

        try {
            garantirArquivo();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO))) {
                lista = (ArrayList<Participante>) ois.readObject();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }

        return lista;
    }

    public static void adicionarParticipante(Participante novaParticipante) {
        ArrayList<Participante> participantes = lerLista();

        // participante com mesmo email
        boolean existe = participantes.stream()
                .anyMatch(p -> p.getEmail().equals(novaParticipante.getEmail()));

        if (existe) {
            throw new IllegalArgumentException("Já existe um participante com este email");
        }

        participantes.add(novaParticipante);
        salvarLista(participantes);
    }
}