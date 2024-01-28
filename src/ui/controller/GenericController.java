package ui.controller;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import animatefx.animation.*;
import app.App;
import java.io.IOException;
import java.util.logging.Level;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import logic.exceptions.EmptyFieldException;
import logic.exceptions.IncorrectFormatException;
import ui.exceptions.PasswordTooShortException;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * An abstract base controller class for JavaFX applications. This class
 * provides common functionality and properties that can be shared among various
 * controller classes.
 *
 * @author Dani
 * @author Alex Epelde
 */
public class GenericController {

    protected static Image logo = new Image("resources/img/app_logo.png");

    /**
     * Package logger
     */
    protected static final Logger LOGGER = Logger.getLogger("package view.controller\":");

    /**
     * maximum text fields length.
     */
    protected final int MAX_LENGTH = 255;
    /**
     * the business logic object containing all business methods.
     */
    //protected Signable signable;
    /**
     * the Stage object associated to the Scene controlled by this controller.
     */
    protected Stage stage;

    /**
     * gets the Stage object related to this controller.
     *
     * @return The Stage object initialized by this controller.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * sets the Stage object related to this controller.
     *
     * @param stage The Stage object to be initialized.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * shows an error message in an alert dialog.
     *
     * @param errorMsg The error message to be shown.
     */
    protected void showErrorAlert(String errorMsg) {
        // Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        // alert.getDialogPane().getStylesheets().add(getClass().getResource("/javafxapplicationud3example/ui/view/customCascadeStyleSheet.css").toExternalForm());
        alert.showAndWait();

    }

    protected void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    protected boolean showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm launch new window");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().filter(result -> result == ButtonType.OK).isPresent();
    }

    protected void handleLogOutAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Log Out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/login_view.fxml"));
                Parent root = (Parent) loader.load();
