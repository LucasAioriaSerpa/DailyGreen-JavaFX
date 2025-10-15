package org.dailygreen.dailygreen.Service;

import org.dailygreen.dailygreen.Factory.UserFactory;
import org.dailygreen.dailygreen.model.user.AbstractUser;
import org.dailygreen.dailygreen.model.user.Role;
import org.dailygreen.dailygreen.model.user.types.Organizator;
import org.dailygreen.dailygreen.model.user.types.Participant;
import org.dailygreen.dailygreen.repository.impl.OrganizadorJsonRepository;
import org.dailygreen.dailygreen.repository.impl.ParticipantJsonRepository;

public class UserService {
    private final ParticipantJsonRepository participantJsonRepository = new ParticipantJsonRepository();
    private final OrganizadorJsonRepository organizadorJsonRepository = new OrganizadorJsonRepository();
    public void registerNewUser(String nome, String email, String password, String nomeOrganizador, String cnpj , Role role) throws Exception {
        AbstractUser newUser = (AbstractUser) UserFactory.createUser(role, nome, email, password, nomeOrganizador, cnpj);
        if (newUser instanceof Participant) { participantJsonRepository.save((Participant) newUser); }
        else if (newUser instanceof Organizator) { organizadorJsonRepository.save((Organizator) newUser); }
    }
}