package org.dailygreen.dailygreen.Users.Administrador.dao;

import org.dailygreen.dailygreen.Users.Administrador.models.Administrador;
import org.dailygreen.dailygreen.Users.Administrador.utils.FileManager;
import org.dailygreen.dailygreen.util.Criptografia;

import java.util.List;


public class AdmDAO {
    private static final String FILE_PATH = "adm.dat";

    public static boolean validarLogin(String email, String password) {
        List<Administrador> lista = FileManager.carregar(FILE_PATH);
        return lista.stream().anyMatch(a -> {
            try {return a.getEmail().equals(email) && a.getPassword().equals(password);
            } catch (Exception e) {throw new RuntimeException(e);}
        });
    }

    public static boolean salvarNovoAdm(String email, String password) throws Exception {
        List<Administrador> lista = FileManager.carregar(FILE_PATH);
        if (lista.stream().anyMatch(a -> a.getEmail().equals(email))) {return false;}
        lista.add(new Administrador(email, Criptografia.criptografar(password, Criptografia.lerChaveDeArquivo(Criptografia.getARQUIVO_CHAVE()))));
        FileManager.salvar(FILE_PATH, lista);
        return true;
    }
}
