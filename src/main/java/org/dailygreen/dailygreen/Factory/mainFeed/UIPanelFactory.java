package org.dailygreen.dailygreen.Factory.mainFeed;

import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.model.user.Role;

public class UIPanelFactory {
    public static IPostagensUIPanel getPanel(User user) {
        Role userType = user.getRole();
        if (Role.PARTICIPANTE.equals(userType)) { return new ParticipanteUIPanel(); }
        else if (Role.ORGANIZADOR.equals(userType)) { return new OrganizadorUIPanel(); }
        throw new IllegalArgumentException("Tipo de usuário desconhecido:" + userType);
    }
}
