package org.dailygreen.dailygreen.Users.Administrador.models;

import java.io.Serializable;

public class Administrador implements Serializable {
    private String email;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
