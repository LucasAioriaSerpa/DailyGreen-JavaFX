package org.dailygreen.dailygreen.model.user;

import java.io.Serial;
import java.io.Serializable;

public abstract class AbstractUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long ID;
    private String nome;
    private String email;
    private String password;
    public AbstractUser(String nome, String email, String password) {
        this.nome = nome;
        this.email = email;
        this.password = password;
    }
    public static long getSerialVersionUID() { return serialVersionUID; }
    public long getID() { return ID; }
    public void setID(long ID) { this.ID = ID; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Usuário{" +
                "serialVersionUID='" + serialVersionUID + '\'' +
                "   ID='" + ID + '\'' +
                "   nome='" + nome + '\'' +
                ",  email='" + email + '\'' +
                ",  Senha='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractUser that = (AbstractUser) obj;
        return this.ID == that.ID || this.email.equals(that.email) || this.password.equals(that.password);
    }

    @Override
    public int hashCode() { return Long.hashCode(ID); }

}
