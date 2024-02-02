package logic.business;

import java.io.InputStream;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The EmailManager class extends the Thread class to handle email operations in
 * a separate thread. It provides functionality for sending password recovery
 * emails to a specified recipient using an embedded email template.
 */
public class EmailManager extends Thread {

    private final String embeddedPassword = "abcd*1234";
    private String recipient;
    private String password;

    /**
     * Constructs an EmailManager with specified recipient and password.
     *
     * @param recipient The email address of the recipient.
     * @param password The new password to be included in the email.
     */
    public EmailManager(String recipient, String password) {
        this.password = password;
        this.recipient = recipient;
    }

    /**
     * Overrides the run method of Thread class. This method is called when the
     * thread starts and it initiates the process of sending an email.
     */
    @Override
    public void run() {
        super.run();
        sendEmail(recipient, password);
    }

    /**
     * Sends an email to the specified recipient with the new password. The
     * method configures the email server settings, constructs the email
     * message, and sends it.
     *
     * @param recipient The recipient's email address.
     * @param newPassword The new password to be sent.
     */
    public void sendEmail(String recipient, String newPassword) {
        try {
            String email = getMailPrompt().replace(embeddedPassword, newPassword);;

            final String ZOHO_HOST = "smtp.zoho.eu";
            final String TLS_PORT = "897";

            final String SENDER_USERNAME = "bmoncalvillo@zohomail.eu";
            final String SENDER_PASSWORD = "Du75zJLqbaZ1";

            // protocol properties
            Properties props = System.getProperties();
            props.setProperty("mail.smtps.host", ZOHO_HOST); // change to GMAIL_HOST for gmail                                                         // for gmail
            props.setProperty("mail.smtp.port", TLS_PORT);
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtps.auth", "true");

            // close connection upon quit being sent
            props.put("mail.smtps.quitwait", "false");

            Session session = Session.getInstance(props, null);

            // create the message
            final MimeMessage msg = new MimeMessage(session);

            // set recipients and content
            msg.setFrom(new InternetAddress(SENDER_USERNAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            msg.setSubject("Password Recovery");
            msg.setText(email, "utf-8", "html");
            msg.setSentDate(new Date());

            // send the mail
            Transport transport = session.getTransport("smtps");
            // send the mail
            transport.connect(ZOHO_HOST, SENDER_USERNAME, SENDER_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
        } catch (Exception ex) {
            Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieves the email template from a file and returns it as a String. The
     * method reads the HTML content of the email from a resource file and
     * returns it.
     *
     * @return A String containing the HTML email template.
     */
    private String getMailPrompt() {
        // Specify the path to HTML file
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("resources/Email.html");
            byte[] emailBytes = new byte[stream.available()];
            stream.read(emailBytes);
            return new String(emailBytes);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

}
