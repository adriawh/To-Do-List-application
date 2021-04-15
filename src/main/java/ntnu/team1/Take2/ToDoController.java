package ntnu.team1.Take2;

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import ntnu.team1.Take2.App;
import ntnu.team1.application.MainRegister;
import ntnu.team1.application.fileHandling.Read;
import ntnu.team1.application.task.Category;
import ntnu.team1.application.task.MainTask;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

public class ToDoController {

    @FXML
    private javafx.scene.control.TableView<MainTask> TableView;

    @FXML
    private TableColumn<MainTask, Boolean> doneColumn;

    @FXML
    private TableColumn<MainTask, String> nameColumn;

    @FXML
    private TableColumn<MainTask, String> descriptionColumn;

    @FXML
    private TableColumn<MainTask, LocalDate> startDateColumn;

    @FXML
    private TableColumn<MainTask, LocalDate> endDateColumn;

    @FXML
    private TableColumn<MainTask, Integer> priorityColumn;

    @FXML
    private TableColumn<MainTask, Integer> categoryColumn;


    MainRegister register = App.getRegister();
    ObservableList<MainTask> registerWrapper;


    public void initialize(){
        File category= new File("data/categories.ser");
        if (category.exists()){
            Read reader = new Read("data/categories.ser","data/tasks.ser");
            register.setCategories(reader.readCategory());
            register.setTasks(reader.readTasks());
        }
        App.setRegister(register);
        doneColumn.setCellFactory( MainTask -> new CheckBoxTableCell<>());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));

        int n=1000;
        /*for(int i = 0; i<=n;i++) {
            String name = "task " + i;
            String description = "Lorem Ipsum";
            Random random = new Random();
            register.addMainTask(null, null, name, description, random.nextInt(3), -1);
        }*/
        fillTable();
        /*File category= new File("data/categories.ser");
         if(category.exists()){
            Read reader = new Read("data/categories.ser","data/tasks.ser");
            register.setCategories(reader.readCategory());
            register.setTasks(reader.readTasks());
            fillTable();
        }*/
    }

    @FXML
    private void markAsDone(){

    }

    private void fillTable(){

        registerWrapper = FXCollections.observableArrayList(register.getAllTasks());
        registerWrapper.addListener((ListChangeListener<MainTask>) change -> System.out.println("List has updated"));
        TableView.setItems(registerWrapper);
    }

    public ObservableList<MainTask> getRegisterWrapper() {
        return registerWrapper;
    }
}
