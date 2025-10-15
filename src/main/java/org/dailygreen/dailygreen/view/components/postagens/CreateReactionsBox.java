package org.dailygreen.dailygreen.view.components.postagens;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.dailygreen.dailygreen.model.post.Post;
import org.dailygreen.dailygreen.model.user.User;
import org.dailygreen.dailygreen.repository.impl.ReactionJsonRepository;
import org.dailygreen.dailygreen.controller.MainFeedController;

import static org.dailygreen.dailygreen.view.components.postagens.UpdatePostList.updatePostList;

public class CreateReactionsBox {
    public static HBox createReactionsBox(Stage stage, Post post, User user) {
        HBox reactionsBox = new HBox(5);
        reactionsBox.setAlignment(Pos.CENTER_LEFT);
        reactionsBox.getStyleClass().add("reactions-box");
        String[] tipos = {"gostei", "parabens", "apoio", "amei", "genial"};
        String tipoReacaoUsuario = String.valueOf(new ReactionJsonRepository().findUserReactionType(user.getAccountParticipante().getEmail(), post.getID()));
        for (String tipo : tipos) {
            long count = new ReactionJsonRepository().countByTarget(post.getID(), tipo);
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
