package org.dailygreen.dailygreen.Users.Administrador.dao;

import org.dailygreen.dailygreen.Users.Administrador.models.Administrador;
import org.dailygreen.dailygreen.Users.Administrador.utils.FileManager;

import java.util.List;


public class AdmDAO {
    private static final String FILE_PATH = "src/main/resources/db_dailygreen/adm.dat";

    public static boolean validarLogin(String email, String password) {
        List<Administrador> lista = FileManager.carregar(FILE_PATH);
        return lista.stream().anyMatch(a -> a.getEmail().equals(email) && a.getPassword().equals(password));
    }

    public static boolean salvarNovoAdm(String email, String password) {
        List<Administrador> lista = FileManager.carregar(FILE_PATH);
        if (lista.stream().anyMatch(a -> a.getEmail().equals(email))) {
            return false;
        }
        lista.add(new Administrador(email, password));
        FileManager.salvar(FILE_PATH, lista);
        return true;
    }
}
