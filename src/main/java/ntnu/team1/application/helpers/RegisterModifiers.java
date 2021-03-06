package ntnu.team1.application.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import ntnu.team1.application.main.App;
import ntnu.team1.backend.MainRegister;
import ntnu.team1.backend.exceptions.RemoveException;
import ntnu.team1.backend.objects.Category;
import ntnu.team1.backend.objects.Task;
import ntnu.team1.application.category.CategoryDialog;
import ntnu.team1.application.task.TaskDialog;

import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * Class used for modifying the register
 */

public class RegisterModifiers {

    /**
     * Method for creating a window where you can edit an existing task
     * @param existingTask The task that should be edited
     */

    public static void editTask(Task existingTask){
        if(existingTask == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a Task:");
            alert.setHeaderText("No task selected" );
            alert.setContentText("Please select a task to edit");
            alert.showAndWait();
        }else{
            TaskDialog patientDialog = new TaskDialog(existingTask, true);
            Optional<MainRegister> result = patientDialog.showAndWait();
            result.ifPresent(App::setRegister);
        }
    }

    /**
     * Creates a window for adding a new task
     */

    public static void addNewTask(){
        TaskDialog addNewDialog = new TaskDialog();
        Optional<MainRegister> result = addNewDialog.showAndWait();
        if(result.isPresent()){
            MainRegister register = result.get();
            App.setRegister(register);
        }
    }

    /**
     * Creates a confiramtion dialog for removing a task
     * @param task Task to be removed
     */

    public static void removeTask(Task task) throws FileNotFoundException {
        ImageView imageView = new ImageView("Images/deleteAll.png");
        imageView.setFitWidth(70); imageView.setFitHeight(70);
        MainRegister register = App.getRegister();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog - Delete Item");
        alert.setContentText("Are you sure you want to delete this task?");
        alert.setGraphic(imageView);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            register.removeTask(task.getID());
            App.setRegister(register);
        }
    }

    /**
     * Creates a confirmation for deleting a category, also deletes all tasks associated with the category
     * @param categoryId Id of category to be deleted
     */

    public static void removeAllTasksInCategory(int categoryId){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog - Delete Item");
        alert.setContentText("Are you sure you want to delete ALL tasks in category? \n \nThis action can not be rolled back");
        MainRegister register = App.getRegister();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            App.getRegister().getAllTasks().stream()
                    .filter(MainTask -> MainTask.getCategoryId() == categoryId)
                    .forEach(MainTask -> register.removeTask(MainTask.getID()));
        }
    }

    /**
     * Creates a window for adding a new category
     */

    public static void addNewCategory(){
        CategoryDialog addNewDialog = new CategoryDialog();
        Optional<MainRegister> result = addNewDialog.showAndWait();
        if(result.isPresent()){
            MainRegister register = result.get();
            App.setRegister(register);
        }
    }

    /**
     * Creates a window for editing a category
     * @param selectedCategory Category to be edited
     */

    public static void editCategory(Category selectedCategory){
        CategoryDialog editDialog = new CategoryDialog(selectedCategory, true);
        Optional<MainRegister> result = editDialog.showAndWait();
        if(result.isPresent()){
            MainRegister register = result.get();
            App.setRegister(register);
        }
    }

    /**
     * Removes the selected category
     * @param selectedCategory Category to be removed
     * @throws RemoveException If it cant remove the selected category
     */

    public static void removeCategory(Category selectedCategory) throws RemoveException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog - Delete Item");
        alert.setContentText("Are you sure you want to delete this category?");
        alert.setContentText("Deleting this category will delete: \n ALL TASKS IN THIS CATEGORY");
        ImageView image = new ImageView("Images/deleteAll.png"); image.setFitHeight(70); image.setFitWidth(70);
        alert.setGraphic(image);

        MainRegister register = App.getRegister();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            App.getRegister().getAllTasks().stream()
                    .filter(MainTask -> MainTask.getCategoryId() == selectedCategory.getID())
                    .forEach(MainTask -> register.removeTask(MainTask.getID()));
            register.removeCategory(selectedCategory.getID());
            App.setRegister(register);
        }
    }
}
