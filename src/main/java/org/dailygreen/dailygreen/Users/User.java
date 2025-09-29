package org.dailygreen.dailygreen.Users;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String type;
    private String emailOrganizador;

    private Administrador accountAdministrador;
    private Participante accountParticipante;
    private Organizacao accountOrganizacao;
    private boolean isLogged = false;
    public User(String type){this.type = type; /* ? "participante" , "Organizador" e "Administrador"*/ }

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public Administrador getAccountAdministrador() {return accountAdministrador;}
    public void setAccountAdministrador(Administrador accountAdministrador) {this.accountAdministrador = accountAdministrador;}

    public Participante getAccountParticipante() {return accountParticipante;}
    public void setAccountParticipante(Participante accountParticipante) {this.accountParticipante = accountParticipante;}

    public Organizacao getAccountOrganizacao() {return accountOrganizacao;}
    public void setAccountOrganizacao(Organizacao accountOrganizacao) {this.accountOrganizacao = accountOrganizacao;}

    public boolean isLogged() {return isLogged;}
    public void setLogged(boolean logged) {isLogged = logged;}

    public String getEmailOrganizador() {
        return emailOrganizador;
    }
    public void setEmailOrganizador(String emailOrganizador) {
        this.emailOrganizador = emailOrganizador;
    }
}