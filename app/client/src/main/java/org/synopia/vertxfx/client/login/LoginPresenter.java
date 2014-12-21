package org.synopia.vertxfx.client.login;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.synopia.vertxfx.formular.Form;
import org.synopia.vertxfx.formular.FormEvent;
import org.synopia.vertxfx.formular.Required;
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
    @Required
    private TextField login;

    @FXML
    @Required
    private PasswordField password;

    @FXML
    private Button send;

    @Inject
    private RPC rpc;
    private Form form;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        send.setDisable(true);
        form = new Form(this);

        form.validProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean old, Boolean newValue) {
                send.setDisable(!newValue);
            }
        });

        form.initialize();
    }

    public void onSend() {
        if (form.getValid()) {
            form.getRoot().fireEvent(FormEvent.SUBMIT);

            rpc.get(SessionService.class).createSession(login.getText(), password.getText()).setHandler(result -> {
                if (result.succeeded()) {
                    form.getRoot().fireEvent(new FormEvent(FormEvent.FORM_FINISHED, 1));
                } else {
                    result.cause().printStackTrace();
                }
            });
        }
    }
}
