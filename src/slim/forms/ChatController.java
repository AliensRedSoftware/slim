/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slim.forms;

import java.awt.TextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


/**
 * FXML Controller class
 *
 * @author merkus
 */
public class ChatController implements Initializable {

    @FXML
    private javafx.scene.control.TextArea text;

    @FXML
    public TextField sending;

    @FXML
    private TextField search;

    @FXML
    private ListView<?> listuser;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void sendingmsg(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            if (this.sending.getText().trim().isEmpty()) {
                this.sending.setText("");
                System.out.print("Отправить невозможно пустое!"); 
                new Alert(Alert.AlertType.ERROR, "Введите текст для отправки сообщение...").showAndWait();
            } else {
                //Успешное отправка...
            }
        }
    }
    
}
