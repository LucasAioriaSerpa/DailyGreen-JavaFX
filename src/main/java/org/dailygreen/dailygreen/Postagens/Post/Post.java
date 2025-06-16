package org.dailygreen.dailygreen.Postagens.Post;

import org.dailygreen.dailygreen.Postagens.Reacao.Recao;

import java.io.Serializable;
import java.util.Date;
import java.io.Serial;

public class Post implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long ID;
    private final long id_autor;
    private String titulo;
    private String descricao;
    private Date data_criacao;
    private final Recao reacoes;
    public Post(long id_autor, String titulo, String descricao) {
        this.id_autor = id_autor;
        this.titulo = titulo;
        this.descricao = descricao;
        data_criacao = new Date();
        reacoes = new Recao("");
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

    //? add reaction
    public void addReaction(String reaction){reacoes.setReaction(reaction, '+');}

    //? substract reaction
    public void substractReaction(String reaction){reacoes.setReaction(reaction, '-');}

}
