package org.synopia.vertxfx.client.main;

import com.airhacks.afterburner.views.FXMLView;
import javafx.scene.Parent;
import jidefx.scene.control.decoration.DecorationPane;

/**
 * Created by synopia on 20.12.2014.
 */
public class MainView extends FXMLView {
    @Override
    public Parent getView() {
        return new DecorationPane(super.getView());
    }
}
