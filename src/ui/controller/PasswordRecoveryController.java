package ui.controller;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import logic.exceptions.LogicException;

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
                if (newValue) {
                    new Expand(recoveryButton).play();
                } else {
                    new Contract(recoveryButton).play();
                }
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
        try {
            sendEmail("alexander.epelde@gmail.com");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }

    private void sendEmail(String givenEmail) throws LogicException {
        String emailToSend = new ReadHTMLFile().getMail();

        //String emailToSend = "html.toString()";
        // port and host configuration
        final String ZOHO_HOST = "smtp.zoho.eu";
        final String TLS_PORT = "897";
        // senders credentials
        final String SENDER_USERNAME = "bmoncalvillo@zohomail.eu";
        final String SENDER_PASSWORD = "Du75zJLqbaZ1";

        // protocol properties
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", ZOHO_HOST); // change to GMAIL_HOST for gmail // for gmail
        props.setProperty("mail.smtp.port", TLS_PORT);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtps.auth", "true");

        // close connection upon quit being sent
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        try {
            // create the message
            final MimeMessage msg = new MimeMessage(session);

            // set recipients and content
            msg.setFrom(new InternetAddress(SENDER_USERNAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(givenEmail, false));
            msg.setSubject("Demo");
            msg.setText(emailToSend, "utf-8", "html");
            msg.setSentDate(new Date());

            // send the mail
            Transport transport = session.getTransport("smtps");
            // send the mail
            transport.connect(ZOHO_HOST, SENDER_USERNAME, SENDER_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (MessagingException e) {
        }
    }

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
                        /*
                         * Logger.getLogger(App.class
                         * .getName()).log(Level.SEVERE, null, ex);
                         */
                    }
                });
    }
}
