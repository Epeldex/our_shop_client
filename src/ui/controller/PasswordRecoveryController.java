package ui.controller;

import animatefx.animation.Jello;
import animatefx.animation.partial.Contract;
import animatefx.animation.partial.Expand;
import app.App;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Base64;
import javax.ws.rs.core.GenericType;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import logic.business.EmailManager;
import logic.encryption.EncriptionManagerFactory;
import logic.exceptions.EmptyFieldException;
import logic.exceptions.IncorrectFormatException;
import rest.CustomerRESTClient;
import transfer.objects.Customer;



/**
 * Controller for the Password Recovery window.
 *
 * This class handles user interactions and controls the behavior of the
 * Password Recovery window, including sending recovery emails and transitioning
 * to the Login window.
 *
 * @author Alex Epelde
 */
public class PasswordRecoveryController extends GenericController {

    /**
     * Text fields where user will input the their email.
     */
    @FXML
    private TextField emailTextField;
    /**
     * Red image of email format error.
     */
    @FXML
    private ImageView emailErrorImage;
    /**
     * Text displaying email format error info.
     */
    @FXML
    private Text emailErrorText;
    /**
     * Buttons of the window: recovery, and back to login.
     */
    @FXML
    private Button recoveryButton, loginButton;
    /**
     * Box containing everything
     */
    @FXML
    private AnchorPane box;
    /**
     * Useful when checking email.
     */
    boolean triedToRecover;

    /**
     * Initializes the stage and sets/prepares the values inside of it.
     *
     * @param root The root node of the scene.
     */
    public void initStage(Parent root) {
        box.setVisible(false);
        LOGGER.info("Initialazing " + " window.");
        Scene scene = new Scene(root);
        // stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.getIcons().add(logo);
        stage.setTitle("Recovery Window");

        // Setting every node enabled.
        emailTextField.setDisable(false);
        recoveryButton.setDisable(false);
        loginButton.setDisable(false);

        // Hiding error messages
        emailErrorImage.setVisible(false);
        emailErrorText.setVisible(false);
        // Setting listener
        emailTextField.textProperty().addListener(this::handleEmail);

        // Button listeners
        recoveryButton.setOnAction(this::handleRecovery);
        loginButton.setOnAction(this::handleLogin);

        // Button hovering animations

        recoveryButton.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                new Expand(recoveryButton).play();
            else
                new Contract(recoveryButton).play();

        });

        //
        stage.setScene(scene);
        // Let's show the window.
        stage.show();
        // Logger update.
        LOGGER.info("Window opened.");

        // Entrance animation
        slideBoxIn(box, 1);
    }

    /**
     * Method that checks the email format and shows (or hides)
     * the error message.
     * 
     * @param observable
     * @param oldValue
     * @param newValue current value in email field
     */
    public void handleEmail(ObservableValue observable,
            String oldValue, String newValue) {

        if (triedToRecover) {
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
     * Handles the password recovery action.
     *
     * @param event The ActionEvent triggered by the recovery button.
     */
    private void handleRecovery(ActionEvent event) {
        triedToRecover = true;
        handleEmail(null, null, emailTextField.getText());
        Customer customer = null;
        try {
            if (validateEmail(emailTextField.getText())) {
                customer = new CustomerRESTClient().resetPassword(
                        new GenericType<Customer>() {
                        }, emailTextField.getText());

                if (customer == null)
                    showErrorAlert("There's no user with this email adress.");

                else {
                    EmailManager em = new EmailManager(
                            customer.getEmail(),
                            Base64.getEncoder().encodeToString(
                                    EncriptionManagerFactory
                                            .getInstance().decryptMessage(customer.getPassword())));
                    em.start();
                }

            }
        } catch (IncorrectFormatException | EmptyFieldException e) {
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
        if (customer == null)
            showErrorAlert("There's no user with this email adress.");
    }

    /**
     * Handles the login action.
     *
     * @param event The ActionEvent triggered by the login button.
     */
    public void handleLogin(ActionEvent event) {
        new Jello(loginButton).play();
        box.requestFocus();
        /**
         * This little code here animates the fade out of the window before
         * launching the next one.
         */
        fadeTransition(box, 1, 0)
                .setOnFinished((ActionEvent) -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getClassLoader().getResource("ui/views/login_view.fxml"));
                        Parent root = (Parent) loader.load();
                        // Obtain the Sign In window controller
                        LoginController controller = LoginController.class
                                .cast(loader.getController());
                        controller.setStage(stage);
                        controller.initStage(root);

                    } catch (Exception ex) {

                        Logger.getLogger(App.class
                                .getName()).log(Level.SEVERE, null, ex);

                    }
                });
    }
}
