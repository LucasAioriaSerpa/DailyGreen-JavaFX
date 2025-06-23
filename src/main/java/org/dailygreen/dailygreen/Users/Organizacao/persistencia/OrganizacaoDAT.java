package org.dailygreen.dailygreen.Users.Organizacao.persistencia;

import org.dailygreen.dailygreen.Users.Organizacao.model.Organizador;

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
    public static List<Organizador> lerOrganizadores() {
        File file = new File("resources/db_dailygreen/organizadores.dat");
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Organizador>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void salvarOrganizadores(List<Organizador> lista) {
        File file = new File("resources/db_dailygreen/organizadores.dat");
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
