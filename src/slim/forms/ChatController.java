/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slim.forms;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


/**
 * FXML Controller class
 *
 * @author merkus
 */
public class ChatController implements Initializable {

    @FXML
    private TextArea text;

    @FXML
    public TextField sending;

    @FXML
    private TextField search;

    @FXML
    private ListView<?> listuser;
    
    /**
     * Отправка сообщение
     * @param event 
     */
    @FXML
    protected void SendMessage(ActionEvent event) {
        if (this.sending.getText().trim().isEmpty()) {
            this.sending.setText("");
            System.out.println("Отправить невозможно пустое!"); 
        } else {
            //Успешное отправка...
        }
    }
    
    /**
     * Пойск пользователя по нику
     * @param event 
     */
    @FXML
    void SearchToUser(ActionEvent event) {
        if (this.search.getText().trim().isEmpty()) {
            this.search.setText("");
            System.out.println("Найти пустого пользователя невозможно!"); 
        } else {
            //Успешное отправка...
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