package org.dailygreen.dailygreen.model.post;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serial;
import java.util.List;

public class Post implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @SerializedName("postID")
    private long ID;
    private final long id_autor;
    private String titulo;
    private String descricao;
    private Date data_criacao;
    private final Reaction reacoes;
    private List<Comment> comments = new ArrayList<>();
    public Post(long id_autor, String titulo, String descricao) {
        this.id_autor = id_autor;
        this.titulo = titulo;
        this.descricao = descricao;
        data_criacao = new Date();
        reacoes = new Reaction("", ID, "");
    }
    // ? GETTER AND SETTER
    public long getSerialVersionUID() {return serialVersionUID;}

    public long getID() {return ID;}
    public void setID(long ID) {this.ID = ID;}

    public long getId_autor() {return id_autor;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
}
