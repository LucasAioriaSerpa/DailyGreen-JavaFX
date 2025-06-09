package org.dailygreen.dailygreen.Users.Administrador.models;

import java.io.Serializable;
import java.util.Date;

public class Suspenso implements Serializable {
    private String tituloPost;
    private String descricaoPost;
    private Date dataPublicacao;

    public Suspenso (String tituloPost, String descricaoPost, Date dataPublicacao){
        this.tituloPost = tituloPost;
        this.descricaoPost = descricaoPost;
        this.dataPublicacao = dataPublicacao;
    }

    // MÉTODOS GETTERS E SETTERS

    public String getTituloPost(){
        return tituloPost;
    }

    public void setTituloPost(String tituloPost) {
        this.tituloPost = tituloPost;
    }

    public String getDescricaoPost(){
        return descricaoPost;
    }

    public void setDescricaoPost(String descricaoPost) {
        this.descricaoPost = descricaoPost;
    }

    public Date getDataPublicacao(){
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao){
        this.dataPublicacao = dataPublicacao;
    }

}
