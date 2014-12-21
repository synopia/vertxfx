package org.synopia.vertxfx.client.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.synopia.vertxfx.client.login.LoginView;
import org.synopia.vertxfx.deploy.DeployService;
import org.synopia.vertxfx.formular.FormEvent;
import org.synopia.vertxfx.rpc.RPC;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by synopia on 20.12.2014.
 */
public class MainPresenter implements Initializable {
    @FXML
    private Label status;

    @FXML
    private Label ping;

    @FXML
    private Pane content;

    @Inject
    private RPC rpc;
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ping.setText("-");
        status.setText("");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            rpc.get(DeployService.class).ping(System.currentTimeMillis()).setHandler(r -> {
                if (r.succeeded()) {
                    long sentTime = r.result();
                    ping.setText((System.currentTimeMillis() - sentTime) + "ms");
                    setStatus("Connected");
                } else {
                    ping.setText("-");
                    setStatus("No connection");
                }
            });
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        this.timeline = new Timeline(new KeyFrame(Duration.seconds(3), actionEvent -> {
            status.setVisible(false);
        }));
        this.timeline.setCycleCount(1);
        content.getChildren().add(new LoginView().getView());
        content.addEventHandler(FormEvent.FORM_FINISHED, formEvent -> content.getChildren().clear());
    }

    public void setStatus(String msg) {
        if (!msg.equals(status.getText())) {
            status.setText(msg);
            status.setVisible(true);
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                timeline.stop();
            }
            timeline.play();
        }
    }
}
