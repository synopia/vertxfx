package org.synopia.vertxfx.client.login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.synopia.vertxfx.rpc.RPC;
import org.synopia.vertxfx.session.SessionService;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by synopia on 14.12.2014.
 */
public class LoginPresenter implements Initializable {
    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button send;

    @FXML
    private Label connectionState;

    @FXML
    private Label ping;

    @Inject
    private RPC rpc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onSend() {
        rpc.get(SessionService.class).createSession(login.getText(), password.getText()).setHandler(result -> {
            connectionState.setText("connected! id=" + result.result());
        });
    }
}
