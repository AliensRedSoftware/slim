/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slim.forms;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import slim.DBConnection;


/**
 * FXML Controller class
 *
 * @author merkus
 */
public class ChatController implements Initializable {
    
    protected Connection con = null; //подключение mysql
        
    @FXML
    private TextArea text;

    @FXML
    public TextField sending;

    @FXML
    private TextField search;

    @FXML
    private ListView<?> listuser;
    private ResultSet result;
    private String sql;
    private PreparedStatement pst;
    
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
           // LAL
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
             try {
                String text = this.search.getText().trim();
                this.sql = "SELECT * FROM `database`";
                this.pst = this.con.prepareStatement(this.sql);
                this.result = this.pst.executeQuery();
                while(this.result.next()){
                    if(this.result.getString(2).equals(text)){
                        new Alert(Alert.AlertType.INFORMATION, "Найден пользователь: " + text).showAndWait();
                        System.out.println(this.result.getString(2));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBConnection db = new DBConnection ();
        this.con = db.getConnection("127.0.0.1:3306", "space_1337", "root", "root"); //Подключение бд sql :)
    }    
}