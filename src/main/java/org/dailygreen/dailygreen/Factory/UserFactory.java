package org.dailygreen.dailygreen.Factory; // Ou em um pacote factory

import org.dailygreen.dailygreen.model.user.Role;
import org.dailygreen.dailygreen.model.user.types.Administrator;
import org.dailygreen.dailygreen.model.user.types.Organizator;
import org.dailygreen.dailygreen.model.user.types.Participant;

public class UserFactory {

    /**
     * Cria uma instância de um tipo de usuário específico.
     * @param role O papel do usuário a ser criado.
     * @param nome O nome do usuário.
     * @param email O email do usuário.
     * @param password A senha (sem criptografia ainda).
     * @return Uma instância de AbstractUser (Administrator, Participant, ou Organizator).
     */
    public static Object createUser(Role role, String nome, String email, String password, String nomeOrganizacao, String CNPJ) throws Exception {
        if (role == null) { throw new IllegalArgumentException("O papel (Role) do usuário não pode ser nulo."); }
        switch (role) {
            case ADMINISTRADOR: return new Administrator(email, password);
            case PARTICIPANTE:  return new Participant(nome, email, password);
            case ORGANIZADOR:   return new Organizator(nome, email, password, nomeOrganizacao, CNPJ);
            default: throw new IllegalArgumentException("Tipo de usuário desconhecido: " + role);
        }
    }
}