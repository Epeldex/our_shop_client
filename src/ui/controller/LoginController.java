package ui.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import animatefx.animation.*;
import animatefx.animation.partial.Contract;
import animatefx.animation.partial.Expand;
import exceptions.EmptyFieldException;
import exceptions.IncorrectFormatException;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;

public class LoginController extends GenericController {

    /**
     * Label for displaying error messages for both login and password.
     */
    @FXML
    private Text usernameErrorText, passwordErrorText;
    /**
     * ImageViews used for the window aesthetic.
     */
    @FXML
    private ImageView usernameErrorImage, passwordErrorImage, showPasswordImage;
    /**
     * Text fields where user will input the login password.
     */
    @FXML
    private TextField usernameTextField, passwordTextField;
    /**
     * Password field (where text is hidden)
     */
    @FXML
    private PasswordField passwordField;
    /**
     * Buttons of the window: confirmation, exit and show password.
     */
    @FXML
    private Button loginButton, signUpButton, showPasswordButton, forgotPasswordButton;
    /**
     * Box that contains everthing
     */
    @FXML
    private AnchorPane box;

    /**
     * Value used to control the validation of the fields.
     */
    private boolean triedToLogin = false;

    /**
     * method that initiates the stage and sets/prepares the values inside of
     * it.
     *
     * @param root
     */
    public void initStage(Parent root) {
        box.setVisible(false); // necessary for the entrance animation.

        LOGGER.info("Initialazing LOGIN window.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //TODO: set interface.

        // Set window dimensions and properties.
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setTitle("Login");
        stage.getIcons().add(logo);
       
        // Hide the clear view of the password.
        passwordTextField.setVisible(false);

        // Buttons are set enabled.
        loginButton.setDisable(false);
        signUpButton.setDisable(false);
        forgotPasswordButton.setDisable(false);
        showPasswordButton.setDisable(false);

        // Error labels are set hidden
        usernameErrorText.setVisible(false);
        usernameErrorImage.setVisible(false);
        passwordErrorText.setVisible(false);
        passwordErrorImage.setVisible(false);

        // Listeners of all the fields.
        usernameTextField.textProperty().addListener(this::handleUsername);
        passwordField.textProperty().addListener(this::handlePassword);
        passwordTextField.textProperty().addListener(this::handlePassword);

        // In case of pressing the enter key
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            // Adds an event handler that records every
            // time the escape key is pressed
            if (KeyCode.ENTER == event.getCode()) {
                loginButton.requestFocus();
                handleLogin(null);
            }

        });


        // Button listeners.
        showPasswordButton.setOnAction(this::handleShowPassword);
        forgotPasswordButton.setOnAction(this::handleRecoverPassword);
        signUpButton.setOnAction(this::handleSignUp);
        loginButton.setOnAction(this::handleLogin);

        // Button hovering animations
        {
            loginButton.hoverProperty().addListener((observable, oldValue, newValue) -> { 
                if (newValue)
                    new Expand(loginButton).play();
                else
                    new Contract(loginButton).play();
            });
        }

        showPasswordButton.setFocusTraversable(false);
        
        // Close request reaction.
        // TODO: + esc 
        stage.setOnCloseRequest(this::handleCloseRequest);


        stage.show();

        // Logger update.
        LOGGER.info("Login window opened.");

        // Entrance animation
        slideBoxIn(box, 0);
    }

    /**
     * case of changing the username field
     *
     * @param observable in this case, username
     * @param oldValue previous username in field
     * @param newValue new username in field.
     */
    protected void handleUsername(ObservableValue observable,
        String oldValue, String newValue) {
            try {
                if (triedToLogin) {
                    validateUsername(newValue);

                    // If everything goes alright, hide error.
                    usernameErrorText.setVisible(false);
                    usernameErrorImage.setVisible(false);
                }
                
            } catch (EmptyFieldException | IncorrectFormatException e) {
                showErrorLabel(usernameErrorText, usernameErrorImage, e.getMessage());
            }
    }

    /**
     * case of changing the password text field
     *
     * @param observable in this case, password
     * @param oldValue previous password in password field
     * @param newValue new password in field
     */
    private void handlePassword(ObservableValue observable,
            String oldValue,
            String newValue) {

        if (newValue.equals(passwordTextField.getText()))
                passwordField.setText(newValue);
        else
                passwordTextField.setText(newValue);
                
        try {
            if (triedToLogin) {
                validatePassword(newValue);
                
                // If everything goes alright, hide errors.
                passwordErrorText.setVisible(false);
                passwordErrorImage.setVisible(false);
            }

        } catch (IncorrectFormatException | EmptyFieldException e) {
            showErrorLabel(passwordErrorText, passwordErrorImage, e.getMessage());
        } 
    }

    /**
     * case of "show password" button being pressed
     *
     * @param event
     */
    private void handleShowPassword(ActionEvent event) {
        //Logger.getLogger(App.class.getName()).info("Show Password button pressed");
        if (!passwordTextField.isVisible()) {
            showPassword(passwordTextField, passwordField, showPasswordImage, true);
            passwordTextField.requestFocus();
        }
            
        else {
            showPassword(passwordTextField, passwordField, showPasswordImage, false);
            passwordField.requestFocus();
        }
            

        
    }

    /*
     * LAUNCHING OTHER WINDOWS
     */

    private void handleLogin(ActionEvent event) {
        triedToLogin = true;
        new RubberBand(loginButton).play();
        
        handleUsername(null, null, usernameTextField.getText());
        
        if (passwordField.isVisible())
        handlePassword(null, null, passwordField.getText());
        else
        handlePassword(null, null, passwordTextField.getText());


        shakeErrors(usernameErrorText, usernameErrorImage);

        // TODO: launch the main window.
     }


    /**
     * Handle the case of pressing the SignUp button.
     *
     * @param event
     */
    private void handleSignUp(ActionEvent event) {
        new Jello(signUpButton).play();
        box.requestFocus();
        /**
         * This little code here animates the fade out of the window before launching the next one.
         */
        fadeTransition(box, 1, 0)
        .setOnFinished((ActionEvent) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/sign_up_view.fxml"));
                Parent root = (Parent) loader.load();
                // Obtain the Sign In window controller
                SignUpController controller = SignUpController.class
                    .cast(loader.getController());
                controller.setStage(stage);
                controller.initStage(root);
    
            } catch (Exception ex) {
                /*
                Logger.getLogger(App.class
                        .getName()).log(Level.SEVERE, null, ex);
                 */
            }
        });
    }

    /**
     * Launch password recovery window.
     * @param event
     */
    private void handleRecoverPassword(ActionEvent event) {
        box.requestFocus();
        /**
         * This little code here animates the fade out of the window before launching the next one.
         */
        fadeTransition(box, 1, 0)
        .setOnFinished((ActionEvent) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/password_recovery_view.fxml"));
                Parent root = (Parent) loader.load();
                // Obtain the Sign In window controller
                PasswordRecoveryController controller = PasswordRecoveryController.class
                    .cast(loader.getController());
                controller.setStage(stage);
                controller.initStage(root);
    
            } catch (Exception ex) {
                
                Logger.getLogger(LoginController.class
                        .getName()).log(Level.SEVERE, null, ex);
                 
            }
        });

            
            
           
            



    }
    
}