//            Obtain the Sign In window controller
                LoginController controller = LoginController.class
                        .cast(loader.getController());
                controller.setStage(stage);
                controller.initStage(root);
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Checks if the input string is too long.
     *
     * @param input The input string to be checked for length.
     * @throws IncorrectFormatException If the input string is too long, this
     */
    protected boolean isTooLong(String input) throws IncorrectFormatException {
        if (input.length() > 255) {
            throw new IncorrectFormatException();
        }
        return false;
    }

    /**
     * Checks if the provided password is too short.
     *
     * @param password The password to be checked for its length.
     * @throws PasswordTooShortException If the password is too short (less than
     */
    protected boolean isTooShort(String password) throws PasswordTooShortException {
        if (password.length() < 8) {
            throw new PasswordTooShortException();
        }
        return false;
    }

    /**
     * action that will be executed when the user tries to close the
     * application.
     */
    protected void showExitDialog() {
        Optional<ButtonType> action = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit the application?").showAndWait();
        if (action.get() == ButtonType.OK) {
            Logger.getLogger(App.class.getName()).info("Exiting application");
            Platform.exit();
        }
    }

    /**
     * method that analyses the username
     *
     * @param email username to be checked
     * @return true if value is correct
     * @throws IncorrectFormatException if value doesn't match condition.
     */
    protected boolean validateUsername(String username) throws EmptyFieldException, IncorrectFormatException {
        if (username.isEmpty()) {
            throw new EmptyFieldException("The username cannot be empty");
        }

        if (username.contains(" ")) {
            throw new IncorrectFormatException("The username cannot have spaces");
        }

        if (MAX_LENGTH < username.length()) {
            throw new IncorrectFormatException("The username is too long");
        }

        if (username.length() < 3) {
            throw new IncorrectFormatException("The username must be at least 3 characters long");
        }

        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[a-zA-Z0-9._-]{3,}$",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(username);

        if (!matcher.matches()) {
            throw new IncorrectFormatException("The username must have only letters, numbers and '-', '/' or '_'");
        }

        return true;
    }

    /**
     * method that analyses the email
     *
     * @param email username to be checked
     * @return true if value is correct
     * @throws IncorrectFormatException if value doesn't match condition.
     */
    protected boolean validateEmail(String email) throws EmptyFieldException, IncorrectFormatException {
        if (email.isEmpty()) {
            throw new EmptyFieldException("The email cannot be empty");
        }

        if (email.contains(" ")) {
            throw new IncorrectFormatException("The email cannot have spaces");
        }

        if (MAX_LENGTH < email.length()) {
            throw new IncorrectFormatException("The email is too long");
        }

        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        if (!matcher.matches()) {
            throw new IncorrectFormatException("The email must be an email");
        }

        return true;
    }

    /**
     * Validates a password for correct format, length, and emptiness.
     *
     * @param password The password to be validated.
     * @return
     */
    protected boolean validatePassword(String password)
            throws IncorrectFormatException, EmptyFieldException {

        if (password.length() < 8) {
            throw new IncorrectFormatException("The password must be at least 8 characters long.");
        }

        if (MAX_LENGTH < password.length()) {
            throw new IncorrectFormatException("The password is too long");
        }

        if (password.length() == 0) {
            throw new EmptyFieldException("The password cannot be empty");
        }

        return true;
    }

    protected void validatePersonalInfo(String info, String typeOfInfo) throws EmptyFieldException, IncorrectFormatException {
        if (info.isEmpty()) {
            throw new EmptyFieldException(
                    typeOfInfo + " cannot be empty");
        }
        if (!Character.isUpperCase(info.charAt(0))) {
            throw new IncorrectFormatException(
                    typeOfInfo + " must start with capital letters");
        }

        if (info.contains(" ")) {
            if (info.indexOf(" ") + 1 != info.length()) {
                if (!Character.isUpperCase(info.charAt(info.indexOf(" ") + 1))) {
                    throw new IncorrectFormatException(
                            "Every word must start with capital letters");
                }
            }
        }
    }

    /**
     * Method to handle the close request
     */
    protected void handleCloseRequest(WindowEvent event) {
        event.consume();
        if (event.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)) {
            showExitDialog();
        }
    }


    /*
    ANIMATION STUFFF *****************************
     */
    /**
     * This method slides smoothly the box in.
     *
     * @param box
     * @param dir if dir is 1, goes up, if 0 down. otherwise up.
     */
    protected void slideBoxIn(Pane box, int dir) {
        box.setOpacity(0);
        new FadeIn(box).play();
        if (dir == 0) {
            new SlideInUp(box).play();
        } else {
            new SlideInDown(box).play();
        }
        box.setVisible(true);
    }

    /**
     * The following code animates a fading animation. If parameters are 1 to 0,
     * animates a fade out. If there are 0 to 1, animates a fade in. Use to
     * returned FadeTransition object to launch the next window with the method
     * setOnFinished from {@link FadeTransition}
     *
     * @param node node to fade out or in
     * @param from current status
     * @param to next status
     * @return a FadeTransition object. It has to be returned in order to use it
     * to launch the following window.
     */
    protected FadeTransition fadeTransition(Pane node, int from, int to) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(from);
        ft.setToValue(to);
        ft.play();
        return ft;
    }

    protected void showErrorLabel(Text errorLabel, ImageView errorImage, String message) {
        if (!errorLabel.isVisible()) {
            errorLabel.setText(message);
            new FadeIn(errorLabel).play();

            shakeErrors(errorLabel, errorImage);

            errorLabel.setVisible(true);
            errorImage.setVisible(true);
        } else {
            errorLabel.setText(message);
        }
    }

    protected void shakeErrors(Text errorLabel, ImageView errorImage) {
        new Shake(errorImage).play();
    }

    /**
     * Method that shows the password (hide Password Field and show Text Field)
     *
     * @param visible if true, sets the password text field visible and the
     * field non-visible. If not, the oposite.
     */
    protected void showPassword(TextInputControl passwordTextField,
            TextInputControl passwordField, ImageView showPasswordImage, boolean visible) {
        if (visible) {
            passwordField.setVisible(false);
            passwordTextField.setVisible(true);
            showPasswordImage.setImage(new Image("resources/img/not-seen.png"));
        } else {
            passwordField.setVisible(true);
            passwordTextField.setVisible(false);
            showPasswordImage.setImage(new Image("resources/img/seen.png"));
        }

        new Flash(showPasswordImage).play();
    }
}
