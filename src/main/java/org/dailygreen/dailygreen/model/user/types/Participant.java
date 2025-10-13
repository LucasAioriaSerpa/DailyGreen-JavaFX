package org.dailygreen.dailygreen.model.user.types;

import org.dailygreen.dailygreen.model.user.AbstractUser;
import org.dailygreen.dailygreen.util.Criptografia;

import java.io.Serializable;

public class Participant extends AbstractUser implements Serializable {
    public Participant(String nome, String email, String password) throws Exception {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return this.getID() == that.getID() || this.getEmail().equals(that.getEmail());
    }

}