package org.dailygreen.dailygreen.Users.Administrador.models;

import java.io.Serializable;
import java.util.Date;

public class Denuncia implements Serializable {
    //private Integer id;
    private String titulo;
    private String motivo;

    public Denuncia(String titulo, String motivo) {
        this.titulo = titulo;
        this.motivo = motivo;
    }

    // METODOS GETTERS E SETTERS
    //public Integer getId(){ return id};

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
