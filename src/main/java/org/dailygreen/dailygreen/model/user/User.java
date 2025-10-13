package org.dailygreen.dailygreen.model.user;

import org.dailygreen.dailygreen.model.user.types.Administrator;
import org.dailygreen.dailygreen.model.user.types.Organizator;
import org.dailygreen.dailygreen.model.user.types.Participant;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Role role;
    private String emailOrganizador;
    private Administrator accountAdministrator;
    private Participant accountParticipant;
    private Organizator accountOrganizator;
    private boolean isLogged = false;
    public User(Role role){ this.role = role; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Administrator getAccountAdministrador() {return accountAdministrator;}
    public void setAccountAdministrador(Administrator accountAdministrator) {this.accountAdministrator = accountAdministrator;}

    public Participant getAccountParticipante() {return accountParticipant;}
    public void setAccountParticipante(Participant accountParticipant) {this.accountParticipant = accountParticipant;}

    public Organizator getAccountOrganizator() {return accountOrganizator;}
    public void setAccountOrganizator(Organizator accountOrganizator) {this.accountOrganizator = accountOrganizator;}

    public boolean isLogged() {return isLogged;}
    public void setLogged(boolean logged) {isLogged = logged;}

    public String getEmailOrganizador() {
        return emailOrganizador;
    }
    public void setEmailOrganizador(String emailOrganizador) {
        this.emailOrganizador = emailOrganizador;
    }
}