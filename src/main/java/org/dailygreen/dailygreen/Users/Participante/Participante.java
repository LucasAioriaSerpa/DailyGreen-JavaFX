package org.dailygreen.dailygreen.Users.Participante;

import java.io.Serial;
import java.io.Serializable;

public class Participante implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String nome;
    private String email;
    private String password;

    public Participante(String nome, String email, String password) {
        this.nome = nome;
        this.email = email;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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

    @Override
    public String toString() {
        return "Participante{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                "Senha='" + password + '\'' +
                '}';
    }
}