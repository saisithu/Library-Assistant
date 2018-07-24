/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.system.dao.BookDAO;
import library.system.dao.IssueDAO;
import library.system.dao.MemberDAO;
import library.system.model.Book;
import library.system.model.IssueInfo;
import library.system.model.Member;
import library.system.util.MessageBox;

/**
 *
 * @author Dell
 */
public class MainController implements Initializable {

    @FXML
    private Button homeBtn;
    @FXML
    private Button addBookBtn;
    @FXML
    private StackPane centerPane;
    @FXML
    private TabPane homeView;
    @FXML
    private JFXButton bookListBtn;
    @FXML
    private JFXButton memberListBtn;
    @FXML
    private JFXButton addMemberBtn;
    @FXML
    private JFXTextField searchBookIdField;
    @FXML
    private Text titleText;
    @FXML
    private Text authorText;
    @FXML
    private Text publisherText;
    @FXML
    private Text availableText;
    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private IssueDAO issueDAO;
    @FXML
    private JFXTextField searchMemberIdField;
    @FXML
    private Text nameText;
    @FXML
    private Text mobileText;
    @FXML
    private Text addressText;
    @FXML
    private JFXButton issueBtn;
    @FXML
    private JFXTextField issuedBookSearch;
    @FXML
    private Text bTitleText;
    @FXML
    private Text bAuthorText;
    @FXML
    private Text bPublisherText;
    @FXML
    private Text mNameText;
    @FXML
    private Text mMobileText;
    @FXML
    private Text mAddressText;
    @FXML
    private Text issuedDateText;
    @FXML
    private Text renewCountText;
    @FXML
    private JFXButton returnBtn;
    @FXML
    private JFXButton renewBtn;
    @FXML
    private JFXButton issuedInfoBtn;
    @FXML
    private MenuItem dbConfigItem;

