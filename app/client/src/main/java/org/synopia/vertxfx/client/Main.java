package org.synopia.vertxfx.client;

import com.airhacks.afterburner.injection.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.synopia.vertxfx.client.login.LoginView;
import org.synopia.vertxfx.rpc.ClientRPC;
import org.synopia.vertxfx.rpc.RPC;
import org.vertx.java.platform.PlatformLocator;
import org.vertx.java.platform.PlatformManager;
import org.vertx.java.spi.cluster.impl.hazelcast.HazelcastClusterManagerFactory;

/**
 * Created by synopia on 14.12.2014.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("vertx.clusterManagerFactory", HazelcastClusterManagerFactory.class.getCanonicalName());
        PlatformManager pm = PlatformLocator.factory.createPlatformManager(0, "127.0.0.1");

        Injector.setModelOrService(RPC.class, new ClientRPC(pm.vertx().eventBus()));
        LoginView view = new LoginView();
        Scene scene = new Scene(view.getView());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
