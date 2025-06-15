package org.dailygreen.dailygreen.Users.Administrador.dao;

import org.dailygreen.dailygreen.Users.Administrador.models.Administrador;
import org.dailygreen.dailygreen.Users.Administrador.utils.FileManager;
import org.dailygreen.dailygreen.util.Criptografia;

import javax.crypto.SecretKey;
import java.util.List;
import java.io.*;
import java.util.concurrent.atomic.AtomicLong;


public class AdmDAO {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/adm.dat";
    private static final String ARQUIVO_ID = "src/main/resources/db_dailygreen/adm_ultimo_id.dat";
    private static AtomicLong ultimoId;

    static {
        carregarUltimoId();
    }

    private static void carregarUltimoId() {
        File arquivo = new File(ARQUIVO_ID);
        if (arquivo.exists()) {
            try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(arquivo))) {
                ultimoId = new AtomicLong(OIS.readLong());
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
            if (!arquivo.getParentFile().exists()) {arquivo.getParentFile().mkdirs();}
            try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(arquivo))) {OOS.writeLong(ultimoId.get());}
        } catch (IOException e) {
            System.err.println("Erro ao salvar último ID: " + e.getMessage());
        }
    }

    private static long gerarNovoId() {
        long novoId = ultimoId.incrementAndGet();
        salvarUltimoId();
        return novoId;
    }

    public static boolean validarLogin(String email, String password) {
        List<Administrador> lista = FileManager.carregar(FILE_PATH);
        SecretKey chave;
        try {chave = Criptografia.lerChaveDeArquivo(Criptografia.getARQUIVO_CHAVE());} catch (IOException e) {throw new RuntimeException(e);}
        return lista.stream().anyMatch(a -> {
            try {
                return a.getEmail().equals(email) && Criptografia.descriptografar(a.getPassword(), chave).equals(password);
            } catch (Exception e) {throw new RuntimeException(e);}
        });
    }

    public static boolean salvarNovoAdm(String email, String password) throws Exception {
        List<Administrador> lista = FileManager.carregar(FILE_PATH);
        if (lista.stream().anyMatch(a -> a.getEmail().equals(email))) {return false;}
        SecretKey chave = Criptografia.lerChaveDeArquivo(Criptografia.getARQUIVO_CHAVE());
        Administrador novoAdm = new Administrador(email, Criptografia.criptografar(password, chave));
        novoAdm.setID(gerarNovoId());
        lista.add(novoAdm);
        FileManager.salvar(FILE_PATH, lista);
        return true;
    }
}