package ntnu.team1;

import ntnu.team1.application.exceptions.RemoveException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MainRegisterTest {

    @Nested
    class Category{

        @Nested
        class addCategory{
            @Test
            void addCategoryPositive() {
                MainRegister register = new MainRegister();
                assertTrue(register.addCategory("test", Color.pink));
            }
            @Test
            void addCategoryNegative() {
                MainRegister register = new MainRegister();
                register.addCategory("test", Color.pink);
                assertFalse(register.addCategory("test", Color.pink));
            }
        }

        @Nested
        class removeCategory{
            @Test
            void removeCategoryPositive(){
                MainRegister register = new MainRegister();
                register.addCategory("test", Color.pink);
                try{
                    register.removeCategory(0);
                }catch(RemoveException e){
                }
                assert(register.getCategories().size() == 0);
            }

            @Test
            void removeCategoryNegative(){
                MainRegister register = new MainRegister();
                assertThrows(RemoveException.class, ()-> register.removeCategory(0));
            }
        }
    }

    @Nested
    class MainTask{

        @Nested
        class getMainTask{
            @Test
            void getMainTaskPositive(){
                MainRegister register = new MainRegister();
                register.addMainTask(null, null, "test", "description", 1, -1);
                assertNotNull(register.getMainTask(0));
            }

            @Test
            void getMainTaskNegative(){
                MainRegister register = new MainRegister();
                register.addMainTask(null, null, "test", "description", 1, -1);
                assertNull(register.getMainTask(1));
            }
        }

        @Nested
        class addMainTask{
            @Test
            void addMainTaskPositive() {
                MainRegister register = new MainRegister();
                register.addMainTask(LocalDate.now(), LocalDate.now(),"Task_1",
                        "Lorem Ipsum", 0,0);

                assertNotNull(register.getMainTask(0));
            }

            @Test
            void addMainTaskNegative() {
                MainRegister register = new MainRegister();
                assertThrows(NullPointerException.class, ()-> register.addMainTask(null, null,
                        null, "Lorem Ipsum",1 ,1));
            }
        }
        @Nested
        class removeMainTask{

            @Test
            void removeMainTaskPositive() {
                MainRegister register = new MainRegister();

                register.addCategory("Kategori_1", Color.pink);
                register.addCategory("Kategori_2", Color.blue);
                register.addCategory("Kategori_3", Color.red);
                register.addCategory("Kategori_4", Color.green);

                for(int i = 0; i<=10;i++) {
                    String name = "task " + i;
                    String description = "Lorem Ipsum";
                    Random random = new Random();
                    register.addMainTask(null, null, name, description, random.nextInt(3), random.nextInt(3));
                }
                assertNotNull(register.getMainTask(1));

                System.out.println(register.getAllTasks());

                try{
                    register.removeMainTask(1);
                }catch (IllegalArgumentException e){
                    assertNull(e);
                }

                assertThrows(IllegalArgumentException.class, () -> register.getMainTask(1));
            }
            @Test
            void removeMainTaskNegative()  {
                MainRegister register = new MainRegister();

                assertThrows(IllegalArgumentException.class, () -> register.removeMainTask(1));
            }
        }

        @Nested
        class changePriority{
            @Test
            void changePriority(){
                MainRegister register = new MainRegister();
                register.addMainTask(null, null, "test", "description", 1, -1);

                assertEquals(1, register.getMainTask(0).getPriority());
                register.changePriorityMainTask(0,2);
                assertEquals(2, register.getMainTask(0).getPriority());
            }
        }
    }

    @Test
    void setTaskCategory() {
    }

    @Test
    void sortByPriority() {
    }

    @Test
    void sortByCategory() {
    }
}