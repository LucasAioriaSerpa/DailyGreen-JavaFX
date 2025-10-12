package org.dailygreen.dailygreen.Users;

import org.dailygreen.dailygreen.util.Criptografia;

import java.io.Serializable;

public class Organizador extends Usuario implements Serializable {
    private String nomeOrganizacao;
    private String CNPJ;
    public Organizador(String nome, String email, String password, String nomeOrganizacao, String CNPJ) throws Exception {
        this.nomeOrganizacao = nomeOrganizacao;
        this.CNPJ = CNPJ;
        super(nome, email, Criptografia.criptografar(
                password,
                Criptografia.lerChaveDeArquivo(Criptografia.getARQUIVO_CHAVE()))
        );
    }

    public String getNomeOrganizacao() { return this.nomeOrganizacao; }
    public void setNomeOrganizacao(String nomeOrganizacao) { this.nomeOrganizacao = nomeOrganizacao; }
    public String getCNPJ() { return CNPJ; }
    public void setCNPJ(String CNPJ) { this.CNPJ = this.CNPJ; }

    @Override
    public String toString() {
        return "Organizador{" +
                "   serialVersionUID='" + getSerialVersionUID() + '\'' +
                "   ID='" + getID() + '\'' +
                "   nome='" + getNome() + '\'' +
                "   Nome da organização='" + getNomeOrganizacao() + '\'' +
                "   CNPJ='" + getCNPJ() + '\'' +
                ",  email='" + getEmail() + '\'' +
                ",  Senha='" + getPassword() + '\'' +
                '}';
    }

}
