package org.dailygreen.dailygreen.Users.Administrador.models;

import org.dailygreen.dailygreen.Users.Administrador.dao.DenunciaDAO;
import java.io.Serializable;

public class Denuncia implements Serializable {
    private Integer id;
    private String titulo;
    private String motivo;

    public Denuncia(String titulo, String motivo) {
        this.id = DenunciaDAO.updateId();
        this.titulo = titulo;
        this.motivo = motivo;
    }

    // METODOS GETTERS E SETTERS
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
