/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.editMember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import library.system.dao.MemberDAO;
import library.system.editbook.EditbookController;
import library.system.model.Member;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class EditmemberController implements Initializable {

    @FXML
    private JFXTextField idField;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cacelBtn;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField mobileField;
    @FXML
    private JFXTextField addressField;
    
    private MemberDAO memberDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        memberDAO=new MemberDAO();
    }    

    @FXML
    private void editMemberInfo(ActionEvent event) {
         int id=Integer.parseInt(idField.getText());
        String name=nameField.getText();
        String mobile=mobileField.getText();
        String address=addressField.getText();
        
        if(name.isEmpty()||mobile.isEmpty()||address.isEmpty()){
            System.out.println("Please fill in all required fields");
            return;
        }
        
        Member member=new Member(id, name, mobile, address);
        
        try {
            memberDAO.updateMember(member);
            System.out.println("updating success");
            Stage stage=(Stage)nameField.getScene().getWindow();
            stage.close();    
        } catch (SQLException ex) {
            Logger.getLogger(EditbookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void closeWindow(ActionEvent event) {
         Stage stage=(Stage)nameField.getScene().getWindow();
            stage.close();    
    }

    public void setInitData(Member member) {
        idField.setDisable(true);
        idField.setText(Integer.toString(member.getId()));
        nameField.setText(member.getName());
        mobileField.setText(member.getMobile());
        addressField.setText(member.getAddress());
    }
    
}
