package org.dailygreen.dailygreen.model.moderation;

import java.io.Serializable;
import java.time.LocalDate;

public class Report implements Serializable {
    private Integer id;
    private String titulo;
    private String motivo;
    private String participante;
    private LocalDate data;
    private String status;
    private boolean suspenso = false;
    private boolean banido = false;

    public Report(String participante, String titulo, String motivo) {
        this.id = DenunciaDAO.updateId();
        this.participante = participante;
        this.titulo = titulo;
        this.motivo = motivo;
        this.data = LocalDate.now();
        this.status = "Pendente";
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

    public String getStatus(){
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getParticipante(){return participante;}
    public void setParticipante(String participante){this.participante = participante;}

    public LocalDate getData(){return data;}
    public void setData(LocalDate data){this.data = data;}

    public boolean getSuspenso(){
        return suspenso;
    }
    public void setSuspenso(boolean suspenso){
        this.suspenso = suspenso;
    }

    public boolean getBanido(){
        return banido;
    }
    public void setBanido(boolean banido){ this.banido = banido;}

}
