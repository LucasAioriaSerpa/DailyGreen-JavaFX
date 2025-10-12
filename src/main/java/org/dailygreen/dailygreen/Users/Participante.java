package org.dailygreen.dailygreen.Users;

import org.dailygreen.dailygreen.util.Criptografia;

import java.io.Serializable;

public class Participante extends Usuario implements Serializable {
    public Participante(String nome, String email, String password) throws Exception {
        super(nome, email, Criptografia.criptografar(
                password,
                Criptografia.lerChaveDeArquivo(Criptografia.getARQUIVO_CHAVE()))
        );
    }
    @Override
    public String toString() {
        return "Participante{" +
                "   serialVersionUID='" + getSerialVersionUID() + '\'' +
                "   ID='" + getID() + '\'' +
                "   nome='" + getNome() + '\'' +
                ",  email='" + getEmail() + '\'' +
                ",  Senha='" + getPassword() + '\'' +
                '}';
    }
}