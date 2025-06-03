package org.dailygreen.dailygreen.Users.Organizacao;



public class Organizacao {
    private int id;
    private String nome;
    private String email;
    private String cnpj;
    private String descricao;
    private boolean verificada;

    // Construtor com ID (usado ao carregar do banco de dados)
    public Organizacao(int id, String nome, String email, String cnpj, String descricao, boolean verificada) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.verificada = verificada;
    }

    // Construtor sem ID (usado ao inserir no banco)
    public Organizacao(String nome, String email, String cnpj, String descricao, boolean verificada) {
        this.nome = nome;
        this.email = email;
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.verificada = verificada;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isVerificada() {
        return verificada;
    }

    public void setVerificada(boolean verificada) {
        this.verificada = verificada;
    }

    // Representação em texto (útil em TableView ou console)
    @Override
    public String toString() {
        return nome + " - " + (verificada ? "Verificada" : "Não verificada");
    }
}
