package org.dailygreen.dailygreen.model.moderation;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Report implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String titulo;
    private String motivo;
    private String participante;
    private LocalDate data;
    private String status;
    private boolean suspenso;
    private boolean banido;

    public Report() {
        this.data = LocalDate.now();
        this.status = "Pendente";
        this.suspenso = false;
        this.banido = false;
    }

    public Report(String participante, String titulo, String motivo) {
        this();
        this.participante = participante;
        this.titulo = titulo;
        this.motivo = motivo;
    }

    public Report(Integer id, String titulo, String motivo, String participante, LocalDate data, String status, boolean suspenso, boolean banido) {
        this.id = id;
        this.titulo = titulo;
        this.motivo = motivo;
        this.participante = participante;
        this.data = (data != null) ? data : LocalDate.now();
        this.status = (status != null) ? status : "Pendente";
        this.suspenso = suspenso;
        this.banido = banido;
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

    @Override
    public String toString() {
        return String.format(
                "Report{id=%d, participante='%s', titulo='%s', motivo='%s', status='%s', data=%s, suspenso=%s, banido=%s}",
                id, participante, titulo, motivo, status, data, suspenso, banido
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
