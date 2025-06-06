package org.dailygreen.dailygreen.Users.Participante;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ArquivoParticipante {
    private static final String CAMINHO_ARQUIVO = "participante.dat"; // Nome consistente

    public ArquivoParticipante() {
    }

    public static void salvarLista(ArrayList<Participante> participantes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(CAMINHO_ARQUIVO))) {

            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }

            oos.writeObject(participantes);
            System.out.println("Lista de participantes salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    public static ArrayList<Participante> lerLista() {
        ArrayList<Participante> lista = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(CAMINHO_ARQUIVO))) {

            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                lista = (ArrayList<Participante>) ois.readObject();
            }
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }

        return lista;
    }

    public static void adicionarParticipante(Participante novaParticipante) {
        ArrayList<Participante> participantes = lerLista();
        participantes.add(novaParticipante);
        salvarLista(participantes);
    }
}
