package org.dailygreen.dailygreen.util.DAT;

import org.dailygreen.dailygreen.Postagens.EventoOrganizacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventoOrganizacaoDAT {
    private static final String FILE_PATH = "db_dailygreen/eventoOrganizacao.dat";

    public static List<EventoOrganizacao> lerLista() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<EventoOrganizacao>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void salvarLista(List<EventoOrganizacao> lista) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void adicionarEvento(EventoOrganizacao evento) {
        List<EventoOrganizacao> lista = lerLista();
        lista.add(evento);
        salvarLista(lista);
    }
}
