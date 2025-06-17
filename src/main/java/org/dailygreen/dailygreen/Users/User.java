package org.dailygreen.dailygreen.Users;

import java.io.Serial;
import java.io.Serializable;

public class User<T extends Serializable> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String type;
    private T account;
    public User(String type){this.type = type; /* ? "participante" , "Organizador" e "Administrador"*/}
    public String getType() {return type;}
    public T getAccount() {return account;}
    public void setAccount(Object account) {this.account = (T) account;}
    public void setType(String type) {this.type = type;}
}