package org.dailygreen.dailygreen.model.event;

import java.io.Serializable;

public class EventAttendance implements Serializable {
    private String nome;
    private String descricao;
    private String data;

    public EventAttendance(String nome, String descricao, String data) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Evento: " + nome + " | " + descricao + " | Data: " + data;
    }
}
