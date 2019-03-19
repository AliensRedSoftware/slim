/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 *
 * @author merkus
 */
public class DBConnection {
    protected Connection connection = null;
    public Connection getConnection(String ip, String dbname, String UserName, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + dbname, UserName, password);
            System.out.println("[SQL => подключение успешно :)]");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("[SQL => Ошибка подключение :(]");
            new Alert(Alert.AlertType.ERROR, "SQL => Ошибка подключение :(").showAndWait();
            Platform.exit();
        }
        return this.connection;
    } 
}
