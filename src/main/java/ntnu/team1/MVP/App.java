package ntnu.team1.MVP;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ntnu.team1.application.MainRegister;
import ntnu.team1.application.fileHandling.Write;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static MainRegister reg = new MainRegister();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 640, 480);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setReg(MainRegister reg) {
        App.reg = reg;
    }

    public static void setRootWithSave(String fxml, MainRegister register) throws IOException {
        reg=register;
        Write writer = new Write(register.getCategories(),register.getAllTasks());
        writer.writeCategories();
        writer.writeTasks();
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void stop(){
        System.out.println("Program is closing");
        Write writer = new Write(reg.getCategories(),reg.getAllTasks());
        writer.writeCategories();
        writer.writeTasks();
    }
}