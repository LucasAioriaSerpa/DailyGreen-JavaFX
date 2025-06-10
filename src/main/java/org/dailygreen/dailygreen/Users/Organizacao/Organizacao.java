package org.dailygreen.dailygreen.Users.Organizacao;

/**
 * Classe que representa uma organização no sistema.
 * Contém informações básicas como nome, email, CNPJ e status de verificação.
 */
public class Organizacao {
    private int id;
    private String nome;
    private String email;
    private String cnpj;
    private String descricao;
    private boolean verificada;

    // Construtores
    public Organizacao() {
        // Construtor padrão para frameworks que exigem
    }

    /**
     * Construtor para criação de novas organizações
     */
    public Organizacao(String nome, String email, String cnpj, String descricao, boolean verificada) {
        setNome(nome);
        setEmail(email);
        setCnpj(cnpj);
        setDescricao(descricao);
        setVerificada(verificada);
    }

    // Getters e Setters com validações básicas
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id < 0) {
            throw new IllegalArgumentException("ID não pode ser negativo");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email.toLowerCase().trim();
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        if(cnpj == null || cnpj.replaceAll("\\D", "").length() != 14) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
        this.cnpj = cnpj.replaceAll("\\D", ""); // Remove não-dígitos
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao != null ? descricao.trim() : "";
    }

    public boolean isVerificada() {
        return verificada;
    }

    public void setVerificada(boolean verificada) {
        this.verificada = verificada;
    }

    // Métodos utilitários
    @Override
    public String toString() {
        return String.format("%s (ID: %d) - %s", nome, id, verificada ? "Verificada" : "Não verificada");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organizacao that = (Organizacao) o;
        return id == that.id || cnpj.equals(that.cnpj);
    }

    @Override
    public int hashCode() {
        return cnpj.hashCode();
    }
}