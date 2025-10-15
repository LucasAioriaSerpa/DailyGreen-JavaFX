package org.dailygreen.dailygreen.view.components.postagens;

import org.dailygreen.dailygreen.controller.MainFeedController;
import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.persistence.PersistenceFacade;
import org.dailygreen.dailygreen.persistence.PersistenceFacadeFactory;
import static org.dailygreen.dailygreen.view.components.postagens.UpdatePostList.updatePostList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CreateReactionsBox {
    private static final PersistenceFacade persistenceFacade = PersistenceFacadeFactory.createJsonPersistenceFacade();
    
    public static HBox createReactionsBox(Stage stage, Post post, User user) {
        HBox reactionsBox = new HBox(5);
        reactionsBox.setAlignment(Pos.CENTER_LEFT);
        reactionsBox.getStyleClass().add("reactions-box");
        String[] tipos = {"gostei", "parabens", "apoio", "amei", "genial"};
        String tipoReacaoUsuario = persistenceFacade.findUserReactionType(user.getAccountParticipante().getEmail(), post.getID()).orElse("");
        for (String tipo : tipos) {
            long count = persistenceFacade.countReactionsByType(post.getID(), tipo);
            Button btn = new Button(tipo.substring(0, 1).toUpperCase() + tipo.substring(1) + " (" + count + ")");
            btn.getStyleClass().add("reaction-button");
            btn.getStyleClass().add("button-" + tipo);
            if (tipo.equals(tipoReacaoUsuario)) {
                btn.getStyleClass().add("selected");
            }
            btn.setOnAction(_ -> {
                MainFeedController.acaoReagir(post, tipo, user.getAccountParticipante().getEmail());
                updatePostList(stage);
            });
            reactionsBox.getChildren().add(btn);
        }
        return reactionsBox;
    }
}
