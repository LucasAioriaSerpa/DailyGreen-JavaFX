package org.dailygreen.dailygreen.Users;

import java.io.Serializable;

public class User<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String type;
    private String status;
    private T account;
    public User(String type, String status) {
        this.type = type; // ? "Participante" , "Organizador" e "Administrador"
        this.status = status; //? Found! e not_found!
    }
    public String getType() {return type;}
    public String getStatus() {return status;}
    public T getAccount() {return account;}
    public void setAccount(Object account) {this.account = (T) account;}
    public void setStatus(String status) {this.status = status;}
    public void setType(String type) {this.type = type;}
}