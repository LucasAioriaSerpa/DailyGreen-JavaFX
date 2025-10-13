package org.dailygreen.dailygreen.model.user.types;

import java.io.Serial;
import java.io.Serializable;

public class Administrator implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long ID;
    private String email;
    private String password;

    public Administrator(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // METODOS GETTERS E SETTERS
    public long getID() {return ID;}
    public void setID(long ID) {this.ID = ID;}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {return password;}
    public String setPassword(String password) { return this.password = password; }

}
