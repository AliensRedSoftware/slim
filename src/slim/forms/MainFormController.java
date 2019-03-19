/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slim.forms;

import java.io.IOException;
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
    protected TextField name;
    @FXML
    protected Button Login;
    @FXML
    protected Button register;
    @FXML
    protected PasswordField pass;
    
    protected String sql = "SELECT * FROM `database`";
    
    protected Connection con = null; //подключение mysql
    
    protected PreparedStatement pst = null;
    
    protected ResultSet result = null;
    
    protected Boolean successLogin = false , successPassword = false;
    
    /**
     * Авторизация пользователя
     * @param event
     * @throws Exception 
     */
    @FXML
    protected void auth(ActionEvent event) throws Exception {
        if (this.name.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите логин для входа").showAndWait();
            this.name.setText("");
        } else if (this.pass.getText().trim().isEmpty()) {
           new Alert(Alert.AlertType.ERROR, "Пожалуйста введите пароль для входа").showAndWait(); 
           this.pass.setText("");
        } else {
            try {
                try {
                    this.sql = "SELECT * FROM `database`";
                    this.pst = this.con.prepareStatement(this.sql);
                    this.result = this.pst.executeQuery();
                    while(this.result.next()) {
                        if (this.name.getText().equals(this.result.getString(2)) && this.pass.getText().equals(this.result.getString(3))) { //Проверка на логинность
                            this.successLogin = true;
                            this.successPassword = true;
                            break;
                        } else {
                            this.successLogin = false;
                            this.successPassword = false;
                        }
                    }
                    if (this.successLogin == true && this.successPassword == true) {
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
                    this.pst.cancel();
                } catch (SQLException ex) {
                    System.out.println("[SQL => Ошибка запроса]");
                }
            } catch (IOException e) {
                System.out.println("Ошибка!");
            }
        }
    }
    
    /**
     * Регестрация пользователя
     * @param event
     * @throws SQLException 
     */
    @FXML
    protected void register(ActionEvent event) throws SQLException {
        if (this.name.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите логин для регестраций").showAndWait();
            this.name.setText("");
        } else if (this.pass.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите пароль для регестраций").showAndWait();
            this.pass.setText("");
        } else if (this.name.getText().length() >= 128 || this.name.getText().length() < 3) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите логин не больше 128 символов и не меньше 3 символов").showAndWait();
        } else if (this.pass.getText().length() >= 128 || this.pass.getText().length() < 8) {
            new Alert(Alert.AlertType.ERROR, "Пожалуйста введите пароль не больше 128 символов и не меньше 8 символов").showAndWait();
        } else {
            Boolean checkauth = this.checkRegister(this.name.getText());
            if (checkauth == true) {
                this.sql = "INSERT INTO `database` (`id`, `name`, `password`) VALUES (NULL," + "'" + this.name.getText().trim() + "'" + "," + "'" + this.pass.getText().trim() + "'" + ")";
                try {
                    this.pst = this.con.prepareStatement(this.sql);
                    if (this.pst.executeUpdate() == 1) {
                        System.out.println("[SQL => Новый запрос на регестрацию] => Успешно :)");
                        this.name.setText("");
                        this.pass.setText("");
                        new Alert (Alert.AlertType.INFORMATION, "Вы успешно прошли регестрацию :)").showAndWait();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally {
                    this.pst.cancel();
                }
            } else {
                System.out.println("[SQL => Регестрация невозможно так как такой пользователь уже есть!] => Условная ошибка  :/");
                new Alert(Alert.AlertType.ERROR, "Пожалуйста, придумайте другое имя так как такой пользователь уже зарегистрирован").showAndWait();
            }
        } 
    }
    
    /**
     * Вход в аккаунт используя enter
     * @param event 
     */
    @FXML
    private void AuthKeyEnter(ActionEvent event) throws Exception {
        this.auth(event);
    }
    
    /**
     * Проверка есть такой пользователь или нет
     */
    Boolean checkRegister (String username) {
        try {
            this.pst = this.con.prepareStatement(this.sql);
            this.result = this.pst.executeQuery();
            while (this.result.next()){
                if (username.equals(this.result.getString(2))) {
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
