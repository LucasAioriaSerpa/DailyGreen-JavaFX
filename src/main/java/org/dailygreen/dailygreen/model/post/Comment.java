package org.dailygreen.dailygreen.model.post;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String autorEmail;
    private String conteudo;
    private Date data;
    private long idPost;

    public Comment(String autorEmail, String conteudo, long idPost) {
        this.autorEmail = autorEmail;
        this.conteudo = conteudo;
        this.data = new Date();
        this.idPost = idPost;
    }
    //? Getters and Setters:
    public String getAutorEmail() { return autorEmail; }
    public String getConteudo() { return conteudo; }
    public Date getData() { return data; }
    public long getIdPost() { return idPost; }
}