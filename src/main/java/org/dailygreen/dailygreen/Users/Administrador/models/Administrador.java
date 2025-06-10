package org.dailygreen.dailygreen.Users.Administrador.models;

import org.dailygreen.dailygreen.util.Criptografia;

import java.io.Serial;
import java.io.Serializable;

public class Administrador implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String email;
    private final String password;

    public Administrador(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // METODOS GETTERS E SETTERS

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() throws Exception {return Criptografia.descriptografar(password, Criptografia.lerChaveDeArquivo(Criptografia.getARQUIVO_CHAVE()));}

}
