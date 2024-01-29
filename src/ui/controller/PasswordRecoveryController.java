package ui.controller;

import animatefx.animation.Jello;
import animatefx.animation.partial.Contract;
import animatefx.animation.partial.Expand;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PasswordRecoveryController extends GenericController {

    @FXML
    private TextField emailTextField;

    @FXML
    private Button recoveryButton, loginButton;

    @FXML
    private AnchorPane box;

    /**
     * method that initiates the stage and sets/prepares the values inside of
     * it.
     *
     * @param root
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

        emailTextField.setDisable(false);
        recoveryButton.setDisable(false);
        loginButton.setDisable(false);

        // Setting listener
        emailTextField.textProperty().addListener(this::handleEmail);

        // Button listeners
        recoveryButton.setOnAction(this::handleRecovery);
        loginButton.setOnAction(this::handleLogin);

        // Button hovering animations
        {
            recoveryButton.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue)
                    new Expand(recoveryButton).play();
                else
                    new Contract(recoveryButton).play();
            });
        }

        stage.setScene(scene);

        stage.show();
        // Logger update.
        LOGGER.info("Window opened.");

        slideBoxIn(box, 1);
    }

    public void handleEmail(ObservableValue observable,
            String oldValue, String newValue) {

    }

    public void handleRecovery(ActionEvent event) {
        // TODO:

    }

    

    public void handleLogin(ActionEvent event) {
        new Jello(loginButton).play();
        box.requestFocus();
        /**
         * This little code here animates the fade out of the window before launching
         * the next one.
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
                        /*
                         * Logger.getLogger(App.class
                         * .getName()).log(Level.SEVERE, null, ex);
                         */
                    }
                });
    }
}
