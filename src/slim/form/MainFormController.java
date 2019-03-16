/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slim.form;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author zero_two
 */
public class MainFormController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private Button Login;
    @FXML
    private Button register;
    @FXML
    private PasswordField pass;
    
    @FXML
    private void auth(ActionEvent event) {
        String name = this.name.getText().trim();
        String password = this.pass.getText().trim();
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите логин для входа").showAndWait();
            this.name.setText("");
        } else if (password.isEmpty()) {
           new Alert(Alert.AlertType.ERROR, "Пожалуйста введите пароль для входа").showAndWait(); 
           this.pass.setText("");
        } else {
            
        }
    }
    
    @FXML
    void register(ActionEvent event) {
        String name = this.name.getText().trim();
        String password = this.pass.getText().trim();
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите логин для регестраций").showAndWait();
            this.name.setText("");
        } else if (password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите пароль для регестраций").showAndWait();
            this.pass.setText("");
        } else {
            
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
