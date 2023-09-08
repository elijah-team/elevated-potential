package tripleo.elijah.ui.monaco;

import eu.mihosoft.monacofx.*;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import org.jetbrains.annotations.NotNull;

public class MonacoFXApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(@NotNull Stage primaryStage) throws Exception {

		// create a new monaco editor node
		MonacoFX  monacoFX = new MonacoFX();
		StackPane root     = new StackPane(monacoFX);

		// set initial text
		monacoFX.getEditor().getDocument().setText(
				"#include <stdio.h>\n" +
						"int main() {\n" +
						"   // printf() displays the string inside quotation\n" +
						"   printf(\"Hello, World!\");\n" +
						"   return 0;\n" +
						"}");

		// use a predefined language like 'c'
		monacoFX.getEditor().setCurrentLanguage("c");
		monacoFX.getEditor().setCurrentTheme("vs-dark");

		// the usual scene & stage setup
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setTitle("MonacoFX Demo (running on JDK " + System.getProperty("java.version") + ")");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


}
