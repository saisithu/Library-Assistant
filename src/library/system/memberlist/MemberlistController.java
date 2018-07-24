/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.memberlist;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.system.booklist.BooklistController;
import library.system.dao.MemberDAO;
import library.system.editMember.EditmemberController;
import library.system.model.Book;
import library.system.model.Member;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class MemberlistController implements Initializable {

    @FXML
    private TableView<Member> memberTable;
    @FXML
    private MenuItem editItem;
    @FXML
    private MenuItem deleteItem;
    @FXML
    private TableColumn<Member, Integer> idColumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> mobileColumn;
    @FXML
    private TableColumn<Member, String> addressColumn;
    
    private MemberDAO memberDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        memberDAO=new MemberDAO();
        initColumn();
        loadTableData();
    }   
    
    private void initColumn() {
         idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
          nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
           mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    }
    
    private void loadTableData() {
        
       
        try {
           ObservableList<Member> list =memberDAO.getMembers();
             memberTable.getItems().setAll(list);
        } catch (SQLException ex) {
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
        }     
       
    }

    @FXML
    private void loadEditView(ActionEvent event) throws IOException {
         Member selectedMember=memberTable.getSelectionModel().getSelectedItem();
        
        if(selectedMember==null){
            System.out.println("Please select the member which you want to delete");
            return;
        }
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/library/system/editMember/editmember.fxml"));
        
        
        Parent root = loader.load();
        EditmemberController controller=loader.getController();
        controller.setInitData(selectedMember);
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.initOwner(memberTable.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
        
        loadTableData();
    }

    @FXML
    private void deleteBook(ActionEvent event) {
          Member selectedMember=memberTable.getSelectionModel().getSelectedItem();
        
        if(selectedMember==null){
            System.out.println("Please select the member which you want to delete");
            return;
        }
        try {
            memberDAO.deleteMember(selectedMember.getId());
            memberTable.getItems().remove(selectedMember);
        } catch (SQLException ex) {
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
