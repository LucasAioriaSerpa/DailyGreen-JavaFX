package org.dailygreen.dailygreen.Users;

import org.dailygreen.dailygreen.util.Criptografia;

import java.io.*;

public class Participante implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long ID;
    private String nome;
    private String email;
    private String password;
    public Participante(String nome, String email, String password) throws Exception {
        this.nome = nome;
        this.email = email;
        this.password = Criptografia.criptografar(password, Criptografia.lerChaveDeArquivo(Criptografia.getARQUIVO_CHAVE()));
    }
    public long getID() {return ID;}
    public void setID(long ID) {this.ID = ID;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    @Override
    public String toString() {
        return "Participante{" +
                "serialVersionUID='" + serialVersionUID + '\'' +
                "ID='" + ID + '\'' +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", Senha='" + password + '\'' +
                '}';
    }
}