    private final String defaultStyle = "-fx-border-width:0 0 0 5px; -fx-border-color:#263238";
    private final String activetStyle = "-fx-border-width:0 0 0 5px; -fx-border-color:#eceff1";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeActive();
        bookDAO = new BookDAO();
        memberDAO = new MemberDAO();
        issueDAO = new IssueDAO();
    }

    @FXML
    private void loadHomeView(ActionEvent event) {
        homeActive();
        centerPane.getChildren().clear();
        centerPane.getChildren().add(homeView);
    }

    @FXML
    private void loadAddBookView(ActionEvent event) throws IOException {
        addBookActive();
        loadView("/library/system/addbook/addbook.fxml");

    }

    @FXML
    private void loadBookListView(ActionEvent event) throws IOException {
        bookListActive();
        loadView("/library/system/booklist/booklist.fxml");

    }

    @FXML
    private void loadAddMemberView(ActionEvent event) throws IOException {
        memberActive();
        loadView("/library/system/addMember/addmember.fxml");

    }

    @FXML
    private void loadMemberListView(ActionEvent event) throws IOException {
        memberListActive();
        loadView("/library/system/memberlist/memberlist.fxml");

    }

    @FXML
    private void loadIssuedInfoView(ActionEvent event) throws IOException {
        issuedInfoActive();
        loadView("/library/system/issuedInfoList/issuedInfoList.fxml");
    }

    @FXML
    private void searchBookInfo(ActionEvent event) {
        clearBookCache();
        String idStr = searchBookIdField.getText();

        if (idStr.isEmpty()) {
            MessageBox.showErrorMessage("Error", "Please enter the book id first");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);

            Book book = bookDAO.getBook(id);

            if (book != null) {
                String title = book.getTitle();
                String author = book.getAuthor();
                String publisher = book.getPublisher();
                boolean available = book.isAvailable();

                titleText.setText(title);
                authorText.setText(author);
                publisherText.setText(publisher);

                if (available) {
                    availableText.setText("Available");
                } else {
                    availableText.setText("NotAvailable");
                }
            } else {
                MessageBox.showErrorMessage("Error", "Can't fing book for this id");
            }
        } catch (NumberFormatException e) {
            MessageBox.showErrorMessage("Error", "Invalid input for book id");
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void searchMemberInfo(ActionEvent event) {
        clearMemberCache();
        String idStr = searchMemberIdField.getText();

        if (idStr.isEmpty()) {
            MessageBox.showErrorMessage("Error", "Please enter the book id first");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);

            Member member = memberDAO.getMember(id);

            if (member != null) {
                String name = member.getName();
                String mobile = member.getMobile();
                String address = member.getAddress();

                nameText.setText(name);
                mobileText.setText(mobile);
                addressText.setText(address);
            } else {
                MessageBox.showErrorMessage("Error", "Can't find member for this id");
            }
        } catch (NumberFormatException e) {
            MessageBox.showErrorMessage("Error", "Invalid input for book id");
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearBookCache() {
        titleText.setText("-");
        authorText.setText("-");
        publisherText.setText("-");
        availableText.setText("-");

    }

    private void clearMemberCache() {
        nameText.setText("-");
        mobileText.setText("-");
        addressText.setText("-");
    }

    @FXML
    private void issueBook(ActionEvent event) {

        String bookIdStr = searchBookIdField.getText();
        String memberIdStr = searchMemberIdField.getText();

        if (bookIdStr.isEmpty() || memberIdStr.isEmpty()) {
            MessageBox.showErrorMessage("Error", "Please fill book id and member id first");
            return;
        }
        int bookId;
        int memberId;

        try {
            bookId = Integer.parseInt(bookIdStr);
            memberId = Integer.parseInt(memberIdStr);
        } catch (NumberFormatException e) {
            return;
        }

        try {
            Book book = bookDAO.getBook(bookId);
            if (book.isAvailable()) {
                issueDAO.saveIssueInfo(new IssueInfo(bookId, memberId));
                bookDAO.updateAvailable(bookId, false);
            } else {
                MessageBox.showErrorMessage("Error", "This book is already issued");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void searchIssuedBook(ActionEvent event) {

        clearIssuedBookInfo();

        String bookIdStr = issuedBookSearch.getText();

        if (bookIdStr.isEmpty()) {
            MessageBox.showErrorMessage("Error", "Please fill in issued book id field");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            MessageBox.showErrorMessage("Error", "Invalid number format");
            return;
        }

        try {
            IssueInfo issueInfo = issueDAO.getIssueInfo(bookId);
            if (issueInfo != null) {
                Book book = bookDAO.getBook(issueInfo.getBookId());
                Member member = memberDAO.getMember(issueInfo.getMemberId());

                bTitleText.setText(book.getTitle());
                bAuthorText.setText(book.getAuthor());
                bPublisherText.setText(book.getPublisher());

                mNameText.setText(member.getName());
                mMobileText.setText(member.getMobile());
                mAddressText.setText(member.getAddress());

                issuedDateText.setText("Issued Date: " + issueInfo.getIssueDate());
                renewCountText.setText("Renew Counnt: " + issueInfo.getRenewCount());
            } else {

                MessageBox.showErrorMessage("Error", "Can't find issued book for this id");
            }
        } catch (SQLException e) {
            System.out.println("You will be forever alone");

        }
    }

    @FXML
    private void returnBook(ActionEvent event) {
        String bookIdStr = issuedBookSearch.getText();

        if (bookIdStr.isEmpty()) {
            MessageBox.showErrorMessage("Error", "Please fill in issued book id field");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            MessageBox.showErrorMessage("Error", "Invalid number format");
            return;
        }

        try {
            IssueInfo issueInfo = issueDAO.getIssueInfo(bookId);
            if (issueInfo != null) {

                Optional<ButtonType> option = MessageBox.showConfirmMessage("Confirmation", "Are you sure you want to return this book");
                if (option.get() == ButtonType.OK) {
                    issueDAO.deleteIssueInfo(bookId);
                    bookDAO.updateAvailable(bookId, true);
                } else {
                    MessageBox.showErrorMessage("Error", "Can't find issued book for this id");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void renewBook(ActionEvent event) {
        String bookIdStr = issuedBookSearch.getText();

        if (bookIdStr.isEmpty()) {
            MessageBox.showErrorMessage("Error", "Please fill in issued book id field");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            MessageBox.showErrorMessage("Error", "Invalid number format");
            return;
        }

        try {
            IssueInfo issueInfo = issueDAO.getIssueInfo(bookId);
            if (issueInfo != null) {
                //int renew=issueInfo.getRenewCount()+1;

                issueDAO.renewIssuedBook(bookId);
                System.out.println("After renewing");
            } else {
                MessageBox.showErrorMessage("Error", "Can't renew");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearIssuedBookInfo() {
        bTitleText.setText("-");
        bAuthorText.setText("-");
        bPublisherText.setText("-");

        mNameText.setText("-");
        mMobileText.setText("-");
        mAddressText.setText("-");

        issuedDateText.setText("-");
        renewCountText.setText("-");

    }

    private void loadView(String url) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));

        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
    }

    @FXML
    private void loadDbConfigView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/library/system/config/dbconfig.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initOwner(centerPane.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);

        stage.show();
    }

    private void homeActive() {
        homeBtn.setStyle(activetStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        addMemberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        issuedInfoBtn.setStyle(defaultStyle);

    }

    private void addBookActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(activetStyle);
        bookListBtn.setStyle(defaultStyle);
        addMemberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        issuedInfoBtn.setStyle(defaultStyle);

    }

    private void bookListActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(activetStyle);
        addMemberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        issuedInfoBtn.setStyle(defaultStyle);

    }

    private void memberActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        addMemberBtn.setStyle(activetStyle);
        memberListBtn.setStyle(defaultStyle);
        issuedInfoBtn.setStyle(defaultStyle);

    }

    private void memberListActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        addMemberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(activetStyle);
        issuedInfoBtn.setStyle(defaultStyle);

    }

    private void issuedInfoActive() {
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        bookListBtn.setStyle(defaultStyle);
        addMemberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        issuedInfoBtn.setStyle(activetStyle);

    }

}
