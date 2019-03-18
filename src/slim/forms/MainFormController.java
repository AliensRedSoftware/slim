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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    
    private Connection con = null; //подключение mysql
    
    private PreparedStatement pst = null;
    private ResultSet result = null;
    
    @FXML
    private void auth(ActionEvent event) throws Exception {
        String name = this.name.getText().trim();
        String password = this.pass.getText().trim();
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите логин для входа").showAndWait();
            this.name.setText("");
        } else if (password.isEmpty()) {
           new Alert(Alert.AlertType.ERROR, "Пожалуйста введите пароль для входа").showAndWait(); 
           this.pass.setText("");
        } else {
            try {
                try {
                    String sql = "SELECT * FROM `database`";
                    Boolean successLogin = false;
                    Boolean successPassword = false;
                    pst = this.con.prepareStatement(sql);
                    result = pst.executeQuery();
                    
                    while (result.next()) {
                        String getName = result.getString(2);
                        String getPassword = result.getString(3);
                        if (name.equals(getName) && password.equals(getPassword)) { //Проверка на логинность
                            successLogin = true;
                            successPassword = true;
                            break;
                        } else {
                            successLogin = false;
                            successPassword = false;
                        }
                    }
                    if (successLogin == true && successPassword == true) {
                        System.out.println("[Аккаунт => успешный был вход!]");
                        new Alert(Alert.AlertType.INFORMATION, "Успешный был вход!").showAndWait();
                        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("chat.fxml"));
                        Parent rootl = (Parent) fxmlloader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Chat");
                        stage.centerOnScreen(); //Показать по середине
                        stage.setScene(new Scene(rootl));
                        stage.show();
                        Node source = (Node) event.getSource();
                        Window MainForm = source.getScene().getWindow();
                        MainForm.getScene().getWindow().hide();
                    }
                    else {
                        System.out.println("[Аккаунт => неверный логин]");
                        new Alert(Alert.AlertType.ERROR, "Неверный логин и пароль!").showAndWait();    
                    }
                } catch (SQLException ex) {
                    System.out.println("[SQL => Ошибка запроса]");
                }
            } catch (Exception e) {
                System.out.println("Ошибка!");
            }
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
            Boolean checkauth = this.checkRegister(name);
            if (checkauth == true) {
                String sql = "INSERT INTO `database` (`id`, `name`, `password`) VALUES (NULL," + "'" + name + "'" + "," + "'" + password + "'" + ")";
                try {
                    pst = this.con.prepareStatement(sql);
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
            } else {
                System.out.println("[SQL => Регестрация невозможно так как такой пользователь уже есть!] => Условная ошибка  :/");
                new Alert(Alert.AlertType.ERROR, "Пожалуйста, придумайте другое имя так как такой пользователь уже зарегистрирован").showAndWait();
            }
        } 
    }
    
    /**
     * Проверка есть такой пользователь или нет
     */
    Boolean checkRegister (String username) {
        try {
            String sql = "SELECT * FROM `database`";
            pst = this.con.prepareStatement(sql);
            result = pst.executeQuery();
            while (result.next()){
                String getName = result.getString(2);
                if (username.equals(getName)) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("[SQL => Ошибка запроса]");
        }
        return true;
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
