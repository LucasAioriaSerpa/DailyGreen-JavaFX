package org.dailygreen.dailygreen.Users.Administrador.dao;

import org.dailygreen.dailygreen.Users.Administrador.models.Administrador;
import org.dailygreen.dailygreen.Users.Administrador.utils.FileManager;
import org.dailygreen.dailygreen.Users.User;
import org.dailygreen.dailygreen.Users.util.DATuser;
import org.dailygreen.dailygreen.util.Criptografia;

import java.util.List;
import java.io.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AdmDAO {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/adm.dat";
    private static final String ARQUIVO_ID = "src/main/resources/db_dailygreen/adm_ultimo_id.dat";
    private static AtomicLong ultimoId;
    private static final Logger LOGGER = Logger.getLogger(AdmDAO.class.getName());

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

    public static boolean validarLogin(String email, String password) {
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            LOGGER.warning("Tentativa de login com credenciais vazias");
            return false;
        }
        LOGGER.info("Iniciando validação de login para email: " + email);
        List<Administrador> lista = FileManager.carregar(FILE_PATH);
        LOGGER.info("Lista de administradores carregada. Tamanho: " + lista.size());

        return lista.stream()
                .filter(admin -> admin.getEmail().equals(email))
                .findFirst()
                .map(admin -> {
                    try {
                        String senhaArmazenada = admin.getPassword();
                        String senhaDescriptografada = Criptografia.descriptografar(senhaArmazenada, Criptografia.lerChaveDeArquivo(Criptografia.getARQUIVO_CHAVE()));
                        LOGGER.info("Verificando senha para administrador: " + admin.getEmail());
                        if (senhaArmazenada == null || senhaArmazenada.trim().isEmpty()) {
                            LOGGER.warning("Senha armazenada está vazia para o email: " + email);
                            return false;
                        }
                        boolean resultado = senhaDescriptografada.equals(password);
                        LOGGER.info("Resultado da validação: " + resultado);
                        if (resultado) {
                            User user = DATuser.getUser();
                            user.setAccountAdministrador(admin);
                            DATuser.setUser(user);
                        }
                        return resultado;
                    } catch (Exception e) {
                        LOGGER.log(Level.WARNING, "Erro ao validar senha para email: " + email, e);
                        return false;
                    }
                })
                .orElse(false);
    }

    public static boolean salvarNovoAdm(String email, String password) throws Exception {
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            LOGGER.warning("Tentativa de criar administrador com dados inválidos");
            return false;
        }

        LOGGER.info("Iniciando processo de criação de novo administrador com email: " + email);
        List<Administrador> lista = FileManager.carregar(FILE_PATH);
        LOGGER.info("Lista de administradores carregada. Tamanho atual: " + lista.size());

        if (lista.stream().anyMatch(a -> a.getEmail().equals(email))) {
            LOGGER.warning("Email já existente: " + email);
            return false;
        }

        try {
            String senhaCriptografada = Criptografia.criptografar(password, Criptografia.lerChaveDeArquivo(Criptografia.getARQUIVO_CHAVE()));
            Administrador novoAdm = new Administrador(email, senhaCriptografada);
            long novoId = gerarNovoId();
            LOGGER.info("Novo ID gerado: " + novoId);
            novoAdm.setID(novoId);
            lista.add(novoAdm);
            FileManager.salvar(FILE_PATH, lista);
            LOGGER.info("Novo administrador criado com sucesso. ID: " + novoId);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar novo administrador com email: " + email, e);
            throw e;
        }
    }
}