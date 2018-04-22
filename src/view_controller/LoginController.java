/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.DBManager;

/**
 * FXML Controller class
 *
 * @author mcken
 */
public class LoginController implements Initializable {


    private String englishLocale = "resources/en_EN";
    private String frenchLocale = "resources/fr_FR";

    private DBManager dbManager;
    ResourceBundle rb = ResourceBundle.getBundle(englishLocale, Locale.getDefault());

    @FXML
    private TextField usernameTextField;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField passwordTextField;
    @FXML
    private AnchorPane root;
    @FXML
    Button submitButton;
    @FXML
    Button exitButton;
    @FXML
    Label loginLabel;
    @FXML
    RadioButton englishRadioButton;
    @FXML
    RadioButton frenchRadioButton;

//    REQ A.  Create a log-in form that can determine the user’s location and translate log-in and error control messages (e.g., “The username and password did not match.”) into two languages.

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage();
    }

    private void setLanguage() {
        ToggleGroup toggleGroup = new ToggleGroup();

        this.englishRadioButton.setToggleGroup(toggleGroup);
        this.frenchRadioButton.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                if (toggleGroup.getSelectedToggle().equals(englishRadioButton)) {
                    rb = ResourceBundle.getBundle(englishLocale, Locale.getDefault());
                    setText();
                } else {
                    rb = ResourceBundle.getBundle(frenchLocale, Locale.getDefault());
                    setText();
                }
            }
        });

        setText();
    }

    private void setText() {
        loginLabel.setText(rb.getString("login_label"));
        usernameLabel.setText(rb.getString("username_label"));
        passwordLabel.setText(rb.getString("password_label"));
        submitButton.setText(rb.getString("submit_button_label"));
        exitButton.setText(rb.getString("exit_button_label"));
    }

    @FXML
    public void submitButtonPressed(ActionEvent event) throws IOException {
        if (DBManager.isLoginValid(usernameTextField.getText(), passwordTextField.getText())) {
            try {
                // Show main screen
                Parent mainScreenParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene mainScreenScene = new Scene(mainScreenParent);
                Stage mainScreenStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mainScreenStage.setScene(mainScreenScene);
                mainScreenStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            //TODO: figure out how to correctly path to the langauge files.
            alert.setTitle(rb.getString("invalid_entry"));
            alert.setHeaderText(rb.getString("password_username_mismatch"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                alert.close();
            }
        }
    }

    @FXML
    public void exitButtonPressed(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(rb.getString("confirm_exit"));
        alert.setHeaderText(rb.getString("confirm_dialogue"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            alert.close();
        }
    }

}
