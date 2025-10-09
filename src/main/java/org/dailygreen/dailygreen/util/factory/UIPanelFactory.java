package org.dailygreen.dailygreen.util.factory;

import org.dailygreen.dailygreen.Users.User;

public class UIPanelFactory {
    public static IPostagensUIPanel getPanel(User user) {
        String userType = user.getType();
        if ("participante".equals(userType)) { return new ParticipanteUIPanel(); }
        else if ("organizador".equals(userType)) { return new OrganizadorUIPanel(); }
        throw new IllegalArgumentException("Tipo de usuário desconhecido:" + userType);
    }
}
