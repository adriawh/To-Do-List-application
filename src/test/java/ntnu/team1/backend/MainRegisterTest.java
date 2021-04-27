package ntnu.team1.backend;

import ntnu.team1.backend.exceptions.RemoveException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainRegisterTest {

    @Nested
    public class Category{

        @Nested
        public class addCategory{
            @Test
            public void addCategoryPositive() {
                MainRegister register = new MainRegister();
                assertTrue(register.addCategory("test", Color.PINK));
            }
        }

        @Nested
        public class removeCategory{
            @Test
            public void removeCategoryPositive(){
                MainRegister register = new MainRegister();
                register.addCategory("test", Color.PINK);
                try{
                    register.removeCategory(0);
                }catch(RemoveException e){
                    assertNull(e);
                }
                System.out.println(register.getCategories().size());
                assert(register.getCategories().size() == 1);
            }

            @Test
            public void removeCategoryNegative(){
                MainRegister register = new MainRegister();
                assertThrows(RemoveException.class, ()-> register.removeCategory(0));
            }
        }

        @Nested
        public class setCategoryColor{

            @Test
            void setCategoryColorPositive(){
                MainRegister register = new MainRegister();
                assertTrue(register.addCategory("test", Color.PINK));

                register.setCategoryColor(0,Color.GREEN);
                assertEquals(register.getCategory(0).getColor(), Color.GREEN);
            }

            @Test
            public void setCategoryColorNegative(){
                MainRegister register = new MainRegister();
                assertTrue(register.addCategory("test", Color.PINK));

                assertThrows(IllegalArgumentException.class, () -> register.setCategoryColor(1,Color.GREEN));
            }
        }

    }

    @Nested
    public class Task {

        @Nested
        public class getTask {
            @Test
            public void getMainTaskPositive(){
                MainRegister register = new MainRegister();
                register.addMainTask(null, null, "test", "description", 1, -1);
                assertNotNull(register.getMainTask(0));
            }

            @Test
            public void getMainTaskNegative(){
                MainRegister register = new MainRegister();
                register.addMainTask(null, null, "test", "description", 1, -1);
                assertThrows(IllegalArgumentException.class, () -> register.getMainTask(1));
            }
        }

        @Nested
        public class addTask {
            @Test
            public void addMainTaskPositive() {
                MainRegister register = new MainRegister();
                register.addMainTask(LocalDate.now(), LocalDate.now(),"Task_1",
                        "Lorem Ipsum", 0,0);

                assertNotNull(register.getMainTask(0));
            }

            @Test
            public void addMainTaskNegative() {
                MainRegister register = new MainRegister();
                assertThrows(NullPointerException.class, ()-> register.addMainTask(null, null,
                        null, "Lorem Ipsum",1 ,1));
            }
        }

        @Nested
        public class removeTask {

            @Test
            public void removeMainTaskPositive() {
                MainRegister register = new MainRegister();

                register.addCategory("Kategori_1", Color.PINK);
                register.addCategory("Kategori_2", Color.BLUE);
                register.addCategory("Kategori_3", Color.RED);
                register.addCategory("Kategori_4", Color.GREEN);

                for(int i = 0; i<=10;i++) {
                    String name = "task " + i;
                    String description = "Lorem Ipsum";
                    Random random = new Random();
                    register.addMainTask(null, null, name, description, random.nextInt(3), random.nextInt(3));
                }
                assertNotNull(register.getMainTask(1));

                try{
                    register.removeMainTask(1);
                }catch (IllegalArgumentException e){
                    assertNull(e);
                }

                assertThrows(IllegalArgumentException.class, () -> register.getMainTask(1));
            }
            @Test
            public void removeMainTaskNegative(){
                MainRegister register = new MainRegister();

                assertThrows(IllegalArgumentException.class, () -> register.removeMainTask(1));
            }
        }

        @Nested
        public class changePriority{
            @Test
            public void changePriorityPositive(){
                MainRegister register = new MainRegister();
                register.addMainTask(null, null, "test", "description", 1, -1);

                assertEquals(1, register.getMainTask(0).getPriority());
                register.changePriorityMainTask(0,2);
                assertEquals(2, register.getMainTask(0).getPriority());
            }
        }

        @Nested
        public class getAllTasksFromCategory{

            @Test
            public void getAllTasksFromCategoryPositive(){
                MainRegister register = new MainRegister();
                String name = "task";
                String description = "Lorem Ipsum";
                Random random = new Random();

                register.addCategory("Kategori_1", Color.PINK);
                register.addCategory("Kategori_2", Color.BLUE);
                register.addCategory("Kategori_3", Color.RED);
                register.addCategory("Kategori_4", Color.GREEN);

                register.addMainTask(null, null, name, description, random.nextInt(3), 2);
                register.addMainTask(null, null, name, description, random.nextInt(3), 2);
                register.addMainTask(null, null, name, description, random.nextInt(3), 0);
                register.addMainTask(null, null, name, description, random.nextInt(3), 1);

                assert(register.getAllTasksFromCategory(2).size() == 2);
            }
            @Test
            public void getAllTasksFromCategoryNegative(){
                MainRegister register = new MainRegister();
                assertThrows(IllegalArgumentException.class, () -> register.getAllTasksFromCategory(1));
            }
        }

        @Nested
        public class setTaskCategory {
            @Test
            public void setMainTaskCategoryPositive(){

                try{
                    MainRegister register = new MainRegister();
                    register.addMainTask(null, null, "test", "description", 1, -1);
                    register.addCategory("Name", Color.PINK);

                    register.setMainTaskCategory(0, 0);

                    assert(register.getMainTask(0).getCategoryId() == 0);

                }catch(IllegalArgumentException e){
                    assertNull(e.getMessage());

                }
            }

            @Test
            public void setMainTaskCategoryNegative(){

                try{
                    MainRegister register = new MainRegister();
                    register.addMainTask(null, null, "test", "description", 1, -1);

                    register.setMainTaskCategory(0, 1);

                }catch(IllegalArgumentException e){
                    assertEquals(e.getMessage(), "Category does not exist");
                }
            }
        }

        @Nested
        public class changeDescriptionTask {

            @Test
            public void changeDescriptionMainTaskPositive() {
                try {

                    MainRegister register = new MainRegister();
                    register.addMainTask(null, null, "test", "description", 1, -1);
                    register.changeDescriptionMainTask(0, "New description");
                    assertEquals(register.getMainTask(0).getDescription(), "New description");

                } catch (IllegalArgumentException e) {
                    assertNull(e.getMessage());
                }
            }

            @Test
            public void changeDescriptionMainTaskNegative() {
                try {
                    MainRegister register = new MainRegister();
                    register.addMainTask(null, null, "test", "description", 1, -1);
                    register.changeDescriptionMainTask(1, "New description");
                } catch (IllegalArgumentException e) {
                    assertEquals(e.getMessage(), "No task found with the suggested Id.");
                }
            }
        }
    }
}