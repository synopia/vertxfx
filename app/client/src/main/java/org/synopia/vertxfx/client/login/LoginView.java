package org.synopia.vertxfx.client.login;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Parent;
import jidefx.scene.control.decoration.DecorationPane;

/**
 * Created by synopia on 14.12.2014.
 */
public class LoginView extends FXMLView {
    @Override
    public Parent getView() {
        return new DecorationPane(super.getView());
    }
}
