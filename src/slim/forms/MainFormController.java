/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slim.forms;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import slim.DBConnection;

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
    
    private Connection con = null; 
    private PreparedStatement pst = null;
    
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
    void register(ActionEvent event) throws SQLException {
        String name = this.name.getText().trim();
        String password = this.pass.getText().trim();
        name.length();
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите логин для регестраций").showAndWait();
            this.name.setText("");
        } else if (password.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите пароль для регестраций").showAndWait();
            this.pass.setText("");
        } else if (name.length() >= 128 || name.length() < 3) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите логин не больше 128 символов и не меньше 3 символов").showAndWait();
        } else if (password.length() >= 128 || password.length() < 8) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите пароль не больше 128 символов и не меньше 8 символов").showAndWait();
        } else {
            String sql = "INSERT INTO `database` (`id`, `name`, `password`) VALUES (NULL," + "'" + name + "'" + "," + "'" + password + "'" + ")";
            try {
                pst = con.prepareStatement(sql);
                int i = pst.executeUpdate();
                if (i == 1) {
                    System.out.println("[SQL => Новый запрос на регестрацию] => Успешно :)");
                    this.name.setText("");
                    this.pass.setText("");
                    new Alert (Alert.AlertType.INFORMATION, "Вы успешно прошли регестрацию :)").showAndWait();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally {
                pst.cancel();
            }
        }
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBConnection db = new DBConnection ();
        con = db.getConnection("127.0.0.1:3306", "space_1337", "root", "root"); //Подключение бд sql :)
    }    
}
