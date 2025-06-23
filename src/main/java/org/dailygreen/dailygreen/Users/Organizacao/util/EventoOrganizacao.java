package org.dailygreen.dailygreen.Users.Organizacao.util;

import java.io.Serializable;

public class EventoOrganizacao implements Serializable {
    private String nome;
    private String descricao;
    private String data;

    public EventoOrganizacao(String nome, String descricao, String data) {
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

    @Override
    public String toString() {
        return "Evento: " + nome + " | " + descricao + " | Data: " + data;
    }
}
