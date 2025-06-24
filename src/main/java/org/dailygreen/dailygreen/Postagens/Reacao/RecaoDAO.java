package org.dailygreen.dailygreen.Postagens.Reacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por persistir e recuperar reações das postagens.
 * Utiliza serialização para armazenar os dados em arquivo.
 */
public class RecaoDAO {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/reacoes.dat";
    /**
     * Lê todas as reações salvas no arquivo.
     * @return Lista de reações, vazia se não houver ou em caso de erro.
     */
    public static List<Reacao> lerReacoes() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Reacao>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    /**
     * Salva uma nova reação no arquivo, removendo a anterior do mesmo usuário na mesma postagem.
     * Se a reação já existir (mesmo tipo), remove a reação (toggle).
     *
     * @param reacao Reação a ser salva.
     */
    public static void salvarOuRemoverReacao(Reacao reacao) {
        List<Reacao> reacoes = lerReacoes();
        boolean jaExiste = reacoes.removeIf(r ->
                r.getIdPost() == reacao.getIdPost() &&
                r.getAutorEmail().equals(reacao.getAutorEmail()) &&
                r.getTipo().equals(reacao.getTipo())
        );
        if (!jaExiste) {
            reacoes.removeIf(r ->
                r.getIdPost() == reacao.getIdPost() &&
                r.getAutorEmail().equals(reacao.getAutorEmail())
            );
            reacoes.add(reacao);
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(reacoes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Remove a reação do usuário para um post (qualquer tipo).
     */
    public static void removerReacao(String autorEmail, long idPost) {
        List<Reacao> reacoes = lerReacoes();
        reacoes.removeIf(r -> r.getIdPost() == idPost && r.getAutorEmail().equals(autorEmail));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(reacoes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Busca todas as reações associadas a uma postagem específica.
     * @param idPost ID da postagem.
     * @return Lista de reações da postagem.
     */
    public static List<Reacao> buscarPorPost(long idPost) {
        return lerReacoes().stream()
                .filter(r -> r.getIdPost() == idPost)
                .collect(Collectors.toList());
    }
    /**
     * Conta quantas reações de um tipo específico existem para uma postagem.
     * @param idPost ID da postagem.
     * @param tipo Tipo da reação (ex: "gostei").
     * @return Quantidade de reações do tipo.
     */
    public static long contarPorTipo(long idPost, String tipo) {
        return buscarPorPost(idPost).stream()
                .filter(r -> r.getTipo().equals(tipo))
                .count();
    }
    /**
     * Retorna o tipo de reação do usuário para um post, ou null se não houver.
     */
    public static String buscarTipoReacaoUsuario(String autorEmail, long idPost) {
        return lerReacoes().stream()
                .filter(r -> r.getIdPost() == idPost && r.getAutorEmail().equals(autorEmail))
                .map(Reacao::getTipo)
                .findFirst()
                .orElse(null);
    }
}
