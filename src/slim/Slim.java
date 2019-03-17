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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author zero_two
 */
public class Slim extends Application {
    
    
    @Override
    public void start(Stage MainForm) throws Exception {
        MainForm.setTitle("Slim"); //Установка загаловка
        MainForm.centerOnScreen(); //Показать по середине
        MainForm.setResizable(false); //Убрать растягивание формы
        Parent root = FXMLLoader.load(getClass().getResource("forms/MainForm.fxml"));
        
        Scene scene = new Scene(root);
        
        MainForm.setScene(scene);
        MainForm.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
