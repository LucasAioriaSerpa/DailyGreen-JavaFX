package org.dailygreen.dailygreen.Users.Organizacao.persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizacaoDAT {

    private static final String FILE_PATH = "db_organizacao.dat";

    public static boolean cadastrarOrganizacao(String email, String senha) {
        List<String[]> organizacoes = lerDados();

        for (String[] org : organizacoes) {
            if (org[0].equalsIgnoreCase(email)) {
                return false; // Já existe esse email cadastrado
            }
        }

        organizacoes.add(new String[]{email, senha});
        salvarDados(organizacoes);
        return true;
    }

    public static boolean fazerLogin(String email, String senha) {
        List<String[]> organizacoes = lerDados();
        for (String[] org : organizacoes) {
            if (org[0].equalsIgnoreCase(email) && org[1].equals(senha)) {
                return true; // Login correto
            }
        }
        return false; // Dados incorretos
    }

    private static List<String[]> lerDados() {
        List<String[]> lista = new ArrayList<>();
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            lista = (List<String[]>) input.readObject();
        } catch (Exception e) {
            lista = new ArrayList<>(); // Arquivo não existe ainda
        }
        return lista;
    }

    private static void salvarDados(List<String[]> lista) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            output.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
