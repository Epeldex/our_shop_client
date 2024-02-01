package ui.controller;

import java.io.IOException;

import animatefx.animation.*;
import animatefx.animation.partial.Contract;
import animatefx.animation.partial.Expand;
import app.App;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.exceptions.EmptyFieldException;
import logic.exceptions.IncorrectFormatException;
import logic.exceptions.PasswordsDoNotMatchException;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.encryption.EncriptionManagerFactory;
import logic.exceptions.LogicException;
import logic.factories.CustomerManagerFactory;
import transfer.objects.Customer;
import transfer.objects.UserType;

/**
 * Controller class for the SignUp window. Handles user input validation and
 * sign-up process.
 *
 * @author Alex Irusta
 */
public class SignUpController extends GenericController {

    @FXML
    private Text usernameErrorText, password1ErrorText, password2ErrorText, emailErrorText,
            fullNameErrorText, adressErrorText, postalCodeErrorText, cityErrorText, phoneErrorText;

    @FXML
    private TextField usernameTextField, passwordTextField1, passwordTextField2, emailTextField, fullNameTextField,
            adressTextField, postalCodeTextField, cityTextField, phoneNumberTextField;

    @FXML
    private PasswordField passwordField1, passwordField2;

    @FXML
    private ImageView usernameErrorImage, password1ErrorImage, password2ErrorImage, emailErrorImage,
            fullNameErrorImage,
            adressErrorImage, postalCodeErrorImage, cityErrorImage, phoneErrorImage,
            showPasswordImage1, showPasswordImage2;

    @FXML
    private Button loginButton, signUpButton, showPasswordButton1, showPasswordButton2;

    @FXML
    private AnchorPane box;

    private boolean triedToSignUp;

