package org.dailygreen.dailygreen.Postagens.Post;

import org.dailygreen.dailygreen.Postagens.Comentario.Comentario;
import org.dailygreen.dailygreen.Postagens.Reacao.Reacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serial;
import java.util.List;

public class Post implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long ID;
    private final long id_autor;
    private String titulo;
    private String descricao;
    private Date data_criacao;
    private final Reacao reacoes;
    private List<Comentario> comentarios = new ArrayList<>();
    public Post(long id_autor, String titulo, String descricao) {
        this.id_autor = id_autor;
        this.titulo = titulo;
        this.descricao = descricao;
        data_criacao = new Date();
        reacoes = new Reacao("", ID, "");
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
