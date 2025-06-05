package org.dailygreen.dailygreen.Users.Administrador.models;

import org.dailygreen.dailygreen.Users.Administrador.utils.FileManager;
import org.dailygreen.dailygreen.Users.Administrador.models.Admnistrador;
import javax.tools.JavaFileManager;
import java.util.ArrayList;
import java.util.List;


public class AdmValidacao {
    private static final String FILE_PATH = "adm.dat";

    public static boolean validarLogin(String email, String password) {
        List<Admnistrador> lista = FileManager.carregar(FILE_PATH);
        return lista.stream().anyMatch(a -> a.getEmail().equals(email) && a.getPassword().equals(password));
    }

    public static boolean salvarNovoAdm(String email, String password) {
        List<Admnistrador> lista = FileManager.carregar(FILE_PATH);
        if (lista.stream().anyMatch(a -> a.getEmail().equals(email))) {
            return false;
        }
        lista.add(new Admnistrador(email, password));
        FileManager.salvar(FILE_PATH, lista);
        return true;
    }
}
