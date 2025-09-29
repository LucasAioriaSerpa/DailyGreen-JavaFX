package org.dailygreen.dailygreen.Users;

import java.io.Serializable;

public class Organizador implements Serializable {
    private String email;
    private String senha;
    private String cnpj;

    public Organizador(String email, String senha, String cnpj) {
        this.email = email;
        this.senha = senha;
        this.cnpj = cnpj;
    }

    // Getters e setters
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getCnpj() { return cnpj; }

    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
}
