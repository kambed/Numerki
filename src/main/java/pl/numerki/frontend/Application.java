package pl.numerki.frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static pl.numerki.frontend.MainFormController.*;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(MAIN_FORM_RESOURCE));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle(MAIN_FORM_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}