package slim.forms;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    protected Connection con = null; // подключение mysql

    @FXML
    private TextArea text;

    @FXML
    public TextField sending, search;

    @FXML
    private ListView<String> listuser;
    private ResultSet result;
    private String sql,searchText;
    private PreparedStatement pst;

    private final ObservableList<String> userdata = FXCollections.observableArrayList();// лист к listview
    
    protected DBConnection db = new DBConnection();

    /**
     * Отправка сообщение
     * 
     * @param event
     */
    @FXML
    protected void SendMessage(ActionEvent event) {
        if (this.sending.getText().trim().isEmpty()) {
            this.sending.setText("");
            System.out.println("Отправить невозможно пустое!");
        } else {
            if (!this.listuser.getSelectionModel().getSelectedItem().equals(null)) {
                System.out.println("Сообщение отправлено:" + this.listuser.getSelectionModel().getSelectedItem());

            } else {
                System.out.println("Отправить сообщение не удается так как нужно выбрать пользователя");
            }
        }
    }

    /**
     * Пойск пользователя по нику
     * 
     * @param event
     */
    @FXML
    void SearchToUser(ActionEvent event) {
        if (this.search.getText().trim().isEmpty()) {
            this.search.setText("");
            System.out.println("Найти пустого пользователя невозможно!");
        } else {
            try {
                // Перемычка
                this.searchText = this.search.getText().trim();
                this.listuser.setItems(this.userdata);
                // Смычка )
                this.sql = "SELECT * FROM `database`";
                this.pst = this.con.prepareStatement(this.sql);
                this.result = this.pst.executeQuery();
                while (this.result.next()) {
                    if (this.result.getString(2).equals(this.searchText)) {
                        System.out.println("Найден пользователь: [ " + this.result.getString(2) + " ]");
                        if (Arrays.asList(this.userdata).contains(this.searchText)) {
                            new Alert(Alert.AlertType.ERROR, "Найденый пользователь уже добавлен в список:  " + this.searchText).showAndWait();
                        } else {
                            this.userdata.add(this.searchText);
                            new Alert(Alert.AlertType.INFORMATION, "Пользователь: " + this.searchText + " успешно добавлен").showAndWait();
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                this.search.setText("");
            }
        }
    }

    /**
     * Initializes the controller class.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.con = this.db.getConnection(); // Подключение бд sql :)
    }
}