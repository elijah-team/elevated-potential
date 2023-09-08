package tripleo.elijah.ui.monaco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.Objects;

public class BasicApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(@NotNull Stage stage) throws Exception {
		System.setProperty("javafx.verbose", "true");

		final URL resource = getClass().getResource("BasicFXML.fxml");

		Parent root  = FXMLLoader.load(Objects.requireNonNull(resource));
		Scene  scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
