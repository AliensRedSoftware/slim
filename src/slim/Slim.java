package slim;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author zero_two
 */
public class Slim extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Slim"); //Установка загаловка
        stage.centerOnScreen(); //Показать по середине
        stage.setResizable(false); //Убрать растягивание формы
        Parent root = FXMLLoader.load(getClass().getResource("forms/MainForm.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
