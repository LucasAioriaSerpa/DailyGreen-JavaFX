package org.dailygreen.dailygreen.model.post;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Representa uma reação de um usuário a uma postagem.
 */
public class Reaction implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String autorEmail;
    private long idPost;
    private String tipo;
    private Date data;

    /**
     * Construtor da reação.
     * @param autorEmail E-mail do autor da reação.
     * @param idPost ID da postagem.
     * @param tipo Tipo da reação.
     */
    public Reaction(String autorEmail, long idPost, String tipo) {
        this.autorEmail = autorEmail;
        this.idPost = idPost;
        this.tipo = tipo;
        this.data = new Date();
    }

    public String getAutorEmail() { return autorEmail; }
    public long getIdPost() { return idPost; }
    public String getTipo() { return tipo; }
    public Date getData() { return data; }
    /**
     * Define o tipo de reação.
     * @param reaction Tipo da reação (ex: "gostei", "parabens", etc.).
     * @param c Operação: '+' para adicionar a reação, '-' para remover.
     */
    public void setReaction(String reaction, char c) {
        if (c == '+') { this.tipo = reaction; }
        else if (c == '-') { this.tipo = ""; }
        else { throw new IllegalArgumentException("Invalid operation: " + c); }
    }
}
