package org.dailygreen.dailygreen.Users.Administrador.models;

import org.dailygreen.dailygreen.Users.Administrador.utils.datAdm;

import java.io.IOException;
import java.util.List;


public class AdmValidacao {
    private static final datAdm adm;

    static {
        try {
            adm = new datAdm();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AdmValidacao() throws IOException {
    }

    public static boolean validarLogin(String email, String password) {
        List<Administrador> lista = adm.loadArray();
        return lista.stream().anyMatch(a -> a.getEmail().equals(email) && a.getPassword().equals(password));
    }

    public static boolean salvarNovoAdm(String email, String password) {
        adm.addObject(new Administrador(email, password));
        return true;
    }
}