    /**
     * Initializes the stage and prepares the values inside it.
     *
     * @param root The root Parent of the stage.
     */
    public void initStage(Parent root) {
        box.setVisible(false); // necessary for the entrance animation.

        LOGGER.info("Initialazing SIGN UP window.");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // TODO: set interface

        // Set window dimensions and properties.
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setTitle("Sign Up");
        stage.getIcons().add(logo);

        // Hide the clear view of the password.
        passwordTextField1.setVisible(false);
        passwordTextField2.setVisible(false);

        // Buttons are set enabled.
        loginButton.setDisable(false);
        signUpButton.setDisable(false);
        showPasswordButton1.setDisable(false);
        showPasswordButton2.setDisable(false);

        // Error labels and images are set hidden
        usernameErrorText.setVisible(false);
        usernameErrorImage.setVisible(false);
        password1ErrorText.setVisible(false);
        password1ErrorImage.setVisible(false);
        password2ErrorText.setVisible(false);
        password2ErrorImage.setVisible(false);
        emailErrorText.setVisible(false);
        emailErrorImage.setVisible(false);
        fullNameErrorText.setVisible(false);
        fullNameErrorImage.setVisible(false);
        adressErrorText.setVisible(false);
        adressErrorImage.setVisible(false);
        postalCodeErrorText.setVisible(false);
        postalCodeErrorImage.setVisible(false);
        cityErrorText.setVisible(false);
        cityErrorImage.setVisible(false);
        phoneErrorText.setVisible(false);
        phoneErrorImage.setVisible(false);

        // Listeners of every field.
        usernameTextField.textProperty().addListener(this::handleUsername);
        passwordField1.textProperty().addListener(this::handlePassword1);
        passwordTextField1.textProperty().addListener(this::handlePassword1);
        passwordField2.textProperty().addListener(this::handlePassword2);
        passwordTextField2.textProperty().addListener(this::handlePassword2);
        emailTextField.textProperty().addListener(this::handleEmail);
        fullNameTextField.textProperty().addListener(this::handleFullName);
        adressTextField.textProperty().addListener(this::handleAdress);
        postalCodeTextField.textProperty().addListener(this::handlePostalCode);
        cityTextField.textProperty().addListener(this::handleCity);
        phoneNumberTextField.textProperty().addListener(this::handlePhoneNumber);

        // Button listener.
        loginButton.setOnAction(this::handleLoginButton);
        signUpButton.setOnAction(this::handleSignUp);
        showPasswordButton1.setOnAction(this::handleShowPassword1);
        showPasswordButton2.setOnAction(this::handleShowPassword2);
        signUpButton.setDefaultButton(true);

        // Button hovering animations
        {
            signUpButton.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    new Expand(signUpButton).play();
                } else {
                    new Contract(signUpButton).play();
                }
            });
        }

        showPasswordButton1.setFocusTraversable(false);
        showPasswordButton2.setFocusTraversable(false);

        // Close request reaction.
        stage.setOnCloseRequest(this::handleCloseRequest);

        stage.show();
        // Logger update.
        LOGGER.info("Window opened.");

        // Entrance animation
        slideBoxIn(box, 0);
    }

    /**
     * case of changing the password text field
     *
     * @param observable in this case, password
     * @param oldValue previous password in password field
     * @param newValue new password in field
     */
    private void handleUsername(ObservableValue observable,
            String oldValue,
            String newValue) {
        try {
            if (triedToSignUp) {
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
     * case of changing the password text field or password field 1
     *
     * @param observable in this case, password
     * @param oldValue previous password in password field
     * @param newValue new password in field
     */
    private void handlePassword1(ObservableValue observable,
            String oldValue,
            String newValue) {
        if (passwordTextField1.isVisible()) {
            passwordField1.setText(newValue);
        } else {
            passwordTextField1.setText(newValue);
        }

        try {
            if (triedToSignUp) {
                validatePassword(newValue);

                // If the other field (whichever has been lastly modified) doesn't match, throw exception.
                if (!(passwordTextField2.isVisible() && newValue.equals(passwordTextField2.getText()))
                        || !(passwordField2.isVisible() && newValue.equals(passwordField2.getText()))) {
                    throw new PasswordsDoNotMatchException();
                } else {
                    password1ErrorText.setVisible(false);
                    password1ErrorImage.setVisible(false);
                    // If everything goes alright, hide errors.
                    password2ErrorText.setVisible(false);
                    password2ErrorImage.setVisible(false);
                }
            }

        } catch (IncorrectFormatException | EmptyFieldException e) {
            showErrorLabel(password1ErrorText, password1ErrorImage, e.getMessage());
        } catch (PasswordsDoNotMatchException e) {
            showErrorLabel(password1ErrorText, password1ErrorImage, e.getMessage());
        }
    }

    /**
     * case of changing the password text field or password field 1
     *
     * @param observable in this case, password
     * @param oldValue previous password in password field
     * @param newValue new password in field
     */
    private void handlePassword2(ObservableValue observable,
            String oldValue,
            String newValue) {

        if (passwordTextField2.isVisible()) {
            passwordField2.setText(newValue);
        } else {
            passwordTextField2.setText(newValue);
        }

        try {
            if (triedToSignUp) {
                validatePassword(newValue);

                // If the other field (whichever has been lastly modified) doesn't match, throw exception.
                if ((passwordTextField1.isVisible() && !newValue.equals(passwordTextField1.getText()))
                        || (passwordField1.isVisible() && !newValue.equals(passwordField1.getText()))) {
                    throw new PasswordsDoNotMatchException();
                } else {
                    password1ErrorText.setVisible(false);
                    password1ErrorImage.setVisible(false);
                    // If everything goes alright, hide errors.
                    password2ErrorText.setVisible(false);
                    password2ErrorImage.setVisible(false);
                }

            }

        } catch (IncorrectFormatException | EmptyFieldException e) {
            showErrorLabel(password1ErrorText, password1ErrorImage, e.getMessage());
        } catch (PasswordsDoNotMatchException e) { // don't match error always shown on first field
            showErrorLabel(password1ErrorText, password1ErrorImage, e.getMessage());
        }
    }

    /**
     * case of changing the password text field
     *
     * @param observable in this case, password
     * @param oldValue previous password in password field
     * @param newValue new password in field
     */
    private void handleEmail(ObservableValue observable,
            String oldValue,
            String newValue) {

        if (triedToSignUp) {
            try {
                validateEmail(newValue);

                // If there are no errors, hide labels
                emailErrorText.setVisible(false);
                emailErrorImage.setVisible(false);
            } catch (IncorrectFormatException | EmptyFieldException e) {
                showErrorLabel(emailErrorText, emailErrorImage, e.getMessage());
            }
        }
    }

    /**
     * case of changing the password text field
     *
     * @param observable in this case, password
     * @param oldValue previous password in password field
     * @param newValue new password in field
     */
    private void handleFullName(ObservableValue observable,
            String oldValue,
            String newValue) {
        if (triedToSignUp) {
            try {
                validatePersonalInfo(newValue, "Full name");

                // If there are no errors, hide labels
                fullNameErrorText.setVisible(false);
                fullNameErrorImage.setVisible(false);
            } catch (IncorrectFormatException | EmptyFieldException e) {
                showErrorLabel(fullNameErrorText, fullNameErrorImage, e.getMessage());
            } catch (IndexOutOfBoundsException e) {

            }
        }
    }

    /**
     * case of changing the password text field
     *
     * @param observable in this case, password
     * @param oldValue previous password in password field
     * @param newValue new password in field
     */
    private void handleAdress(ObservableValue observable,
            String oldValue,
            String newValue) {
        try {
            if (triedToSignUp) {
                validatePersonalInfo(newValue, "Adress");

                // If everything goes alright, hide error.
                adressErrorText.setVisible(false);
                adressErrorImage.setVisible(false);
            }

        } catch (EmptyFieldException | IncorrectFormatException e) {
            showErrorLabel(adressErrorText, adressErrorImage, e.getMessage());
        }
    }

    /**
     * case of changing the password text field
     *
     * @param observable in this case, password
     * @param oldValue previous password in password field
     * @param newValue new password in field
     */
    private void handlePostalCode(ObservableValue observable,
            String oldValue,
            String newValue) {
        try {
            if (triedToSignUp) {
                if (newValue.isEmpty()) {
                    throw new EmptyFieldException("Postal code cannot be empty");
                }

                if (!newValue.matches("[0-9]+")
                        || newValue.contains(" ")) {
                    throw new NumberFormatException("Postal code must be numerical");
                }

                // If everything goes alright, hide error.
                postalCodeErrorText.setVisible(false);
                postalCodeErrorImage.setVisible(false);
            }

        } catch (EmptyFieldException e) {
            showErrorLabel(postalCodeErrorText, postalCodeErrorImage, e.getMessage());
            postalCodeErrorText.setFill(Color.valueOf("#ff6161"));//red
            postalCodeErrorImage.setImage(new Image("/resources/img/error-mark.png"));

        } catch (NumberFormatException e) {
            postalCodeErrorText.setFill(Color.valueOf("#4495ff"));//blue
            postalCodeErrorImage.setImage(new Image("/resources/img/info.png"));

            postalCodeTextField.setText(
                    newValue
                            .replaceAll("[^\\d.]", ""));
            if (!postalCodeErrorText.isVisible()) {
                showErrorLabel(postalCodeErrorText, postalCodeErrorImage, e.getMessage());
            }
        }
    }

    /**
     * case of changing the password text field
     *
     * @param observable in this case, password
     * @param oldValue previous password in password field
     * @param newValue new password in field
     */
    private void handleCity(ObservableValue observable,
            String oldValue,
            String newValue) {
        try {
            if (triedToSignUp) {
                validatePersonalInfo(newValue, "City");

                // If everything goes alright, hide error.
                cityErrorText.setVisible(false);
                cityErrorImage.setVisible(false);
            }

        } catch (EmptyFieldException | IncorrectFormatException e) {
            showErrorLabel(cityErrorText, cityErrorImage, e.getMessage());
        }
    }

    /**
     * case of changing the password text field
     *
     * @param observable in this case, password
     * @param oldValue previous password in password field
     * @param newValue new password in field
     */
    private void handlePhoneNumber(ObservableValue observable,
            String oldValue,
            String newValue) {
        /// TODO: parsing to integer doesnt work. number can be even 999999999999999999999.

        try {
            if (triedToSignUp) {
                if (newValue.isEmpty()) {
                    throw new EmptyFieldException("Phone number cannot be empty");
                }

                if (!newValue.matches("[0-9]+")
                        || newValue.contains(" ")) {
                    throw new NumberFormatException("Phone number must be numerical");
                }

                // If everything goes alright, hide error.
                phoneErrorText.setVisible(false);
                phoneErrorImage.setVisible(false);
            }

        } catch (EmptyFieldException e) {
            showErrorLabel(phoneErrorText, phoneErrorImage, e.getMessage());
            phoneErrorText.setFill(Color.valueOf("#ff6161"));//red
            phoneErrorImage.setImage(new Image("/resources/img/error-mark.png"));
        } catch (NumberFormatException e) {
            phoneErrorText.setFill(Color.valueOf("#4495ff"));//blue
            phoneErrorImage.setImage(new Image("/resources/img/info.png"));

            phoneNumberTextField.setText(
                    phoneNumberTextField.getText()
                            .replaceAll("[^\\d.]", ""));
            if (!phoneErrorText.isVisible()) {
                showErrorLabel(phoneErrorText, phoneErrorImage, e.getMessage());
            }
        }
    }

    // Event handler for the sign-up button
    private void handleSignUp(ActionEvent event) {
        triedToSignUp = true;

        handleUsername(null, null, usernameTextField.getText());

        handlePassword1(null, null, passwordField1.getText());

        handlePassword2(null, null, passwordField2.getText());

        handleEmail(null, null, emailTextField.getText());
        handleFullName(null, null, fullNameTextField.getText());
        handleAdress(null, null, adressTextField.getText());
        handlePostalCode(null, null, postalCodeTextField.getText());
        handleCity(null, null, cityTextField.getText());
        handlePhoneNumber(null, null, phoneNumberTextField.getText());

        Customer customer = new Customer();
        customer.setActive(true);
        customer.setBalance(0D);
        customer.setCity(cityTextField.getText());
        customer.setEmail(emailTextField.getText());
        customer.setFullName(fullNameTextField.getText());
        customer.setPassword(EncriptionManagerFactory.getInstance().hashMessage(passwordField2.getText()));
        customer.setPhone(phoneNumberTextField.getText());
        customer.setPostalCode(Integer.parseInt(postalCodeTextField.getText()));
        customer.setStreet(adressTextField.getText());
        customer.setUserType(UserType.CUSTOMER);
        customer.setUsername(usernameTextField.getText());

        try {
            CustomerManagerFactory.getInstance().insertCustomer(customer);
        } catch (LogicException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void handleLoginButton(ActionEvent event) {
        box.requestFocus();
        new Jello(loginButton).play();
        /**
         * This little code here animates the fade out of the window before
         * launching the next one.
         */
        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(box);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
        ft.setOnFinished((ActionEvent) -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getClassLoader().getResource("ui/views/login_view.fxml"));
                Parent root = (Parent) loader.load();
                // Obtain the Sign In window controller
                LoginController controller = LoginController.class
                        .cast(loader.getController());
                controller.setStage(stage);
                controller.initStage(root);

            } catch (IOException ex) {

                Logger.getLogger(App.class
                        .getName()).log(Level.SEVERE, null, ex);

            }
        });

    }

    // Event handler for changing the password field or password field 1
    private void handleShowPassword1(ActionEvent event) {
        if (!passwordTextField1.isVisible()) {
            showPassword(passwordTextField1, passwordField1, showPasswordImage1, true);
            passwordTextField1.requestFocus();
        } else {
            showPassword(passwordTextField1, passwordField1, showPasswordImage1, false);
            passwordField1.requestFocus();
        }

    }

    // Event handler for changing the password field or password field 2
    private void handleShowPassword2(ActionEvent event) {
        if (!passwordTextField2.isVisible()) {
            showPassword(passwordTextField2, passwordField2, showPasswordImage2, true);
            passwordTextField2.requestFocus();

        } else {
            showPassword(passwordTextField2, passwordField2, showPasswordImage2, false);
            passwordField2.requestFocus();
        }

    }
}
