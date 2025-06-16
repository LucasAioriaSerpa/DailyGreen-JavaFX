package org.dailygreen.dailygreen.Users;

import java.io.Serial;
import java.io.Serializable;

public class User <T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String type;
    private boolean logged;
    private T account;
    public User(String type){
        this.type = type; /* ? "participante" , "Organizador" e "Administrador"*/
        this.logged = false;
    }
    public String getType() {return type;}
    public boolean isLogged() {return logged;}
    public T getAccount() {return account;}
    public void setAccount(Object account) {this.account = (T) account;}
    public void setType(String type) {this.type = type;}
    public void setLogged(boolean logged) {this.logged = logged;}
}