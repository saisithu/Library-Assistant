/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.config;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.system.model.DbConfig;
import library.system.util.DbconfigLoader;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class DbconfigController implements Initializable {

    @FXML
    private TextField hostField;
    @FXML
    private Spinner<Integer> portSpinner;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        DbConfig dbConfig=DbconfigLoader.loadDbConfig();
        if(dbConfig==null){
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3350, 3306);
        portSpinner.setValueFactory(valueFactory);
        }else{
        hostField.setText(dbConfig.getHost());
        userNameField.setText(dbConfig.getUser());
        passwordField.setText(dbConfig.getPassword());
        

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3350,dbConfig.getPort());
        portSpinner.setValueFactory(valueFactory);
        }
        

    }

    @FXML
    private void saveDatabaseConfig(ActionEvent event) {

        String host = hostField.getText();
        String port = portSpinner.getValue().toString();
        String user = userNameField.getText();
        String password = passwordField.getText();
        
        DbconfigLoader.saveDbConfig(new DbConfig(host,Integer.parseInt(port),user,password));
         Stage stage=(Stage)hostField.getScene().getWindow();
        stage.close();
        

    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage=(Stage)hostField.getScene().getWindow();
        stage.close();
    }

}
