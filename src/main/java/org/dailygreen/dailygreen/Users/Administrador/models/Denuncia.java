package org.dailygreen.dailygreen.Users.Administrador.models;

import java.util.Date;

public class Denuncia {
    private Integer id;
    private String titulo;
    private String motivo;

    public Denuncia(int id, String titulo, String motivo) {
        this.id = id;
        this.titulo = titulo;
        this.motivo = motivo;
    }

    // METODOS GETTERS E SETTERS
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

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
