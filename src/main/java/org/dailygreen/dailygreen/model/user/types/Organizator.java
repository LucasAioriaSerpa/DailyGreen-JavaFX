package org.dailygreen.dailygreen.model.user.types;

import org.dailygreen.dailygreen.model.user.AbstractUser;
import org.dailygreen.dailygreen.util.Cryptography;

import java.io.Serializable;

public class Organizator extends AbstractUser implements Serializable {
    private String nomeOrganizacao;
    private String CNPJ;
    public Organizator(String nome, String email, String password, String nomeOrganizacao, String CNPJ) throws Exception {
        this.nomeOrganizacao = nomeOrganizacao;
        this.CNPJ = CNPJ;
        super(nome, email, Cryptography.criptografar(
                password,
                Cryptography.lerChaveDeArquivo(Cryptography.getARQUIVO_CHAVE()))
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

    @Override
    public int hashCode() { return CNPJ.hashCode(); }

}
